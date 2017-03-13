package cs240_final;

import java.util.NoSuchElementException;
import cs240_HW4.ListInterface;

/**
 * A Fixed Sized Array List With Iterator
 * @author liang dong
 */
public class FixedSizedArrayListWithIterator<T> implements ListInterface<T>{

	private enum Move  {NEXT, PREVIOUS}
	private final T[] arr;
	private int size;
	
	/**
	 * Create an FixedSizedArrayList object, while holds 10 entries.
	 */
	public FixedSizedArrayListWithIterator() {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[10];
		arr = temp;
		size = 0;
	}
	
	// Iterator that can do actions
	private class IteratorForFixedSizedArrayList implements ListIteratorInterface<T>{

		private int next;
		private int previous;
		private boolean wasNextOrPreviousCalled;
		private Move lastMove;
		
		
		public IteratorForFixedSizedArrayList() {
			next = 1;
			previous = 0;
			wasNextOrPreviousCalled = false;
		}
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {
			return next <= size;
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				T temp = arr[next - 1];
				next++;
				previous++;
				wasNextOrPreviousCalled = true;
				lastMove = Move.NEXT;
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
			if(wasNextOrPreviousCalled) {
				wasNextOrPreviousCalled = false;
				T temp;
				if(lastMove.equals(Move.NEXT)) {
					temp = FixedSizedArrayListWithIterator.this.remove(previous);
					previous--;
					next--;
				}
				else {
					temp = FixedSizedArrayListWithIterator.this.remove(next);
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
			return previous > 0;
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = arr[previous - 1];
				next--;
				previous--;
				wasNextOrPreviousCalled = true;
				lastMove = Move.PREVIOUS;
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * Adds a new entry into the list.
		 * @param newEntry The new entry.
		 */
		public void add(T newEntry) {
			if(size < arr.length) {
				FixedSizedArrayListWithIterator.this.add(next, newEntry);
				size++;
			}
		}
		
		/**
		 * Get the next index
		 * @return the index of the next entry, return the size of 
		 * 		   the list if no next index. index from 1 to size.
		 */
		public int getNextIndex() {
			return next;
		}
		
		
		/**
		 * Get the previous index
		 * @return the index of the previous entry, 
		 * 		   return -1 if no previous index. index from 1 to size.
		 */
		public int getPreviousIndex() {
			return previous;
		}
	}

	/** Adds a new entry to the end of this list.
	   Entries currently in the list are unaffected.
	   The list's size is increased by 1.
	   @param newEntry  The object to be added as a new entry. */
	public void add(T newEntry) {
		if(size < arr.length) {
			arr[size] = newEntry;
			size++;
		}
	}

	/** Adds a new entry at a specified position within this list.
	   Entries originally at and above the specified position
	   are at the next higher position within the list.
	   The list's size is increased by 1.
	   @param newPosition  An integer that specifies the desired
	                       position of the new entry.
	   @param newEntry     The object to be added as a new entry.
	   @throws  IndexOutOfBoundsException if either
	            newPosition < 1 or newPosition > getLength() + 1. */
	public void add(int newPosition, T newEntry) {
		if(newPosition < 1 || newPosition > getLength() + 1) {
			throw new IndexOutOfBoundsException();
		}
		if(size < arr.length) {
			int index = size;
			while(index != (newPosition - 1)) {
				arr[index] = arr[index - 1];
				index--;
			}
			arr[newPosition - 1] = newEntry;
			size++;
		}
	}

	/** Removes the entry at a given position from this list.
	   Entries originally at positions higher than the given
	   position are at the next lower position within the list,
	   and the list's size is decreased by 1.
	   @param givenPosition  An integer that indicates the position of
	                         the entry to be removed.
	   @return  A reference to the removed entry.
	   @throws  IndexOutOfBoundsException if either 
	            givenPosition < 1 or givenPosition > getLength(). */
	public T remove(int givenPosition) {
		if(givenPosition < 1 || givenPosition > getLength()) {
			throw new IndexOutOfBoundsException();
		}
		T temp = arr[givenPosition - 1];
		int index = givenPosition - 1;
		while(index < size - 1) {
			arr[index] = arr[index + 1];
			index++;
		}
		arr[size - 1] = null;
		size--;
		return temp;
	}

	/** Removes all entries from this list. */
	public void clear() {
		for(int i = 0; i < size; i++) {
			arr[i] = null;
		}
		size = 0;
	}

	/** Replaces the entry at a given position in this list.
	   @param givenPosition  An integer that indicates the position of
	                         the entry to be replaced.
	   @param newEntry  The object that will replace the entry at the
	                    position givenPosition.
	   @return  The original entry that was replaced.
	   @throws  IndexOutOfBoundsException if either
	            givenPosition < 1 or givenPosition > getLength(). */
	public T replace(int givenPosition, T newEntry) {
		if(givenPosition < 1 || givenPosition > getLength()) {
			throw new IndexOutOfBoundsException();
		}
		T temp = arr[givenPosition - 1];
		arr[givenPosition - 1] = newEntry;
		return temp;
	}

	/** Retrieves the entry at a given position in this list.
	   @param givenPosition  An integer that indicates the position of
	                         the desired entry.
	   @return  A reference to the indicated entry.
	   @throws  IndexOutOfBoundsException if either
	            givenPosition < 1 or givenPosition > getLength(). */
	public T getEntry(int givenPosition) {
		if(givenPosition < 1 || givenPosition > getLength()) {
			throw new IndexOutOfBoundsException();
		}		
		return arr[givenPosition - 1];
	}

	/** Retrieves all entries that are in this list in the order in which
	   they occur in the list.
	   @return  A newly allocated array of all the entries in the list.
	            If the list is empty, the returned array is empty. */
	public T[] toArray() {
		
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Object[size];
		for(int i = 0; i < size; i++) {
			temp[i] = arr[i];
		}
		return temp;
	}

	/** Sees whether this list contains a given entry.
	   @param anEntry  The object that is the desired entry.
	   @return  True if the list contains anEntry, or false if not. */
	public boolean contains(T anEntry) {
		
		for(int i = 0; i < size; i++) {
			if(anEntry.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}

	/** Gets the length of this list.
	   @return  The integer number of entries currently in the list. */
	public int getLength() {
		return size;
	}
	   
	/** Sees whether this list is empty.
	   @return  True if the list is empty, or false if not. */
	public boolean isEmpty() {
		return size == 0;	
	}
	
	public ListIteratorInterface<T> iterator() {
		return new IteratorForFixedSizedArrayList();
	}
	
	/**
	 * @return An iterator.
	 */
	public ListIteratorInterface<T> getIterator() {
		return iterator();
	}

}
