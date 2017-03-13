package cs240_final;

import java.util.Iterator;
import java.util.NoSuchElementException;

import homework2.EmptyQueueException;
import homework2.QueueInterface;


/**
 * A Circular Linked Data Queue With Iterator
 * @author liang dong
 */
public class CircularLinkedDataQueueWithIterator<T> implements QueueInterface<T>, Iterable<T> {

	private Node free;
	private Node back;
	private int numberOfEntries;
	private int numberOfNodes;
	private boolean initialized = false;
	
	
	// initiate the circular linked data.
	public CircularLinkedDataQueueWithIterator() {
		free = new Node(null,null);
		back = free;
		back.setNextNode(free);
		numberOfEntries = 0;
		numberOfNodes = 1;
		initialized = true;
	}
	
	
	
	// Node contains data field and a reference to another node.
		private class Node {
			private T data;
			private Node next;
			
			/**
			 * constructor that initiates the member fields.
			 * @param data the data
			 * @param next the next node
			 */
			private Node(T data, Node next) {
				this.data = data;
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
			
		}
		
		/** Adds a new entry to the back of this queue.
		   @param newEntry  An object to be added. */
		public void enqueue(T newEntry) {
			checkInitialization();
			if (!isFull()) {
				if(numberOfNodes <= 10) {
					Node temp = new Node(newEntry,back.getNextNode());
					back.setNextNode(temp);
					back = temp;
					numberOfNodes++;
				}
				else {
					back.getNextNode().setData(newEntry);
					back = back.getNextNode();
				}
				numberOfEntries++;
			}
		}

		/** Removes and returns the entry at the front of this queue.
		   @return  The object at the front of the queue. 
		   @throws  EmptyQueueException if the queue is empty before the operation. */
		public T dequeue() {
			checkInitialization();
			if(isEmpty()) {
				throw new EmptyQueueException();
			}
			else {
				T temp = free.getNextNode().getData();
				free.getNextNode().setData(null);
				free = free.getNextNode();
				numberOfEntries--;
				return temp;
			}
		}

		/**  Retrieves the entry at the front of this queue.
		   @return  The object at the front of the queue.
		   @throws  EmptyQueueException if the queue is empty. */
		public T getFront() {
			checkInitialization();
			if(isEmpty()) {
				throw new EmptyQueueException();
			}
			else {
				return free.getNextNode().getData();
			}
		}

		/** Detects whether this queue is empty.
		   @return  True if the queue is empty, or false otherwise. */
		public boolean isEmpty() {
			return numberOfEntries <= 0;
		}

		/** Removes all entries from this queue. */
		public void clear() {
			while(!isEmpty()) {
				dequeue();
			}
		}
		
		/** Detects whether this queue is full.
		   @return  True if the queue is full, or false otherwise. */
		public boolean isFull() {
			return numberOfEntries >= 9;
		}
		
		/** Throws an exception if this object is not initialized.
	     * 
	     */
	    private void checkInitialization()
	    {
	        if (!initialized)
	             throw new SecurityException("CircularLinkedData object is not initialized " +
	                                        "properly.");
	   }
	    
	    
	/**
	 * @return An iterator
	 */
	public Iterator<T> iterator() {
		
		return new IteratorForCircularLinkedData();
	}
	
	/**
	 * @return An iterator.
	 */
	public Iterator<T> getIterator() {
		return iterator();
	}
	
	
	/**
	 * An iterator that can do actions
	 */
	private class IteratorForCircularLinkedData implements Iterator<T> {
		private Node next;
		
		private IteratorForCircularLinkedData() {
			next = free.getNextNode();
		}
		
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {		
			return next.getData() != null;
			
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				T temp = next.getData();
				next = next.getNextNode();
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}

}
