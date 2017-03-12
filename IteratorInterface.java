package cs240_final;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface IteratorInterface<T> extends Iterator<T>{

	/**
	 * Retrieve the previous entry and move the iterator back by one position.
	 * @return The previous entry.
	 * @throws NoSuchElementException if the iterator is before the fisrt entry.
	 */
	public boolean hasPrevious();
	
	/**
	 * Retrieve the previous entry and move the iterator back by one position.
	 * @return The previous entry.
	 * @throws NoSuchElementException if the iterator is before the fisrt entry.
	 */
	public T previous();
}
