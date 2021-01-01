package jGame.core.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * This class provides a set of utility Math methods that can be potentially
 * useful in game logic.
 * 
 * @author Nuno Pereira
 * @since 1.0.0
 *
 */
public final class MathUtils {

	// mathematical constants
	public static final double PI = Math.PI, HALF_PI = PI / 2, TWO_PI = 2 * PI, E = Math.E;

	// sign (and overloads)

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(int number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(float number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(double number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(long number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(short number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	/**
	 * Returns the sign of {@code number}.
	 * 
	 * @param number the number to check the sign of
	 * @return the sign of {@code number}
	 * @since 1.0.0
	 */
	public static int sign(byte number) {

		if (number < 0)
			return -1;
		else if (number > 0)
			return 1;
		else
			return 0;
	}

	// clamp (and overloads)

	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @since 1.0.0
	 */
	public static int clamp(int number, int min, int max) {
		if(number < min) return min;
		else if (number > max) return max;
		else return number;
	}

	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @param number the value to clamp+
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @since 1.0.0
	 */
	public static long clamp(long number, long min, long max) {
		if(number < min) return min;
		else if (number > max) return max;
		else return number;
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @since 1.0.0
	 */
	public static float clamp(float number, float min, float max) {
		if(number < min) return min;
		else if (number > max) return max;
		else return number;
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @since 1.0.0
	 */
	public static double clamp(double number, double min, double max) {
		if(number < min) return min;
		else if (number > max) return max;
		else return number;
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @deprecated use <code>clamp()</code> instead (method names changed to keep
	 *             compatibility, the algorithm is the thing that changed,
	 *             decreasing computation interval)
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @see Math#max(int, int)
	 * @see Math#min(int, int)
	 * @since 1.0.0
	 */
	@Deprecated
	public static int clamp0(int number, int min, int max) {
		return java.lang.Math.max(min, java.lang.Math.min(max, number));
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @deprecated use <code>clamp()</code> instead (method names changed to keep
	 *             compatibility, the algorithm is the thing that changed,
	 *             decreasing computation interval)
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @see Math#max(double, double)
	 * @see Math#min(double, double)
	 * @since 1.0.0
	 */
	@Deprecated
	public static double clamp0(double number, double min, double max) {
		return java.lang.Math.max(min, java.lang.Math.min(max, number));
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @deprecated use <code>clamp()</code> instead (method names changed to keep
	 *             compatibility, the algorithm is the thing that changed,
	 *             decreasing computation interval)
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @see Math#max(long, long)
	 * @see Math#min(long, long)
	 * @since 1.0.0
	 */
	@Deprecated
	public static long clamp0(long number, long min, long max) {
		return java.lang.Math.max(min, java.lang.Math.min(max, number));
	}
	
	/**
	 * Clamps a number to the fit between a certain range of numbers
	 * 
	 * @deprecated use <code>clamp()</code> instead (method names changed to keep
	 *             compatibility, the algorithm is the thing that changed,
	 *             decreasing computation interval)
	 * @param number the value to clamp
	 * @param min    the minimum value <code>number</code> can take;
	 * @param max    the maximum value <code>number</code> can take;
	 * @return the <i>clamped</i> value of <code>number</code>
	 * @see Math#max(float, float)
	 * @see Math#min(float, float)
	 * @since 1.0.0
	 */
	@Deprecated
	public static float clamp0(float number, float min, float max) {
		return java.lang.Math.max(min, java.lang.Math.min(max, number));
	}
	
	//map method (and overloads)
	
	/**
	 * Maps a value between a certain range to another, different range. This
	 * methods clamps the value to the origin range but still throws an exception if
	 * attempting to map a value that's outside of the origin range.
	 * 
	 * @param number     the number to get mapped
	 * @param originMin  the minimum value <code>number</code> can (and must) take
	 * @param originMax  the maximum value <code>number</code> can (and must) take
	 * @param destinyMin the minimum value that the output can take
	 * @param destinyMax the maximum value that the output can take
	 * @return the new value, mapped from the origin range to the destiny range
	 * @throws IllegalArgumentException if the value isn't in the "origin" range
	 * @see #clamp(int, int, int)
	 * @since 1.0.0
	 * @deprecated due to non-floating nature, slopes would be calculated in integer
	 *             form and lead to wrong results
	 */
	@Deprecated
	public static float map(int number, int originMin, int originMax, int destinyMin, int destinyMax) throws IllegalArgumentException{
		
		if (number < originMin || number > originMax)
			throw new IllegalArgumentException("Value must between originMin and originMax");
		
		//despite checking, clamp the number just to be safe
		number = clamp(number, originMin, originMax);
		
		float slope = (destinyMax - destinyMin) / (originMax - originMin);
		float offset = destinyMin - slope * originMin;
		
		return slope * number + offset;
	}
	
	/**
	 * Maps a value between a certain range to another, different range. This
	 * methods clamps the value to the origin range but still throws an exception if
	 * attempting to map a value that's outside of the origin range.
	 * 
	 * @param number     the number to get mapped
	 * @param originMin  the minimum value <code>number</code> can (and must) take
	 * @param originMax  the maximum value <code>number</code> can (and must) take
	 * @param destinyMin the minimum value that the output can take
	 * @param destinyMax the maximum value that the output can take
	 * @return the new value, mapped from the origin range to the destiny range
	 * @throws IllegalArgumentException if the value isn't in the "origin" range
	 * @see #clamp(long, long, long)
	 * @since 1.0.0
	 * @deprecated due to non-floating nature, slopes would be calculated in integer
	 *             form and lead to wrong results
	 */
	@Deprecated
	public static float map(long number, long originMin, long originMax, long destinyMin, long destinyMax) throws IllegalArgumentException{
		
		if (number < originMin || number > originMax)
			throw new IllegalArgumentException("Value must between originMin and originMax");
	
		//despite checking, clamp the number just to be safe
		number = clamp(number, originMin, originMax);
		
		float slope = (destinyMax - destinyMin)/(originMax-originMin);
		float offset = destinyMin - slope*originMin; 
		
		return slope * number + offset;
	}
	
	/**
	 * Maps a value between a certain range to another, different range. This
	 * methods clamps the value to the origin range but still throws an exception if
	 * attempting to map a value that's outside of the origin range.
	 * 
	 * @param number     the number to get mapped
	 * @param originMin  the minimum value <code>number</code> can (and must) take
	 * @param originMax  the maximum value <code>number</code> can (and must) take
	 * @param destinyMin the minimum value that the output can take
	 * @param destinyMax the maximum value that the output can take
	 * @return the new value, mapped from the origin range to the destiny range
	 * @throws IllegalArgumentException if the value isn't in the "origin" range
	 * @see #clamp(float, float, float)
	 * @since 1.0.0
	 */
	public static float map(float number, float originMin, float originMax, float destinyMin, float destinyMax) throws IllegalArgumentException{
		
		if (number < originMin || number > originMax)
			throw new IllegalArgumentException("Value must between originMin and originMax");
		
		//despite checking, clamp the number just to be safe
		number = clamp(number, originMin, originMax);
		
		float slope = (destinyMax - destinyMin)/(originMax-originMin);
		float offset = destinyMin - slope*originMin; 
		
		return slope * number + offset;
	}
	
	/**
	 * Maps a value between a certain range to another, different range. This
	 * methods clamps the value to the origin range but still throws an exception if
	 * attempting to map a value that's outside of the origin range. Due to variable
	 * typing, this method returns a <code>double</code> instead of a
	 * <code>float</code>.
	 * 
	 * @param number     the number to get mapped
	 * @param originMin  the minimum value <code>number</code> can (and must) take
	 * @param originMax  the maximum value <code>number</code> can (and must) take
	 * @param destinyMin the minimum value that the output can take
	 * @param destinyMax the maximum value that the output can take
	 * @return the new value, mapped from the origin range to the destiny range
	 * @throws IllegalArgumentException if the value isn't in the "origin" range
	 * @see #clamp(double, double, double)
	 * @since 1.0.0
	 */
	public static double map(double number, double originMin, double originMax, double destinyMin, double destinyMax) throws IllegalArgumentException{
		
		if (number < originMin || number > originMax)
			throw new IllegalArgumentException("Value must between originMin and originMax");
		
		//despite checking, clamp the number just to be safe
		number = clamp(number, originMin, originMax);
		
		double slope = (destinyMax - destinyMin)/(originMax-originMin);
		double yOffset = destinyMin - slope*originMin; 
		
		return slope*number + yOffset;
	}

	/**
	 * 
	 * @author nunoa
	 * @since 2.0.0
	 */
	public interface ReduceLambda{

		/**
		 * @since 2.0.0
		 */
		public static final ReduceLambda SUM_INT = (x, y) -> { return x.intValue() + y.intValue(); };

		/**
		 * @since 2.0.0
		 */
		public static final ReduceLambda PRODUCT_INT = (x, y) -> { return x.intValue() * y.intValue(); };

		/**
		 * @since 2.0.0
		 */
		public static final ReduceLambda SUBTRACTION_INT = (x, y) -> { return x.intValue() - y.intValue(); };
		public Number accept(Number x, Number y);
	}

	/**
	 * Inspired by Python's built-in 'reduce' function.
	 * 
	 * @param nums
	 * @param reduceFunc
	 * @return
	 * 
	 * @since 2.0.0
	 */
	public static Number reduce(Number[] nums, ReduceLambda reduceFunc) {
		Number accumulator = reduceFunc.accept(nums[0], nums[1]);

		for (int i = 2; i < nums.length; i++)
			accumulator = reduceFunc.accept(accumulator, nums[i]);

		return accumulator;
	}

	/**
	 * Inspired by Python's built-in 'reduce' function.
	 * 
	 * @param nums
	 * @param reduceFunc
	 * @return
	 * 
	 * @since 2.0.0
	 */
	public static Number reduce(Collection<Number> nums, ReduceLambda reduceFunc) {

		Iterator<Number> numsIterator = nums.iterator();

		System.out.println(nums.size());

		Number accumulator = reduceFunc.accept(numsIterator.next(), numsIterator.next());

		System.out.println(accumulator);

		for (Number n = numsIterator.next(); numsIterator.hasNext();) {
			System.out.println("Number is " + n);
			accumulator = reduceFunc.accept(accumulator, n);
			numsIterator.next();
		}

		return accumulator;

	}
}
