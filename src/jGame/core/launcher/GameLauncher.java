package jGame.core.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import jGame.core.entity.EntityManager;
import jGame.core.sound.SoundStore;
import jGame.core.ui.Window;
import jGame.core.ui.hud.Constraints;
import jGame.core.ui.hud.UIHud;
import jGame.core.ui.hud.UIHudButtonElement;
import jGame.core.ui.hud.UIHudElement;
import jGame.core.ui.hud.UIHudTextElement;
import jGame.core.ui.hud.fonts.FontManager;
import jGame.core.utils.properties.PropertiesManager;
import jGame.logging.ProgramLogger;

/**
 * This class is designed to be the entry-point for any game based on the JGame
 * framework
 *
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class GameLauncher {

	static {
		// initializes all the relevant/required properties on class loading to be used
		// at execution
		try {
			PropertiesManager.fetchProperties();
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e, "Error fetching properties.");
		}

		FontManager.addFont("default", new Font(Font.DIALOG, Font.PLAIN, 12));
		SoundStore.init();

	}

	// a thread pool to make code execution non-blocking
	private static ThreadPoolExecutor gameThreadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(Executors.defaultThreadFactory());

	//default window setup
	private static final int DEFAULT_WIDTH = 750, DEFAULT_HEIGHT = DEFAULT_WIDTH * 10 / 14;
	private static final Window DEFAULT_WINDOW = new Window(DEFAULT_WIDTH, DEFAULT_HEIGHT, "Default Game");

	private static Window mainWindow = DEFAULT_WINDOW;

	//number of buffers to use in the canvas
	private static final int BUFFER_AMOUNT = 3;

	private static UIHudTextElement FPSCounter = new UIHudTextElement(0, 0, "FPS: ", Color.GREEN,
			Constraints.concat(Constraints.FROM_TOP_CONSTRAINT, Constraints.FROM_LEFT_CONSTRAINT),
			new int[] { 7, 0, 0, 7 });
	private static int fps = 0;

	/**
	 * Returns the current fps value.
	 * 
	 * @return the current fps value
	 * @since 1.1.0
	 */
	public static int getFPS() {
		return fps;
	}

	private static boolean pause = false, hudEvent = false;

	/**
	 * Convenience method to indicate that any events generated by the user that are
	 * processed by any {@link UIHudElement}s are not to be processed by any
	 * listener declared in this class.
	 * 
	 * @since 1.2.0
	 */
	public static void setHudEvent() {
		GameLauncher.hudEvent = true;
	}

	private static UIHudPauseMenuElement pauseMenu = UIHudPauseMenuElement.singleton;
	
	private static Action pauseAction = new AbstractAction() {

		private static final long serialVersionUID = -8628054626023408846L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (hudEvent) { // 'p' has been pressed on a hud element, need this to not pass events down
				hudEvent = false;
				return;
			}
			ProgramLogger.writeLog("Pausing/Unpausing game");
			if (!pause) {
				pauseMenu.registerInputListener();
				UIHud.addHUDUIElement(pauseMenu);
			} else {
				pauseMenu.removeInputListener();
				UIHud.removeHUDUIElement(pauseMenu);
			}
			
			pause = !pause;
		}
	};

	// since 1.1.0
	private static UIHudButtonElement pauseMenuButton = new UIHudButtonElement(0, 0, 50, 20,
			PropertiesManager.getPropertyOrDefault("pauseButton.displayName", "Pause"),
			Constraints.concat(Constraints.FROM_TOP_CONSTRAINT, Constraints.FROM_RIGHT_CONSTRAINT),
			new int[] { 10, 10, 0, 0 }) {

		{
			// make this value big so we ensure it gets rendered on top of everything a user
			// might add.
			this.zIndex = 200;
		}

		@Override
		protected void processClick(MouseEvent e) {
			pauseAction.actionPerformed(null); // we already defined the behaviour for this previously, so no need to
												// repeat.
		}

		@Override
		public void registerInputListener() {
			super.registerInputListener();
			GameLauncher.getMainWindow().addAction(pauseAction, KeyStroke.getKeyStroke('p'), "pause");
		}

		@Override
		public void removeInputListener() {
			super.removeInputListener();
			GameLauncher.getMainWindow().removeAction(KeyStroke.getKeyStroke('p'), "pause");
		}
	};

	/**
	 * Unpauses the game.
	 *
	 * @since 1.1.0
	 * @deprecated Use {@link #togglePause()} instead
	 */
	@Deprecated
	public static void unpause() {
		pause = false;
	}

	/**
	 * Toggles the pause action.
	 * 
	 * @since 1.3.0
	 */
	public static void togglePause() {
		pauseAction.actionPerformed(null);
	}

	private static boolean isGameRunning = false, drawFPS = true;

	// the framework's main loop
	private static Runnable gameLoop = () -> {
		ProgramLogger.writeLog("Starting game loop.");
		long lastTime = System.nanoTime();
		double desiredFPS = 120;
		double nanoSecondsPerFrame = 1000000000/desiredFPS;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		FPSCounter.setZIndex(9999);
		UIHud.addHUDUIElement(FPSCounter);

		GameStateManager.addUIHudElementToBoard(pauseMenuButton);

		GameStateManager.initializeBoard();
		GameStateManager.addBoard();

		GameStateManager.initState();

		while(isGameRunning) {

			// ensure that all listeners can handle user events
			mainWindow.getWindowCanvas().requestFocus();

			long now = System.nanoTime();
			delta += (now - lastTime) / nanoSecondsPerFrame;
			lastTime = now;
			while (delta >= 1) {
				if (!pause)
					tick();
				delta--;
			}

			if(isGameRunning)
				render();

			// store frames in local variable
			frames++;

			// a second has passed, update fps
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;

				if (!drawFPS)
					System.out.println("FPS: " + frames);

				// copy value of frames to field and reset frames. This allows to correctly draw
				// fps value every second
				fps = frames;
				frames = 0;
			}
		}
		processGameTermination();
		return;
	};

	public static String gameName;

	/**
	 * Renders the game on the screen, by clearing the game window, rendering all
	 * entities and then the HUD.
	 *
	 * @since 1.0.0
	 */
	private static void render() {
		BufferStrategy bs = mainWindow.getWindowCanvas().getBufferStrategy();
		if(bs == null) {
			mainWindow.getWindowCanvas().createBufferStrategy(BUFFER_AMOUNT);
			return;
		}

		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		//insert rendering code here

		g2d.clearRect(0, 0, (int) mainWindow.getWindowCanvas().getBounds().getWidth(),
				(int) mainWindow.getWindowCanvas().getBounds().getHeight());
		g2d.setColor(mainWindow.getWindowCanvas().getBackground());

		EntityManager.renderEntities(g2d);

		//draw FPS on screen
		if (drawFPS)
			FPSCounter.setTextToDisplay("FPS: " + fps);
		else
			FPSCounter.setTextToDisplay("");

		// render all HUD elements on top of every game object
		UIHud.render(g2d);

		g2d.dispose();
		g2d = null;
		bs.show();
		bs = null;
	}

	/**
	 * Processes game termination. This method is called when the game is about to
	 * close and we need to perform some vital last minute operations.
	 *
	 * @since 1.1.0
	 */
	private static void processGameTermination() {

		GameStateManager.terminateState();
		ProgramLogger.writeLog("Terminating game!");
		System.exit(0);

	}

	/**
	 * Updates all the entities in the game before rendering them.
	 *
	 * @since 1.0.0
	 */
	private static void tick() {
		EntityManager.tickEntities();
	}

	/**
	 * Launches the game, rendering all screen events on the {@link #mainWindow}.
	 *
	 * @since 1.0.0
	 */
	public static void launchGame() {

		ProgramLogger.writeLog("Launching game!");

		isGameRunning = true;

		EntityManager.registerInputListeners();
		UIHud.registerInputListeners();

		mainWindow.setMainWindow();

		if(!mainWindow.isShowable())
			mainWindow.showWindow();

		gameThreadPool.execute(gameLoop);
	}

	/**
	 * Queues a {@link Runnable} object to run asynchronously.
	 *
	 * @param r the {@link Runnable} object to run.
	 * @since 1.0.0
	 */
	public synchronized static void queueTask(Runnable r) {
		gameThreadPool.execute(r);
	}

	/**
	 * Sets the main window to <code>mainWindow</code>.
	 *
	 * @param mainWindow the new main window in which the game will be rendered
	 * @since 1.0.0
	 */
	public static void setMainWindow(Window mainWindow) {
		GameLauncher.mainWindow = mainWindow;
	}

	/**
	 * Returns the main window.
	 *
	 * @return the game's main window
	 * @since 1.0.0
	 */
	public synchronized static Window getMainWindow() {
		return mainWindow;
	}

	/**
	 * Enables FPS rendering on the screen.
	 *
	 * @param drawFPS weather to draw or not the FPS
	 * @since 1.0.0
	 */
	public synchronized static void setDrawFPS(boolean drawFPS) {
		GameLauncher.drawFPS = drawFPS;
	}

	/**
	 * Sets the running state of the game.
	 *
	 * @param isGameRunning the new running state of the game.
	 * @since 1.1.0
	 */
	public synchronized static void setGameRunning(boolean isGameRunning) {
		GameLauncher.isGameRunning = isGameRunning;
	}

	/**
	 * Represents the pause menu that appears when the user pauses the game.
	 * 
	 * @author Nuno Pereira
	 * @since 1.2.0
	 */
	public static class UIHudPauseMenuElement extends UIHudElement {

		private UIHudPauseMenuElement() {// make visible from outside game launcher but make unsintantiable
		}

		private static final UIHudButtonElement RESUME_BUTTON = new UIHudButtonElement(0, 0, 200, 75, "Resume",
				Constraints.concat(Constraints.CENTER_HORIZONTAL_CONSTRAINT, Constraints.FROM_BOTTOM_CONSTRAINT),
				new int[] { 0, 0, 20, 0 }) {

			@Override
			protected void processClick(MouseEvent e) {
				GameLauncher.pauseAction.actionPerformed(null);
			}
		};

		private static final UIHudTextElement PAUSED_SCREEN_TITLE = new UIHudTextElement(
				0, 
				0, 
				"Paused", 
				Color.WHITE,
				FontManager.getDefaultFont().deriveFont(100f),
				Constraints.concat(Constraints.CENTER_HORIZONTAL_CONSTRAINT, Constraints.FROM_TOP_CONSTRAINT),
				new int[] { 10, 0, 0, 0 });

		private static final UIHudButtonElement PAUSE_BACK_MENU_BUTTON = new UIHudButtonElement(350, 300, 200, 75,
				"Back to Menu",
				Constraints.concat(Constraints.CENTER_HORIZONTAL_CONSTRAINT, Constraints.FROM_BOTTOM_CONSTRAINT),
				new int[] { 0, 0, 100, 0 }) {

			@Override
			protected void processClick(MouseEvent e) {
				GameLauncher.togglePause();
				GameStateManager.changeGameState("Menu");
			}

		};

		private LinkedList<UIHudElement> elementList = new LinkedList<UIHudElement>();

		static final UIHudPauseMenuElement singleton = new UIHudPauseMenuElement();

		{
			this.drawConstraints = new Constraints(this, Constraints.CENTER_POINT_CONSTRAINT, null);
			this.width = (int) (GameLauncher.getMainWindow().getWidth() * .8);
			this.height = (int) (GameLauncher.getMainWindow().getWidth() * .6);
			this.zIndex = 500;
			RESUME_BUTTON.setZIndex(this.zIndex + 1);
			RESUME_BUTTON.setParentElement(this);
			elementList.add(RESUME_BUTTON);
			PAUSED_SCREEN_TITLE.setZIndex(this.zIndex + 1);
			PAUSED_SCREEN_TITLE.setParentElement(this);
			elementList.add(PAUSED_SCREEN_TITLE);
			PAUSE_BACK_MENU_BUTTON.setZIndex(this.zIndex + 1);
			PAUSE_BACK_MENU_BUTTON.setParentElement(this);
			elementList.add(PAUSE_BACK_MENU_BUTTON);
		}

		/**
		 * Adds the given element to the pause menu.
		 * 
		 * @param element the element to add to the pause menu
		 * @since 1.3.0
		 */
		public static void addPauseMenuElement(UIHudElement element) {
			if (singleton.elementList.contains(element))
				return;
			element.setParentElement(singleton);
			singleton.elementList.add(1, element);
		}

		/**
		 * Removes the element from the pause menu.
		 * 
		 * @param element the element to remove
		 * @since 1.3.0
		 */
		public static void removePauseMenuElement(UIHudElement element) {
			if (!singleton.elementList.contains(element))
				return;
			if (singleton.elementList.size() > 3)
				singleton.elementList.remove(element);
		}

		@Override
		public void render(Graphics2D g) {

			Color startingColor = g.getColor();

			g.setColor(new Color(255, 255, 255, 255 / 2));

			this.x = this.drawConstraints.getXLocation();
			this.y = this.drawConstraints.getYLocation();
			
			g.fillRoundRect(this.x, this.y, this.width, this.height, 15, 15);
			
			for (UIHudElement uiHudElement : elementList) { uiHudElement.render(g); }

			g.setColor(startingColor);
			
		}

		@Override
		public void registerInputListener() {
			for (UIHudElement uiHudElement : elementList) {
				uiHudElement.registerInputListener();
			}
		}

		@Override
		public void removeInputListener() {
			for (UIHudElement uiHudElement : elementList) { uiHudElement.removeInputListener(); }
		}
	}
}
