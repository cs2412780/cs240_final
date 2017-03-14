package cs240_final;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import homework2.StackInterface;

/**
 * A Linked Data Stack With Iterator
 * @author liang dong
 */
public class LinkedDataStackWithIterator<T> implements StackInterface<T>, Iterable<T> {

	private Node firstNode;
	private int numberOfEntries;
	

	// let the first node point to null.
	public LinkedDataStackWithIterator() {
		firstNode = null;
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
		@SuppressWarnings("unused")
		private void setNextNode(Node next) {
			 this.next = next;
		}
		
	}
	/** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
	public void push(T newEntry) {
		Node newNode = new Node(newEntry, firstNode);
		firstNode = newNode;
		numberOfEntries++;
	}

	/** Removes and returns this stack's top entry.
    @return  The object at the top of the stack. 
    @throws  EmptyStackException if the stack is empty before the operation. */
	public T pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		else {
			T data = firstNode.getData();
			firstNode.setData(null);
			firstNode = firstNode.getNextNode();
			numberOfEntries--;
			return data;
		}
	}

	/** Retrieves this stack's top entry.
    @return  The object at the top of the stack.
    @throws  EmptyStackException if the stack is empty. */
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		else {
			
			return firstNode.getData();
		}
	}

	/** Detects whether this stack is empty.
    @return  True if the stack is empty. */
	public boolean isEmpty() {
		return numberOfEntries <= 0;
	}

	/** Removes all entries from this stack. */
	public void clear() {
		while(!isEmpty()) {
			pop();
		}
	}

	//An iterator that can do actions
	private class IteratorForLinkedDataStack implements Iterator<T>, MyIterator<T>{
		private Node next;
		
		private IteratorForLinkedDataStack() {
			next = firstNode;
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
				T temp = next.getData();
				next = next.getNextNode();
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	/**
	 * @return An iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return new IteratorForLinkedDataStack();
	}
	
	/**
	 * @return An iterator.
	 */
	public Iterator<T> getIterator() {
		return iterator();
	}
	
}
