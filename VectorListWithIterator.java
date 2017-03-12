package cs240_final;

import java.util.NoSuchElementException;
import java.util.Vector;
import homework3.*;

/**
 * A list with iterator
 * @author liang dong
 */
public class VectorListWithIterator<T> implements ListInterface<T> {

	private final Vector<T> list;
    private static final int DEFAULT_CAPACITY = 10;
    private boolean initialized = false;
    private static final int MAX_CAPACITY = 100000;
	private enum Move  {NEXT, PREVIOUS}
    
   
    
    /**
     * Creates an empty list having a given initial capacity.
     * @param capacity The integer capacity desired.
     */
	public VectorListWithIterator(int capacity) {
		if(capacity < MAX_CAPACITY) {
			Vector<T> temp = new Vector<>(capacity);
			list = temp;
			initialized = true;
		}
		else {
			throw new IllegalStateException("Attempt to create a list " +
                     "whose capacity exceeds " +
                     "allowed maximum.");
		}
	}
	
    /** Creates an empty list whose initial capacity is 10. */

	public VectorListWithIterator() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * @return A iterator that traverses all entries.
	 */
	public ListIteratorInterface<T> iterator() {
		return new IteratorForVectorList();
	}
	
	/**
	 * A iterator
	 */
	private class IteratorForVectorList implements ListIteratorInterface<T> {
		private int next;
		private int previous;
		private boolean wasNextCalled;
		private Move lastMove;
		
		
		private IteratorForVectorList() {
			next = 0;
			previous = -1;
			wasNextCalled = false;
		}
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {
			return next < list.size();
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				T temp = list.elementAt(next);
				next++;
				previous++;
				lastMove = Move.NEXT;
				wasNextCalled = true;
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		/**
		 * Retrieves the last entry that next() returned, and remove it.
		 * @return The removed entry
		 * @throws IllegalStateException if next() has not 
		 * 		   been called before this operation.
		 */
		public T remove() {
			if(wasNextCalled) {
				wasNextCalled = false;
				T temp;
				if(lastMove.equals(Move.NEXT)) {
					temp = VectorListWithIterator.this.removeByLocation(previous);
					previous--;
					next--;
				}
				else {
					temp = VectorListWithIterator.this.removeByLocation(next);
				}
				return temp;
			}
			else {
				throw new IllegalStateException();
			}
		}
		/**
		 * Check if there is an entry before the current iterator cursor.
		 * @return True if the iterator has previous entry to return.
		 */
		public boolean hasPrevious() {
			return previous > -1;
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = list.elementAt(previous);
				next--;
				previous--;
				lastMove = Move.PREVIOUS;
				wasNextCalled = true;
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * Adds a new entry into the list.
		 * @param newEntry The new entry.
		 * @throws ListIsFullException if list is full.
		 */
		public void add(T newEntry) {
			if(list.size() < MAX_CAPACITY) {
				list.add(next, newEntry);
				next++;
				previous++;
			}
			else {
				throw new ListIsFullException();
			}
			
		}
		
		/**
		 * Get the next index
		 * @return the index of the next entry, return the size of 
		 * 		   the list if no next index. index from 0 to size - 1.
		 */
		public int getNextIndex() {
			return next;
		}
		
		
		/**
		 * Get the previous index
		 * @return the index of the previous entry, 
		 * 		   return -1 if no previous index. index from 0 to size - 1.
		 */
		public int getPreviousIndex() {
			return previous;
		}
	}
	/**
	 * Add an entry to the back of the list.
	 * @param newEntry The object to be added.
	 * @return true if succeeded
	 * @throw ListIsFullException if list is full.
	 */
	public boolean addToEnd(T newEntry) {
		checkInitialization();
		if(list.size() <= MAX_CAPACITY) {
			list.add(newEntry);
			return true;
		}
		else {
			throw new ListIsFullException();
		}
	}
	
	/**
	 * Add an entry to the specific location.
	 * @param newEntry The object to be added
	 * @param location index of the location
	 * @return true if succeeded
	 * @throw ListIsFullException if list is full.
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 */
	public boolean add(T newEntry, int location) {
		checkInitialization();
		if(list.size() <= MAX_CAPACITY) {
			if(location > list.size() || location < 0) {
				throw new GivenLocationOutOfBoundsException();
			}
			else {
				list.add(location, newEntry);
				return true;
			}
		}
		else {
			throw new ListIsFullException();
		}
	}
	
	/**
	 * remove an entry at the specific location.
	 * @param location index of the location.
	 * @return the removed object
	 * @throw EmptyListException if list is empty
	 * @throw GivenLocationOutOfBoundsException is location is greater than the size of the list
	 */
	public T removeByLocation(int location) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		
		else {
			if(list.size() <= location || location < 0) {
				throw new GivenLocationOutOfBoundsException();
			}
			else {
				T temp = list.remove(location);
				return temp;
			}
		}

	}
	
