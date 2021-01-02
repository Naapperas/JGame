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

	/*
	 * Padding is used to set the inner bounds of the "element area", i.e., elements
	 * are placed in a space smaller than the actual element group size: how smaller
	 * is determined by the padding.
	 */
	private int paddingTop, paddingRight, paddingBottom, paddingLeft;

	/*
	 * Margin is used to space elements inside the element group: how separated is
	 * determined by the margin.
	 */
	private int marginTop, marginRight, marginBottom, marginLeft;

	/**
	 * The slack, in pixels, to give when sizing this element: if we can fit another
	 * element within the group's width plus this threshold, increase the width.
	 * 
	 * @since 2.0.0
	 */
	public static final int SIZE_THRESHOLD = 15;

	/**
	 * Creates an element group in the given position, with the given dimensions and
	 * using the given constraints. The group stars with no elements.
	 * 
	 * @param x                the horizontal position of the element group
	 * @param y                the vertical position of the element group
	 * @param width            the width of the element group
	 * @param height           the height of the element group
	 * @param constraintType   the type of constraint to apply
	 * @param constraintValues the values to apply when constraining this element
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, int constraintType, int[] constraintValues) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
	}

	/**
	 * Creates an element group in the given position, with the given dimensions,
	 * out of the given list of {@link UIHudElement}s.
	 * 
	 * @param x          the horizontal position of the element group
	 * @param y          the vertical position of the element group
	 * @param width      the width of the element group
	 * @param height     the height of the element group
	 * @param itemsToAdd the initial elements to add to the group
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, List<UIHudElement> itemsToAdd) {
		super(x, y, width, height);
		this.drawConstraints = Constraints.defaultFor(this);
		this.elementGroup.addAll(itemsToAdd);
	}

	/**
	 * Creates an element group in the given position, with the given dimensions and
	 * using the given constraints, out of the given list of {@link UIHudElement}s.
	 * 
	 * @param x                the horizontal position of the element group
	 * @param y                the vertical position of the element group
	 * @param width            the width of the element group
	 * @param height           the height of the element group
	 * @param constraintType   the type of constraint to apply
	 * @param constraintValues the values to apply when constraining this element
	 * @param itemsToAdd       the initial elements to add to the group
	 * @since 2.0.0
	 */
	public UIHudElementGroup(int x, int y, int width, int height, int constraintType, int[] constraintValues,
			List<UIHudElement> itemsToAdd) {
		super(x, y, width, height);
		this.drawConstraints = new Constraints(this, constraintType, constraintValues);
		this.elementGroup.addAll(itemsToAdd);
	}

	/**
	 * Adds an element to this element to group.
	 * 
	 * @param elementToAdd the element to add to the element group
	 * @since 2.0.0
	 */
	public void addGroupElement(UIHudElement elementToAdd) {
		elementToAdd.parentElement = this;
		elementGroup.add(elementToAdd);
	}

	/**
	 * Adds a collection of elements to this element group.
	 * 
	 * @param elementsToAdd the list of element to add to this element group.
	 * @since 2.0.0
	 */
	public void addGroupElements(List<UIHudElement> elementsToAdd) {
		elementsToAdd.forEach((x) -> { x.parentElement = this; });
		this.elementGroup.addAll(elementsToAdd);
	}

	/**
	 * Adds to this element group all elements of the {@code elementGroup} passed
	 * in.
	 * 
	 * @param elementGroup the {@link UIHudElementGroup} object whose elements are
	 *                     to be added to this element group.
	 * @since 2.0.0
	 */
	public void addGroupElements(UIHudElementGroup elementGroup) {
		if (elementGroup != this)
			this.addGroupElements(elementGroup.elementGroup);
	}

	/**
	 * Sets the top padding on this element group.
	 * 
	 * @param paddingTop the new value for the top padding
	 * @since 2.0.0
	 */
	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	/**
	 * Sets the right padding on this element group.
	 * 
	 * @param paddingRight the new value of the right padding
	 * @since 2.0.0
	 */
	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	/**
	 * Sets the bottom padding on this element group.
	 * 
	 * @param paddingBottom the new value of the bottom padding
	 * @since 2.0.0
	 */
	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	/**
	 * Sets the left padding on this element group.
	 * 
	 * @param paddingLeft the new value of the left padding
	 * @since 2.0.0
	 */
	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	/**
	 * Sets the top margin on this element group.
	 * 
	 * @param marginTop the new value for the top margin
	 * @since 2.0.0
	 */
	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	/**
	 * Sets the right margin for this element group.
	 * 
	 * @param marginRight the new value for the right margin
	 * @since 2.0.0
	 */
	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	/**
	 * Sets the bottom margin for this element group.
	 * 
	 * @param marginBottom the new value for the bottom margin
	 * @since 2.0.0
	 */
	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	/**
	 * Sets the left margin for this element group.
	 * 
	 * @param marginLeft the new value for the left margin
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
