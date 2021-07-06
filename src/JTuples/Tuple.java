package JTuples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>Tuple representation in Java.</h1><br>
 * 
 * A tuple is an immutable, expandable and ordered data type, that can hold objects of different types:
 * <ul>
 * 	<li>You can't change a specific {@code Tuple} by addition or removal of elements, only form new ones.</li>
 * 	<li>Items are stored in order of addition: that means that if you add a {@code String} object after a {@code Number} object (for example), the former's index will be lower than the latter's.</li>
 * </ul>
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class Tuple implements Iterable<Object>, Comparable<Object> { // this class was made as a javadoc-compatible
																		// replacement of the javatuples library
						
	// the list holding objects contained in this tuple object
	private List<Object> elements = new ArrayList<Object>();
	
	// make tuples only instantiable through factory methods
	private Tuple(Object... elements) {
		this.elements = Arrays.asList(elements);
	}
	
	/**
	 * Forms a Tuple object out of the given elements provided.
	 * 
	 * @param elements a variable number of objects to pack in the tuple
	 * @return the {@link Tuple} object containing the elements given
	 * @since 2.0.0
	 */
	public static Tuple from(Object... elements) {
		return new Tuple(elements);
	}
	
	/**
	 * Creates a new tuple out of this tuple, adding the given elements.
	 * 
	 * @param elements the elements to add to the new tuple
	 * @return a new tuple with the contents of this tuple plus the elements added
	 * @since 2.0.0
	 */
	public Tuple add(Object... elements) {
		
		Object[] newArr = new Object[this.elements.size() + elements.length];
		
		try {
			System.arraycopy(this.elements.toArray(), 0, newArr, 0, this.elements.size());
			System.arraycopy(elements, 0, newArr, this.elements.size(), elements.length);
		} catch (Exception e) {
			jGame.logging.ProgramLogger.writeErrorLog(e); // unnecessary import, write fully qualified name here
		}
			
		return new Tuple(newArr);	
	}
	
	/**
	 * Returns this tuple's element at index {@code index}.
	 * 
	 * @param index the index of the desired tuple
	 * @return the object at the given index
	 * @since 2.0.0
	 */
	public Object get(int index) {
		return this.elements.get(index);
	}

	/**
	 * Returns this tuple's size.
	 * 
	 * @return this tuple's size
	 * @since 2.0.0
	 */
	public int size() {
		return this.elements.size();
	}
	
	@Override
	public int compareTo(Object o) {
		Tuple other = (Tuple)o;
		
		if (this.elements.size() < other.elements.size())
			return -1;
		else if (this.elements.size() > other.elements.size())
			return 1;
		else
			return 0; // implement check based on elements, that might be hard to make since elements are not required to have the same type
	}

	@Override
	public Iterator<Object> iterator() {
		return this.elements.iterator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tuple))
			return false;
		Tuple other = (Tuple) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		elements.forEach(elem -> {sb.append(elem).append(", ");});
		return "Tuple [elements=(" + sb.substring(0, sb.length()-2) + ")]"; // use substring to remove the leading ', '
	}
	
}
