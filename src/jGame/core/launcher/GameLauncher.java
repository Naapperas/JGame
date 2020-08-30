package jGame.core.launcher;

import java.awt.Color;
import java.awt.Graphics;
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
	
	// since 1.0.0
	private static UIHudElement FPSCounter = new UIHudTextElement(7, 17, "FPS: ", Color.GREEN);
	private static int fps = 0;
	
	// since 1.1.0
	private static UIHudElement pauseMenu = null; // we are going to be set in the launch method, when the window is
													// already set
	private static boolean pause = false;
	private static Action pauseAction = new AbstractAction() {

		private static final long serialVersionUID = -8628054626023408846L;

		@Override
		public void actionPerformed(ActionEvent e) {
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

	// the frameworks main loop
	private static Runnable gameLoop = () -> {
		ProgramLogger.writeLog("Starting game loop.");
		long lastTime = System.nanoTime();
		double desiredFPS = 120;
		double nanoSecondsPerFrame = 1000000000/desiredFPS;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		UIHud.addHUDUIElement(FPSCounter);

		getMainWindow().addAction(pauseAction, KeyStroke.getKeyStroke('p'), "pause");
		
		pauseMenu.registerInputListener();
		UIHud.addHUDUIElement(pauseMenu);

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
		//processGameTermination();
		return;
	};
	
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
		
		Graphics g = bs.getDrawGraphics();
		
		//insert rendering code here
		
		g.clearRect(0, 0, mainWindow.getWidth(), mainWindow.getHeight());
		g.setColor(mainWindow.getWindowCanvas().getBackground());
		
		//TODO: update rendering system
		EntityManager.renderEntities(g);

		//draw FPS on screen
		if (drawFPS)
			((UIHudTextElement) FPSCounter).setTextToDisplay("FPS: " + fps);
		
		UIHud.render(g);

		g.dispose();
		g = null;
		bs.show();
		bs = null;
	}

	/**
	 * Updates all the entities in the game before rendering them.
	 * 
	 * @since 1.0.0
	 */
	private static void tick() {

		//TODO: update ticking system
		EntityManager.tickEntities();
		
	}

	/**
	 * Launches the game, rendering all screen events on the {@link #mainWindow}.
	 * 
	 * @since 1.0.0
	 */
	public static void launchGame() {
		
		ProgramLogger.writeLog("Launching game!");

		pauseMenu = new UIHudButtonElement((int) getMainWindow().getWindowCanvas().getBounds().getWidth() - 60, 10, 50,
				20, "Pause") {

			@Override
			protected void processClick(MouseEvent e) {
				pause = !pause;
			}

		};

		isGameRunning = true;
		
		EntityManager.registerInputListeners();
		UIHud.registerInputListeners();

		mainWindow.setMainWindow();
		
		if(!mainWindow.isShowable())
			mainWindow.showWindow();
		
		gameThreadPool.execute(gameLoop);
	}
	
	/**
	 * Launches the game, setting the main window to be <code>mainWindow</code>.
	 * 
	 * @param mainWindow the window to be used for rendering the game.
	 * @since 1.0.0
	 */
	public static void launchGame(Window mainWindow) {
		
		setMainWindow(mainWindow);
		
		launchGame();	
	}
	
	/**
	 * Queues a {@link Runnable} object to run asynchronously.
	 * 
	 * @param r the {@link Runnable} object to run.
	 * @since 1.0.0
	 */
	public static void queueTask(Runnable r) {
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
	public static Window getMainWindow() {
		return mainWindow;
	}

	/**
	 * Enables FPS rendering on the screen.
	 * 
	 * @param drawFPS weather to draw or not the FPS
	 * @since 1.0.0
	 */
	public static void setDrawFPS(boolean drawFPS) {
		GameLauncher.drawFPS = drawFPS;
	}
}
