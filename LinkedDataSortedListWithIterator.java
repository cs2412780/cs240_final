package cs240_final;

import java.util.NoSuchElementException;

import cs240_HW4.SortedListInterface;

/**
 * A Linked Data Sorted List With Iterator
 * @author liang dong
 */
public class LinkedDataSortedListWithIterator <T extends Comparable<? super T>>
												implements SortedListInterface<T> {
	
	
	private Node first;
	private int size = 0;
	
	/**
	 * @return An iterator
	 */
	public ListIteratorInterface<T> iterator() {
		return new IteratorForLinkedDataSortedList();
	}
	
	/**
	 * @return An iterator.
	 */
	public ListIteratorInterface<T> getIterator() {
		return iterator();
	}
	
	// An iterator that can do actions
	private class IteratorForLinkedDataSortedList implements ListIteratorInterface<T> {
		private Node next;
		private int moves;
		
		public IteratorForLinkedDataSortedList() {
			next = first;
			moves = 1;
		}
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {
			return next != null;
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				moves++;
				T temp = next.getData();
				next = next.getNext();
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		/**
		 * @throws UnsupportedOperationException, the iterator 
		 * 		   is not able to do this action.
		 */
		public T remove() {
			throw new UnsupportedOperationException();
		}
		/**
		 * @throws UnsupportedOperationException, the iterator 
		 * 		   is not able to do this action.
		 */
		public boolean hasPrevious() {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * @throws UnsupportedOperationException, the iterator 
		 * 		   is not able to do this action.
		 */
		public T previous() {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * @throws UnsupportedOperationException, the iterator 
		 * 		   is not able to do this action.
		 */
		public void add(T newEntry) {
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Get the next index
		 * @return the index of the next entry, return the size of 
		 * 		   the list if no next index. index from 1 to size.
		 */
		public int getNextIndex() {
			return moves;
		}
		
		
		/**
		 * Get the previous index
		 * @return the index of the previous entry, 
		 * 		   return -1 if no previous index. index from 1 to size.
		 */
		public int getPreviousIndex() {
			return moves - 1;
		}
			
		
	}
	
	// Node contains data field and a reference to another node.
	protected class Node {
		private T data;
		private Node next;
		
		/**
		 * constructor that initiates the member fields.
		 * @param data the data
		 * @param next the next node
		 */
		protected Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
		
		/**
		 * constructor that initiates the member fields.
		 * @param data the data
		 */
		protected Node(T data) {
			this(data, null);
		}
		
		/**
		 * getData is a accessor method
		 * @return the data field
		 */
		protected T getData() {
			return data;
		}
		
		/**
		 * a mutator method
		 * @param data the data field
		 */
		protected void setData(T data) {
			this.data = data;
		}
		
		/**
		 * a accessor method
		 * @return the next node.
		 */
		protected Node getNext() {
			return next;
		}
		
		/**
		 * a mutator method
		 * @param next the next node.
		 */
		protected void setNext(Node next) {
			 this.next = next;
		}
		
	}
	
	// return the first node
		protected final Node getFirst() {
			return first;
		}
		
		//add node at the beginning
		protected final void addFirst(Node node) {
			node.setNext(first);
			first = node;
			size++;
		}
		
		//add node after a given node
		protected final void addAfter(Node nodeBefore, Node newNode) {
			newNode.setNext(nodeBefore.getNext());
			nodeBefore.setNext(newNode);
			size++;
		}
		
		//remove the first node
		protected final void removeFirst() {
			first.setData(null);
			first = first.getNext();
			size--;
		}
		
		//remove node after a given node
		protected final void removeAfter(Node nodeBefore) {
			nodeBefore.getNext().setData(null);
			nodeBefore.setNext(nodeBefore.getNext().getNext());
			size--;
		}

		
		/** Adds a new entry to this sorted list in its proper order.
		The list's size is increased by 1.
		@param newEntry  The object to be added as a new entry. */
		public void add(T newEntry) {
			Node temp = new Node(newEntry);
			if(isEmpty()) {
				addFirst(temp);
			}
			else {
				Node traverser = getFirst();
				if(getFirst().getData().compareTo(newEntry) > 0) {
					addFirst(temp);
				}
				else {
					for(int i = 1; i < getLength(); i++) {
						if(traverser.getNext().getData().compareTo(newEntry) <= 0) {
							traverser = traverser.getNext();
						}
					}
					addAfter(traverser,temp);
				}
			}
		}

		/** Removes the first or only occurrence of a specified entry
		from this sorted list.
		@param anEntry  The object to be removed.
		@return  True if anEntry was located and removed; 
	         otherwise returns false. 
	     */
		public boolean remove(T anEntry) {
			Node traverser = getFirst();
			if(getFirst().getData().equals(anEntry)) {
				removeFirst();
				return true;
			}
			else {
				int position = 1;
				while(position < getLength() && traverser.getNext().getData().compareTo(anEntry) <= 0) {
					if(traverser.getNext().getData().equals(anEntry)) {
						removeAfter(traverser);
						return true;
					}
					traverser = traverser.getNext();
					position++;
				}
			}
			return false;
		}

		/** Gets the position of an entry in this sorted list.
		@param anEntry  The object to be found.
		@return  The position of the first or only occurrence of anEntry
	      if it occurs in the list; otherwise returns the position
	      where anEntry would occur in the list, but as a negative
	      integer. 
	      */
		public int getPosition(T anEntry) {
			Node traverser = getFirst();
			for(int i = 1; i <= getLength(); i++) {
				if(traverser.getData().compareTo(anEntry) > 0) {
					break;
				}
				if(traverser.getData().equals(anEntry)) {
					return i;
				}
				traverser = traverser.getNext();
			}
			return -1;
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
		T temp;
		if(givenPosition == 1) {
			temp = first.getData();
			first.setData(null);
			first = first.getNext();
		}
		else {
			Node traverser = first;
			for(int i = 1; i < givenPosition - 1; i++) {
				traverser = traverser.getNext();
			}
			Node pointer = traverser.getNext();// index of pointer is equal to location
			temp = pointer.getData();
			pointer.setData(null);
			traverser.setNext(pointer.getNext());
		}
		size--;
		return temp;
	}

	/** Removes all entries from this list. */
	public void clear() {
		while(size > 0) {
			remove(1);
		}
		first = null;
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

		Node traverser = first;
		for(int i = 1; i < givenPosition; i++) {
			traverser = traverser.getNext();
		}
		T temp = traverser.getData();
		traverser.setData(newEntry);
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
		Node traverser = first;
		for(int i = 1; i < givenPosition; i++) {
			traverser = traverser.getNext();
		}
		T temp = traverser.getData();
		return temp;
		
	}

	/** Retrieves all entries that are in this list in the order in which
	   they occur in the list.
	   @return  A newly allocated array of all the entries in the list.
	            If the list is empty, the returned array is empty. */
	public T[] toArray() {
		
		@SuppressWarnings("unchecked")
		T[] arr = (T[]) new Object[size];
		Node pointer = first;
		for(int i = 0; i < size; i++) {
			arr[i] = pointer.getData();
			pointer = pointer.getNext();
		}
		return arr;
	}

	/** Sees whether this list contains a given entry.
	   @param anEntry  The object that is the desired entry.
	   @return  True if the list contains anEntry, or false if not. */
	public boolean contains(T anEntry) {
		Node traverser = first;
		for(int i = 1; i <= size; i++) {
			if(traverser.getData().equals(anEntry)) {
				return true;
			}
			traverser = traverser.getNext();
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

}
