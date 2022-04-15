package jGame.core.ui.hud;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import jGame.core.launcher.GameLauncher;
import jGame.logging.ProgramLogger;

/**
 * This class represents a Heads Up Display (HUD) to be rendered on the screen
 * on top of the game after every entity has been updated and rendered.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 */
public class UIHud {

	// list of all visual elements to render as an HUD
	private static final List<UIHudElement> HUD = new ArrayList<UIHudElement>();

	// make uninstantiatable
	private UIHud() {
	}

	/**
	 * Renders all {@link UIHudElement}s to the screen
	 * 
	 * @param g the {@link Graphics} object responsible for rendering the
	 *          {@link UIHudElement}s
	 * @since 1.0.0
	 */
	public synchronized static void render(Graphics2D g) {

		if (!HUD.isEmpty())
			HUD.forEach((element) -> { element.render(g); });
	}

	/**
	 * Adds a {@link UIHudElement} to this classe's internal list in order to be
	 * rendered, if it hasn't been already added.
	 * 
	 * @param hudElement the {@link UIHudElement} to be added
	 * @since 1.0.0
	 */
	public synchronized static void addHUDUIElement(UIHudElement hudElement) {
		if (HUD.contains(hudElement))
			return;

		ProgramLogger.writeLog("Adding " + hudElement);
		HUD.add(hudElement);

		if (HUD.size() > 1) // no need to waste time sorting one element
			sortElements(true);

	}

	/**
	 * Sorts all the elements in this HUD according to their {@code zIndex}
	 * 
	 * @param order the order in which to sort element: {@code true} for regular,
	 *              {@code false} for backwards
	 * @since 1.2.0
	 */
	public static void sortElements(final boolean order) {
		ProgramLogger.writeLog("Sorting HUD elements.");
		HUD.sort(new Comparator<UIHudElement>() {

			@Override
			public int compare(UIHudElement o1, UIHudElement o2) {
				if (o1.zIndex > o2.zIndex)
					return order ? 1 : -1;
				else if (o1.zIndex < o2.zIndex)
					return order ? -1 : 1;
				else
					return 0;
			
			}
		});
	}

	/**
	 * Removes a {@link UIHudElement} from the list so it is no longer rendered.
	 * 
	 * @param hudElement the {@link UIHudElement} to be removed
	 * @since 1.0.0
	 */
	public synchronized static void removeHUDUIElement(UIHudElement hudElement) {
		if (HUD.remove(hudElement))
			ProgramLogger.writeLog("Removed " + hudElement);
	}

	/**
	 * Registers each element's input listener, if there is one.
	 * 
	 * @since 1.0.0
	 */
	public synchronized static void registerInputListeners() {
		ProgramLogger.writeLog("Registering input listeners for HUD elements");
		for (int i = HUD.size() - 1; i >= 0; i--) { // events need to be checked from top to bottom, add listeners
													// in reverse order
			HUD.get(i).registerInputListener();
		}
	}

	public synchronized static void updateInputListeners() {
		ProgramLogger.writeLog("Updating input listeners for HUD elements");
		Arrays.asList(GameLauncher.getMainWindow().getWindowCanvas().getListeners(MouseListener.class)).clear();
		Arrays.asList(GameLauncher.getMainWindow().getWindowCanvas().getListeners(KeyListener.class)).clear();
		for (int i = HUD.size() - 1; i >= 0; i--) { // events need to be checked from top to bottom, add listeners
													// in reverse order
			HUD.get(i).registerInputListener();
		}
	}

	/**
	 * Returns the HUD elements.
	 * 
	 * @return the HUD elements
	 * @since 1.0.0
	 */
	public static List<UIHudElement> getHud() {
		return HUD;
	}
}
