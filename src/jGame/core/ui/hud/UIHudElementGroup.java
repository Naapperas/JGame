package jGame.core.ui.hud;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a group of {@link UIHudElement}s to be grouped in a certain area.
 * This makes it so that any elements inside this are constrained relative to
 * this element, while this is constrained relative to it's parent element (or
 * the window if there is no parent element).
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class UIHudElementGroup extends UIHudElement {

	/**
	 * The internal group of elements of this element group.
	 * 
	 * @since 2.0.0
	 */
	private List<UIHudElement> elementGroup = new LinkedList<UIHudElement>();

	private int paddingTop, paddingRight, paddingBottom, paddingLeft;
	private int marginTop, marginRight, marginBottom, marginLeft;

	/**
	 * Creates an element group in the given position, wit the given dimensions and
	 * using the given constraints. The group stars with no elements.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param constraintType
	 * @param constraintValues
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, int constraintType, int[] constraintValues) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param itemsToAdd
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, List<UIHudElement> itemsToAdd) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, Constraints.NONE, null);
		this.elementGroup.addAll(itemsToAdd);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param constraintType
	 * @param constraintValues
	 * @param itemsToAdd
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, int constraintType, int[] constraintValues,
			List<UIHudElement> itemsToAdd) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
		this.elementGroup.addAll(itemsToAdd);
	}

	/**
	 * 
	 * @param elementToAdd
	 * @since 2.0.0
	 */
	public void addGroupElement(UIHudElement elementToAdd) {
		elementToAdd.parentElement = this;
		elementGroup.add(elementToAdd);
	}

	/**
	 * 
	 * @param elementsToAdd
	 * @since 2.0.0
	 */
	public void addGroupElements(List<UIHudElement> elementsToAdd) {
		elementsToAdd.forEach((x) -> { x.parentElement = this; });
		this.elementGroup.addAll(elementsToAdd);
	}

	/**
	 * 
	 * @param elementGroup
	 * @since 2.0.0
	 */
	public void addGroupElements(UIHudElementGroup elementGroup) {
		this.addGroupElements(elementGroup.elementGroup);
	}

	/**
	 * 
	 * @param paddingTop
	 * @since 2.0.0
	 */
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	/**
	 * 
	 * @param paddingRight
	 * @since 2.0.0
	 */
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/**
	 * 
	 * @param paddingBottom
	 * @since 2.0.0
	 */
	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * 
	 * @param paddingLeft
	 * @since 2.0.0
	 */
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	/**
	 * 
	 * @param marginTop
	 * @since 2.0.0
	 */
	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	/**
	 * 
	 * @param marginRight
	 * @since 2.0.0
	 */
	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	/**
	 * 
	 * @param marginBottom
	 * @since 2.0.0
	 */
	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	/**
	 * 
	 * @param marginLeft
	 * @since 2.0.0
	 */
	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	/**
	 * 
	 * @param vertMargin
	 * @since 2.0.0
	 */
	public void setVerticalMargin(int vertMargin) {
		this.marginTop = this.marginBottom = vertMargin;
	}

	/**
	 * 
	 * @param horizMargin
	 * @since 2.0.0
	 */
	public void setHorizontalMargin(int horizMargin) {
		this.marginRight = this.marginLeft = horizMargin;
	}

	/**
	 * 
	 * @param vertPadding
	 * @since 2.0.0
	 */
	public void setVerticalPadding(int vertPadding) {
		this.paddingTop = this.paddingBottom = vertPadding;
	}

	/**
	 * 
	 * @param horizPadding
	 * @since 2.0.0
	 */
	public void setHorizontalPadding(int horizPadding) {
		this.paddingLeft = this.paddingRight = horizPadding;
	}

	/**
	 * 
	 * @param margin
	 * @since 2.0.0
	 */
	public void setMargin(int margin) {
		this.marginBottom = this.marginLeft = this.marginRight = this.marginTop = margin;
	}

	/**
	 * 
	 * @param padding
	 * @since 2.0.0
	 */
	public void setPadding(int padding) {
		this.paddingBottom = this.paddingLeft = this.paddingRight = this.paddingTop = padding;
	}

	@Override
	public void render(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerInputListener() {
		elementGroup.forEach(UIHudElement::registerInputListener);
	}

	@Override
	public void removeInputListener() {
		elementGroup.forEach(UIHudElement::removeInputListener);
	}

}
