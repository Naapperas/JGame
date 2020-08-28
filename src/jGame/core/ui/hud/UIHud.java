package jGame.core.ui.hud;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
	private static final List<UIHudElement> HUD_ELEMENTS_TO_REMOVE = new ArrayList<UIHudElement>();

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
	public synchronized static void render(Graphics g) {

		if (!HUD_ELEMENTS_TO_REMOVE.isEmpty()) {
			HUD_ELEMENTS_TO_REMOVE.forEach((element) -> { HUD.remove(element); });
			HUD_ELEMENTS_TO_REMOVE.clear();
		}

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
	}

	/**
	 * Removes a {@link UIHudElement} from the list so it is no longer rendered.
	 * 
	 * @param hudElement the {@link UIHudElement} to be removed
	 * @since 1.0.0
	 */
	public synchronized static void removeHUDUIElement(UIHudElement hudElement) {
		ProgramLogger.writeLog("Removing " + hudElement);
		HUD_ELEMENTS_TO_REMOVE.add(hudElement);
	}

	/**
	 * Registers each element's input listener, if there is one.
	 * 
	 * @since 1.0.0
	 */
	public synchronized static void registerInputListeners() {
		ProgramLogger.writeLog("Registering input listeners for HUD elements");
		HUD.forEach((element) -> { element.registerInputListener(); });
	}
}
