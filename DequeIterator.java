package cs240_final;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface DequeIterator<T> extends Iterator<T>{
	
	/**
	 * Check if there is an entry before the current iterator cursor.
	 * @return True if the iterator has previous entry to return.
	 */
	public boolean hasPrevious();
	
	/**
	 * Retrieve the previous entry and move the iterator back by one position.
	 * @return The previous entry.
	 * @throws NoSuchElementException if the iterator is before the fisrt entry.
	 */
	public T previous();
	
	public int getNextIndex();
	
	public int getPreviousIndex();
}
