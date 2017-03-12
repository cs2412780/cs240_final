package cs240_final;

import java.util.NoSuchElementException;

public interface ListIteratorInterface<T> {
	

	
	/**
	 * Check if the iterator has finished its travesal.
	 * @return True if the iterator has next entry to return.
	 */
	public boolean hasNext();
	
	/**
	 * Retrieve the next entry and advance the iterator by one position.
	 * @return The next entry.
	 * @throws NoSuchElementException if the iterator has reached the end.
	 */
	public T next();
	/**
	 * Retrieves the last entry that next() returned, and remove it.
	 * @return The removed entry
	 * @throws IllegalStateException if next() has not 
	 * 		   been called before this operation.
	 * @throws UnsupportedOperationException if the iterator 
	 * 		   is not able to do a removal.
	 */
	public T remove();
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
	
	/**
	 * Adds a new entry into the list.
	 * @param newEntry The new entry.
	 * @throws ClassCastException if adding fails.
	 * @throws UnsupportedOperationException if the iterator 
	 * 		   is not able to do an addition.
	 */
	public void add(T newEntry);
	
	/**
	 * Get the next index
	 * @return the index of the next entry, return the size of 
	 * 		   the list if no next index. index from 0 to size - 1.
	 */
	public int getNextIndex();
	
	
	/**
	 * Get the previous index
	 * @return the index of the previous entry, 
	 * 		   return -1 if no previous index. index from 0 to size - 1.
	 */
	public int getPreviousIndex();

}
