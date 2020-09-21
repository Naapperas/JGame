package jGame.core.launcher;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import jGame.core.entity.EntityManager;
import jGame.core.ui.Window;
import jGame.core.ui.hud.Constraints;
import jGame.core.ui.hud.UIHud;
import jGame.core.ui.hud.UIHudButtonElement;
import jGame.core.ui.hud.UIHudElement;
import jGame.core.ui.hud.UIHudTextElement;
import jGame.core.utils.properties.PropertiesManager;
import jGame.logging.ProgramLogger;

/**
 * This class is designed to be the entry-point for any game based on the JGame
 * framework
 *
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public class GameLauncher {

	static {
		// initializes all the relevant/required properties on class loading to be used
		// at execution
		try {
			PropertiesManager.fetchProperties("");
		} catch (Exception e) {
			ProgramLogger.writeErrorLog(e, "Error fetching properties.");
		}

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

	public static int getFPS() {
		return fps;
	}

	// since 1.1.0
	private static UIHudElement pauseMenu = new UIHudButtonElement(0, 0, 50, 20, "Pause",
			Constraints.concat(Constraints.FROM_TOP_CONSTRAINT, Constraints.FROM_RIGHT_CONSTRAINT),
			new int[] { 10, 10, 0, 0 }) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4372277764333169074L;

		@Override
		protected void processClick(MouseEvent e) {
			pause = !pause;
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

	private static boolean pause = false;
	private static Action pauseAction = new AbstractAction() {

		private static final long serialVersionUID = -8628054626023408846L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ProgramLogger.writeLog("Pausing game");
			pause = !pause;
		}
	};

	/**
	 * Unpauses the game.
	 *
	 * @since 1.1.0
	 */
	public static void unpause() {
		pause = false;
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

		UIHud.addHUDUIElement(FPSCounter);

		GameStateManager.addUIHudElementToBoard(pauseMenu);

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
}
