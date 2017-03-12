package cs240_final;

import java.util.NoSuchElementException;
import deque.DequeInterface;
import deque.EmptyQueueException;

/**
 * A Deque with iterator
 * @author liang dong
 */
public class DequeWithIterator<T> implements DequeInterface<T> {

	private Node first;
	private Node last;
	private int numberOfEntries;
	
	public DequeWithIterator(Node first, Node last) {
		this.first = first;
		this.last = last;
		numberOfEntries = 0;
	}
	
	public DequeWithIterator() {
		this(null, null);
	}

	/**
	 * Iterator that can do actions
	 */
	private class IteratorForDeque implements DequeIterator<T> {
		private Node next;
		private Node previous;
		private int moves;
		
		
		private IteratorForDeque() {
			next = first;
			previous = null;
			moves = 0;
		}
		
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		@Override
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
				previous = next;
				T temp = next.getData();
				next = next.getNextNode();
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * Check if there is an entry before the current iterator cursor.
		 * @return True if the iterator has previous entry to return.
		 */
		@Override
		public boolean hasPrevious() {
			return previous != null;
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		@Override
		public T previous() {
			if(hasPrevious()) {
				moves--;
				next = previous;
				T temp = previous.getData();
				previous = previous.getPreviousNode();
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
		
		/**
		 * @return the next index
		 */
		public int getNextIndex() {
			return moves;
		}
		
		/**
		 * @return the previous index
		 */
		public int getPreviousIndex() {
			return moves - 1;
		}
		
	}
	
	// Node contains data field and a reference to another node.
	private class Node {
		private T data;
		private Node previous;
		private Node next;
			
		/**
		 * constructor that initiates the member fields.
		 * @param data the data
		 * @param next the previous node
		 * @param next the next node
		 */
		private Node(Node previous, T data, Node next) {
			this.data = data;
			this.previous = previous;
			this.next = next;
		}
		/**
		 * getData is a accessor method
		 * @return the data field
		 */
		private T getData() {
			return data;
		}
			
		/**
		 * a mutator method
		 * @param data the data field
		 */
		private void setData(T data) {
			this.data = data;
		}
		
		/**
		 * a accessor method
		 * @return the previous node.
		 */
		private Node getPreviousNode() {
			return previous;
		}
			
		/**
		 * a mutator method
		 * @param next the previous node.
		 */
		private void setPreviousNode(Node previous) {
			this.previous = previous;
		}
			
		/**
		 * a accessor method
		 * @return the next node.
		 */
		private Node getNextNode() {
			return next;
		}
			
		/**
		 * a mutator method
		 * @param next the next node.
		 */
		private void setNextNode(Node next) {
			this.next = next;
		}
			
	}// end Node
	

	/**
	 * Add a new entry to the front.
	 * @param newEntry The object to be added as a new entry.
	 */
	@Override
	public void addToFront(T newEntry) {
		Node newNode = new Node(null, newEntry, first);
		if(isEmpty()) {
			last = newNode;
		}
		else {
			first.setPreviousNode(newNode);
		}
		first = newNode;
		numberOfEntries++;
	}// end addToFront

	
	/**
	 * Add a new entry to the back.
	 * @param newEntry The object to be added as a new entry.
	 */
	@Override
	public void addToBack(T newEntry) {
		Node newNode = new Node(last, newEntry, null);
		if(isEmpty()) {
			first = newNode;
		}
		else {
			last.setNextNode(newNode);
		}
		last = newNode;
		numberOfEntries++;		
	}// end addToBack

	/**
	 * Removes and returns the front entry of this deque.
	 * @return The object at the front of this deque.
	 * @throws EmptyQueueException if the deque is empty before this operation.
	 */
	@Override
	public T removeFromFront() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else {
			T temp = first.getData();
			first.setData(null);
			first = first.getNextNode();
			numberOfEntries--;
			return temp;
		}
	}//end removeFromFront

	
	/**
	 * Removes and returns the back entry of this deque.
	 * @return The object at the back of this deque.
	 * @throws EmptyQueueException if the deque is empty before this operation.
	 */
	@Override
	public T removeFromBack() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else {
			T temp = last.getData();
			last.setData(null);
			last = last.getPreviousNode();
			numberOfEntries--;
			return temp;
		}
	}// end removeFromBack

	/**  Retrieves the entry at the front of this deque.
	   @return  The object at the front of the deque.
	   @throws  EmptyQueueException if the deque is empty. 
	 */
	@Override
	public T getFront() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else {
			return first.getData();
		}
	}// end getFront

	/**  Retrieves the entry at the front of this deque.
	   @return  The object at the front of the deque.
	   @throws  EmptyQueueException if the deque is empty. 
	 */
	@Override
	public T getBack() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else {
			return last.getData();
		}
	}// end getBack

	/** Detects whether this queue is empty.
	   @return  True if the queue is empty, or false otherwise. 
	 */
	@Override
	public boolean isEmpty() {
		return numberOfEntries <= 0;
	}// end isEmpty

	/** Removes all entries from this queue. */

	@Override
	public void clear() {
		while(!isEmpty()) {
			removeFromFront();
		}
		
	}// end clear

	
	/**
	 * @return An iterator
	 */
	public DequeIterator<T> iterator() {
		return new IteratorForDeque();
	}

}
