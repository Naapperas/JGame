package JTuples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Tuple representation in Java.
 * 
 * @author Nuno Pereira
 * @since 2.0.0
 */
public class Tuple implements Iterable<Object>, Comparable<Object> { // this class was made as a javadoc-compatible
																		// replacement of the javatuples library

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
	 * 
	 * @param index
	 * @return
	 * @since 2.0.0
	 */
	public Object get(int index) {
		return this.elements.get(index);
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterator<Object> iterator() {
		// TODO Auto-generated method stub
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
		return "Tuple [elements=" + elements + "]";
	}
	
}
