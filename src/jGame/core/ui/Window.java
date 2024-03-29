package jGame.core.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import jGame.core.entity.Entity;
import jGame.core.launcher.GameLauncher;
import jGame.core.ui.hud.UIHudElement;
import jGame.core.utils.MathUtils;
import jGame.logging.ProgramLogger;

/**
 * The default class for presenting displays on the screen. The default
 * resolution is Full HD (1920x1080), so the dimensions of the window frame are
 * automatically adjusted based on the screen resolution, so the user has the
 * impression of the window being the same size on any screen resolution.
 *
 * @see WindowUtils
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class Window {

	private volatile JFrame windowFrame = null;

	// define boundaries for window. (Maybe change through a properties file ?)
	private static final int SIZE_OFFSET = 50;
	private int width = 0, height = 0;

	// the canvas object in which the game is going to be rendered
	private volatile Canvas windowCanvas = new Canvas();

	private boolean fullScreen = false;

	// static boolean to check if main window has been set, so we don't add
	// incorrect behavior on future window objects
	private static boolean mainWindowSet = false;

	/**
	 * Returns the window's canvas.
	 *
	 * @return the window's canvas object.
	 * @since 1.0.0
	 */
	public Canvas getWindowCanvas() {
		return windowCanvas;
	}

	/**
	 * Instantiates a window object to be displayed. The constructor calls
	 * {@link #doSyncUIUpdate(Runnable)} with a lambda expression containing
	 * {@link #init(int, int, CharSequence)}, moving the window initialization to
	 * the AWT Event Thread. <br>
	 * <br>
	 * The actual dimensions of the window wont be the ones passed in to the
	 * constructor, but rather scaled based of the screen size relative to the
	 * default size.
	 *
	 * @param width  the width of the window
	 * @param height the height of the window
	 * @param title  the title of the window
	 * @see WindowUtils
	 * @see WindowUtils#HORIZONTAL_SCALE
	 * @see WindowUtils#VERTICAL_SCALE
	 * @since 1.0.0
	 */
	public Window(int width, int height, CharSequence title) {
		try {
			doSyncUIUpdate(() -> { init(width, height, title); });
		} catch (InvocationTargetException | InterruptedException e) {
			ProgramLogger.writeErrorLog(e);
		}
	}

	/**
	 * Initializes the window with the given <code>width</code>, <code>height</code>
	 * and <code>title</code>. This method is called on the AWT Event Thread.
	 *
	 * @param width  the width of the window
	 * @param height the height of the window
	 * @param title  the title of the window
	 * @see #Window(int, int, CharSequence)
	 * @since 1.0.0
	 */
	private void init(int width, int height, CharSequence title) {

		ProgramLogger.writeLog("Initializing window.");

		//if window size is greater than the screen size, collision is all goofy, enforce window size to a maximum of screen size
		this.width = (int) MathUtils.clamp(width, 0, WindowUtils.WindowParams.getScreenResolution().getWidth());
		this.height = (int) MathUtils.clamp(height, 0, WindowUtils.WindowParams.getScreenResolution().getHeight());

		//initialize frame with title
		windowFrame = new JFrame(title.toString());

		if (this.width == WindowUtils.WindowParams.getScreenResolution().getWidth()
				&& this.height == WindowUtils.WindowParams.getScreenResolution().getHeight())
			windowFrame.setExtendedState(windowFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

		//set dimensions. Height calculations that scale based on the width are responsibility of the client
		Dimension preferredSize = new Dimension(), maxSize = new Dimension(), minSize = new Dimension();
		preferredSize.setSize(width * WindowUtils.HORIZONTAL_SCALE, height * WindowUtils.VERTICAL_SCALE);
		maxSize.setSize((width + SIZE_OFFSET) * WindowUtils.HORIZONTAL_SCALE, (height + SIZE_OFFSET) * WindowUtils.VERTICAL_SCALE);
		minSize.setSize((width - SIZE_OFFSET) * WindowUtils.HORIZONTAL_SCALE, (height - SIZE_OFFSET) * WindowUtils.VERTICAL_SCALE);

		windowFrame.setPreferredSize(preferredSize);
		windowFrame.setMinimumSize(minSize);
		windowFrame.setMaximumSize(maxSize);

		windowFrame.setResizable(false);

		//force close operation. Default to dispose for any window and make exit on main window
		windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// center the window on the center of the screen
		windowFrame.setLocationRelativeTo(null);

		windowCanvas.setBackground(Color.BLACK);
		windowCanvas.setBounds(0, 0, getWidth(), getWidth());

		addComponent(windowCanvas);

		// wrapping up
		windowFrame.pack();
		windowFrame.revalidate();

		windowFrame.paint(null);

	}

	/**
	 * Makes the window visible. The actual code that sets the window visible is
	 * called on the AWT Event Thread
	 *
	 * @see java.awt.EventQueue
	 * @since 1.0.0
	 */
	public void showWindow() {
		try {
			doSyncUIUpdate(() -> {
				windowFrame.setVisible(true);
				windowCanvas.requestFocus();
			});
		} catch (InvocationTargetException | InterruptedException e) {
			ProgramLogger.writeErrorLog(e);
		}
	}

	/**
	 * Adds the specified component to the window.
	 *
	 * @param c the component to add
	 * @since 1.0.0
	 */
	public void addComponent(Component c) {
		windowFrame.add(c);
	}

	/**
	 * Queues a {@link Runnable} to run asynchronously on the AWT Event Thread.
	 *
	 * @param r the {@link Runnable} to queue
	 * @see #doSyncUIUpdate(Runnable)
	 * @see SwingUtilities#invokeLater(Runnable)
	 * @since 1.0.0
	 */
	public synchronized void doAsyncUIUpdate(Runnable r) {
		SwingUtilities.invokeLater(r);
	}

	/**
	 * Queues a {@link Runnable} to run synchronously on the AWT Event Thread.
	 *
	 * @param r the instance of {@code Runnable}
	 * @throws InterruptedException      if we're interrupted while waiting for the
	 *                                   event dispatching thread to finish
	 *                                   executing <code>r.run()</code>
	 * @throws InvocationTargetException if an exception is thrown while running
	 *                                   <code>r</code>
	 * @see #doAsyncUIUpdate(Runnable)
	 * @see SwingUtilities#invokeAndWait(Runnable)
	 * @since 1.0.0
	 */
	public synchronized void doSyncUIUpdate(Runnable r) throws InvocationTargetException, InterruptedException {
		SwingUtilities.invokeAndWait(r);
	}

	/**
	 * Returns weather the window is visible or not
	 *
	 * @return the visibility of {@link #windowFrame}
	 * @since 1.0.0
	 */
	public boolean isShowable() {
		return windowFrame.isVisible();
	}

	/**
	 * Returns the <code>width</code> of the window
	 *
	 * @return the <code>width</code> of the window
	 * @since 1.0.0
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the <code>height</code> of the window
	 *
	 * @return the <code>height</code> of the window
	 * @since 1.0.0
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets this <code>Window</code> object as the main window to be used by the
	 * {@link GameLauncher}
	 *
	 * @see JFrame#setDefaultCloseOperation(int)
	 * @since 1.0.0
	 */
	public void setMainWindow() {

		if (mainWindowSet)
			return;

		this.windowFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.windowFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				GameLauncher.setGameRunning(false);
			}

		});

		mainWindowSet = true;
	}

	/**
	 * Adds a keyboard listener to this windows {@link #windowCanvas}
	 *
	 * @param inputListener the {@link KeyAdapter} object to add
	 * @param entity        the entity whose listener is being added
	 * @see KeyAdapter
	 * @since 1.0.0
	 */
	public void addInputListener(KeyAdapter inputListener, Entity entity) {
		if (Arrays.asList(windowCanvas.getListeners(KeyListener.class)).contains(inputListener))
			return;
		ProgramLogger.writeLog("Adding key input listener for " + entity);
		windowCanvas.addKeyListener(inputListener);
	}

	/**
	 * Adds a mouse listener to this windows {@link #windowCanvas}
	 *
	 * @param mouseInputListener the {@link MouseAdapter} object to add
	 * @param hudElement         the HUD element whose listener is being added
	 * @see MouseAdapter
	 * @since 1.0.0
	 */
	public void addMouseInputListener(MouseAdapter mouseInputListener, UIHudElement hudElement) {
		if (Arrays.asList(windowCanvas.getListeners(MouseListener.class)).contains(mouseInputListener))
			return;
		ProgramLogger.writeLog("Adding mouse input listener for " + hudElement);
		windowCanvas.addMouseListener(mouseInputListener);
	}

	/**
	 * Removes the given input listener in order to stop receiving events.
	 *
	 * @param inputListener the input listener to remove
	 * @param entity        the entity whose listener is being removed
	 * @since 1.0.0
	 */
	public void removeInputListener(KeyAdapter inputListener, Entity entity) {
		ProgramLogger.writeLog("Removing key input listener for " + entity);
		windowCanvas.removeKeyListener(inputListener);
	}

	/**
	 * Removes the given mouse input listener in order to stop receiving events.
	 *
	 * @param inputListener the input listener to remove
	 * @param hudElement    the HUD element whose listener is being removed
	 * @since 1.0.0
	 */
	public void removeMouseInputListener(MouseAdapter inputListener, UIHudElement hudElement) {
		ProgramLogger.writeLog("Removing mouse input listener for " + hudElement);
		windowCanvas.removeMouseListener(inputListener);
	}

	/**
	 * Adds a key binding to the given {@link Action}.
	 *
	 * @param action    the action to perform when a key is pressed
	 * @param keyStroke the key to trigger the action when pressed
	 * @param key       the string denoting the action
	 * @since 1.1.0
	 */
	public void addAction(Action action, KeyStroke keyStroke, String key) {
		((JComponent) windowFrame.getContentPane()).getInputMap().put(keyStroke, key);
		((JComponent) windowFrame.getContentPane()).getActionMap().put(key, action);
	}

	/**
	 * Removes a key binding from the given {@link Action}.
	 *
	 * @param keyStroke the key to trigger the action when pressed
	 * @param key       the string denoting the action
	 * @since 1.1.0
	 */
	public void removeAction(KeyStroke keyStroke, String key) {
		((JComponent) windowFrame.getContentPane()).getInputMap().remove(keyStroke);
		((JComponent) windowFrame.getContentPane()).getActionMap().remove(key);
	}

	/**
	 * Adds a key input listener associated with the given {@code UIHudElement} to
	 * this window.
	 * 
	 * @param keyInputListener the listener of the given {@code element}
	 * @param element          the element whose listener is being registered
	 * @since 1.2.0
	 */
	public void addInputListener(KeyAdapter keyInputListener, UIHudElement element) {
		if (Arrays.asList(windowCanvas.getListeners(KeyListener.class)).contains(keyInputListener))
			return;
		ProgramLogger.writeLog("Adding key input listener for " + element);
		windowCanvas.addKeyListener(keyInputListener);
	}

	/**
	 * Removes a key input listener associated with the given {@code UIHudElement}
	 * to this window.
	 * 
	 * @param keyInputListener the listener of the given {@code element}
	 * @param element          the element whose listener is being removed
	 * @since 1.2.0
	 */
	public void removeInputListener(KeyAdapter keyInputListener, UIHudElement element) {
		ProgramLogger.writeLog("Removing key input listener for " + element);
		windowCanvas.removeKeyListener(keyInputListener);
	}
	
	/**
	 * Serializes the window.
	 * 
	 * @param serializer the serializer object that registers this component
	 * @param indent the indent level
	 * @throws IOException if an IOException
	 * @since 2.0.0
	 */
	public void serialize(BufferedWriter serializer, int indent) throws IOException {
		
		serializer.write("Window.title=");
		serializer.write(this.windowFrame.getTitle());
		serializer.write(System.lineSeparator());
		serializer.write("Window.dimensions={WIDTH:");
		serializer.write(Integer.toString(this.getWidth()));
		serializer.write(", HEIGHT:");
		serializer.write(Integer.toString(this.getHeight()));
		serializer.write('}');
		serializer.write(System.lineSeparator());
		serializer.write("Window.isMainWindow=");
		serializer.write(this.windowFrame.getDefaultCloseOperation() == JFrame.DO_NOTHING_ON_CLOSE ? "true" : "false");
		serializer.write(System.lineSeparator());
		serializer.write("Window.isShowable=");
		serializer.write(this.isShowable() ? "true" : "false");
		serializer.write(System.lineSeparator());
		
	}

	/**
	 * Toggles the window to be fullscreen or not.
	 * 
	 * @since 1.1.0
	 */
	public void toggleFullscreen() {
		
		if (!fullScreen) {
			windowFrame.setVisible(false);
			windowFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			windowFrame.setUndecorated(true);
			windowFrame.setVisible(true);
			fullScreen = true;
		} else {
			windowFrame.setVisible(false);
			windowFrame.setExtendedState(JFrame.NORMAL);
			windowFrame.setUndecorated(false);
			windowFrame.setVisible(true);
			fullScreen = false;
		}
	}
}
