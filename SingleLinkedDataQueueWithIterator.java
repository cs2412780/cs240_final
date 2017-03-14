package cs240_final;

import java.util.Iterator;
import java.util.NoSuchElementException;

import homework2.EmptyQueueException;
import homework2.QueueInterface;

/**
 * A Single Linked Data Queue With Iterator
 * @author liang dong
 */
public class SingleLinkedDataQueueWithIterator<T> implements QueueInterface<T>, Iterable<T>{

	private Node front;
	private Node back;
	private int numberOfEntries;
	
	public SingleLinkedDataQueueWithIterator() {
		front = null;
		back = null;
		numberOfEntries = 0;
	}
	
	
	//An iterator that can do actions
	private class IteratorForSingleLinkedDataQueue implements Iterator<T>,MyIterator<T>{
		Node next;
		
		
		private IteratorForSingleLinkedDataQueue() {
			next = front;
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
		 * The order of next() traveses is inverse of pop().
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
		Node newNode = new Node(newEntry, null);
		if(isEmpty()) {
			front = newNode;
			back = newNode;
		}
		else {
			back.setNextNode(newNode);
			back = newNode;
		}
		numberOfEntries++;
	}

	/** Removes and returns the entry at the front of this queue.
	   @return  The object at the front of the queue. 
	   @throws  EmptyQueueException if the queue is empty before the operation. */
	public T dequeue() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else {
			T data = front.getData();
			front.setData(null);
			front = front.getNextNode();
			numberOfEntries--;
			return data;
		}
	}

	/**  Retrieves the entry at the front of this queue.
	   @return  The object at the front of the queue.
	   @throws  EmptyQueueException if the queue is empty. */
	public T getFront() {
		if(isEmpty()) {
			throw new EmptyQueueException();
		}
		else 
			return front.getData();
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

	/**
	 * @return An iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return new IteratorForSingleLinkedDataQueue();
	}
	
	/**
	 * @return An iterator.
	 */
	public Iterator<T> getIterator() {
		return iterator();
	}

}
