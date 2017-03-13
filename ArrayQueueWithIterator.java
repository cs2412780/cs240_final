package cs240_final;

import java.util.NoSuchElementException;
import homework2.EmptyQueueException;
import homework2.QueueInterface;

/**
 * A class of queue with iterator whose entries are stored in an array.
 * @author iang Dong
 *
 */
public class ArrayQueueWithIterator<T> implements QueueInterface<T>, Iterable<T>{

	private final T[] array;
	private int front;
	private int back;
	private int numberOfEntries;
	private boolean initialized = false;
	
	/**
	 * @return A iterator thats traverses all entries.
	 */
	public IteratorInterface<T> iterator() {
		return new IteratorForArrayQueue();
	}
	
	/**
	 * @return An iterator.
	 */
	public IteratorInterface<T> getIterator() {
		return iterator();
	}
	
	/**
	 * A iterator that can some actions.
	 */
	private class IteratorForArrayQueue implements IteratorInterface<T> {
		private int nextPosition;
		private int previousPosition;
		@SuppressWarnings("unused")
		private boolean wasNextCalled;
		
		private IteratorForArrayQueue() {
			nextPosition = front;
			previousPosition = front - 1;
			wasNextCalled = false;
		}
		
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {		
			return array[nextPosition] != null;
			
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				T temp = array[nextPosition];
				nextPosition++;
				nextPosition = nextPosition % array.length;
				previousPosition++;
				previousPosition = previousPosition % array.length;
				wasNextCalled = true;
				return temp;
			}
			else {
				throw new NoSuchElementException("The iterator has already reached the end");
			}
			
		}
		
		/**
		 * @throws UnsupportedOperationException if the iterator 
		 * 		   is not able to do a removal.
		 */
		public void remove() {
			throw new UnsupportedOperationException();
			
		}
		/**
		 * Check if has a previous entry
		 * @return True if has a previous entry.
		 */
		public boolean hasPrevious() {
			return array[previousPosition] != null;
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = array[previousPosition];
				nextPosition--;
				if(nextPosition < 0)
					nextPosition = array.length - 1;
				previousPosition--;
				if(previousPosition < 0)
					previousPosition = array.length - 1;
				wasNextCalled = true;
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	

	
	/** Creates an empty queue whose initial capacity is 10. */
	public ArrayQueueWithIterator() {
		this(11);
	}
	
	/** Creates an empty queue. */
	public ArrayQueueWithIterator(int capacity) {
		@SuppressWarnings("unchecked")
		T[] tempArray = (T[])new Object[capacity];
		array = tempArray;
		numberOfEntries = 0;
		front = 0;
		back = 0;
		initialized = true;
		
	}
	
	/** Adds a new entry to the back of this queue.
	   @param newEntry  An object to be added. */
	public void enqueue(T newEntry) {
		checkInitialization();
		if(!isFull()) {
			array[back] = newEntry;	
			back++;
			back = back % array.length;
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
			T temp = array[front];
			array[front] = null;
			front++;
			front = front % array.length;
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
			return array[front];
		}
	}

	/** Detects whether this queue is empty.
	   @return  True if the queue is empty, or false otherwise. */
	public boolean isEmpty() {
		return numberOfEntries == 0;
	}

	/** Removes all entries from this queue. */
	public void clear() {
		while(!isEmpty()) {
			dequeue();
		}
	}
	/** Throws an exception if this object is not initialized.
     * 
     */
    private void checkInitialization() {
        if (!initialized)
             throw new SecurityException("ArrayQueue object is not initialized " +
                                        "properly.");
   }
    
    /** Detects whether this stack is full.
     * @return  True if the stack is full. 
     */
 	public boolean isFull() {
 		return numberOfEntries >= array.length - 1;
 	}// end isFull
 
}