	/**
	 * remove an entry that is that same as the given one.
	 * @param newEntry The entry to be removed.
	 * @return true if succeeded
	 * @throw EmptyListException if list is empty
	 */
	public boolean removeByValue(T newEntry) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		
		else {
			boolean temp = list.remove(newEntry);
			return temp;
		}
	}
	
	/**
	 * remove all entries in the list.
	 * @return true if succeeded
	 */
	public boolean removeALl() {
		checkInitialization();
		list.clear();
		if(list.size() == 0)
			return true;
		else
			return false;
	}


	/**
	 * replace an entry with the given entry.
	 * @param location the location of the entry to be replaced
	 * @param newEntry The entry to be added
	 * @return true if succeeded
	 * @throw EmptyListException if list is empty
	 * @throw GivenLocationOutOfBoundsException is location is greater than the size of the list
	 */
	public boolean replaceByLocation(int location, T newEntry) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			if(list.size() <= location || location < 0) {
				throw new GivenLocationOutOfBoundsException();
			}
			else {
				list.set(location, newEntry);
				return true;
			}
		}
	}
	
	/**
	 * replace an entry with the given entry.
	 * @param oldEntry the object to be replaced.
	 * @param newEntry The entry to be added
	 * @return true if succeeded.
	 * @throw EmptyListException if list is empty.
	 */
	public boolean replaceByValue(T oldEntry, T newEntry) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			int indexOfOldEntry = list.indexOf(oldEntry);
			if(indexOfOldEntry == -1) {
				return false;
			}
			list.set(indexOfOldEntry, newEntry);
			return true;
		}
	}
	
	
	/**
	 *look at an entry.
	 * @param location the location of the entry
	 * @return the entry at the given location.
	 * @throw EmptyListException if the list is empty.
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 */
	public T look(int location) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		if(list.size() <= location || location < 0) {
			throw new GivenLocationOutOfBoundsException();
		}
		else {
			T temp = list.elementAt(location);
			return temp;
		}
	}

	
	/**
	 * look at all entries.
	 * @return an array of all entries.
	 * @throw EmptyListException if the list is empty.
	 */
	public T[] lookAll() {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			@SuppressWarnings("unchecked")
			T[] array = (T[]) new Object[list.size()];
			for(int i = 0; i < list.size(); i++) {
				array[i] = list.elementAt(i);
			}
			return array;
		}
	}
	
	/**
	 * check the existence of a given entry.
	 * @param entry The entry to be checked.
	 * @return True if exists.
	 * @throw EmptyListException if the list is empty.
	 */
	public boolean checkExistence(T entry) {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyListException();
		}
		return list.contains(entry);
		
	}
	
	/**
	 * check if the list is empty.
	 * @return true if empty.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * counts the size of the list
	 * @return the size of the list.
	 */
	public int currentSize() {
		return list.size();
	}
	
	/**
	 *  Throws an exception if this object is not initialized.
     */
    private void checkInitialization()
    {
        if (!initialized)
             throw new SecurityException("VectorList object is not initialized " +
                                        "properly.");
   }
       
}
