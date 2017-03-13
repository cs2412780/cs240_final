package cs240_final;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import homework2.ArrayStack;


/**
 * A class of stack with iterator whose entries are stored in a fixed-size array.
 * @author iang Dong
 *
 */
public class ArrayStackWithIterator<T> extends ArrayStack<T> {
	
	private final T[] array;
	private int indexOfTopElement;
	private static final int DEFAULT_CAPACITY = 20;
	private boolean initialized = false;

	/** Creates an empty bag whose initial capacity is 10. */
	public ArrayStackWithIterator(int capacity) {
		@SuppressWarnings("unchecked")
		T[] tempArr = (T[]) new Object[capacity];
		array = tempArr;
		indexOfTopElement = -1;
		initialized = true;
	}//end constructor
	
	/** Creates an empty bag whose initial capacity is 10. */
	public ArrayStackWithIterator() {
		this(DEFAULT_CAPACITY);
	}//end constructor
	
	/**
	 * @return An iterator.
	 */
	public IteratorInterface<T> iterator() {
		return new IteratorForArrayStack();
	}
	
	/**
	 * An iterator that can do actions
	 */
	private class IteratorForArrayStack implements IteratorInterface<T>{
		
		private int nextPosition;
		private int previousPosition;
		@SuppressWarnings("unused")
		private boolean wasNextCalled;
		
		private IteratorForArrayStack() {
			nextPosition = indexOfTopElement;
			previousPosition = nextPosition + 1;
			wasNextCalled = false;
		}
		
		/**
		 * Check if the iterator has finished its travesal.
		 * @return True if the iterator has next entry to return.
		 */
		public boolean hasNext() {		
			return nextPosition > -1;
			
		}
		
		/**
		 * Retrieve the next entry and advance the iterator by one position.
		 * @return The next entry.
		 * @throws NoSuchElementException if the iterator has reached the end.
		 */
		public T next() {
			if(hasNext()) {
				T temp = array[nextPosition];
				nextPosition--;
				previousPosition--;
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
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public boolean hasPrevious() {
			return previousPosition <= indexOfTopElement;
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = array[previousPosition];
				previousPosition++;
				nextPosition++;
				wasNextCalled = true;
				return temp;
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	


	/** Adds a new entry to this bag.
    @param newEntry The object to be added as a new entry.
	*/
	@Override
	public void push(T newEntry) {
		checkInitialization();
		if(!isFull()) {
			indexOfTopElement++;
			array[indexOfTopElement] = newEntry;
		}//end if
	}//end push

	/** Removes and returns this stack's top entry.
     * @return  The object at the top of the stack. 
	 * @throws EmptyStackException 
	 */
	@Override
	public T pop(){
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		else {
			T temp = array[indexOfTopElement];
			array[indexOfTopElement] = null;
			indexOfTopElement--;
			return temp;
		}
	}

	/** Retrieves this stack's top entry.
    * @return  The object at the top of the stack.
    * @throws  EmptyStackException if the stack is empty. 
    */
	@Override
	public T peek() {
		checkInitialization();
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		else {
			return array[indexOfTopElement];
		}
	}

	/** Detects whether this stack is empty.
    * @return  True if the stack is empty. 
    */
	@Override
	public boolean isEmpty() {
		return indexOfTopElement < 0 ;
	}

	/** Removes all entries from this stack. 
	*/
	@Override
	public void clear() {
		while(!isEmpty()) {
			array[indexOfTopElement] = null;
			indexOfTopElement--;
		}
	}
	
	/** Throws an exception if this object is not initialized.
     * 
     */
    private void checkInitialization()
    {
        if (!initialized)
             throw new SecurityException("ArrayStack object is not initialized " +
                                        "properly.");
   }
	
	
	/** Detects whether this stack is full.
    * @return  True if the stack is full. 
    */
	public boolean isFull() {
		return indexOfTopElement >= array.length - 1;
	}// end isFull
	
}
