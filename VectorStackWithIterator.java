package cs240_final;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Vector;
import homework2.StackInterface;

/**
 * A Vector Stack With Iterator
 * @author liang dong
 */
public class VectorStackWithIterator<T> implements StackInterface<T>, Iterable<T> {

	private final Vector<T> vector;
	private boolean initialized = false;
	
	/**
	 * An iterator that can do actions
	 */
	private class IteratorForVectorStack implements IteratorInterface<T> {
		
		private int nextPosition;
		private int previousPosition;
		@SuppressWarnings("unused")
		private boolean wasNextCalled;
		
		private IteratorForVectorStack() {
			nextPosition = vector.size() - 1;
			previousPosition = vector.size();
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
				T temp = vector.elementAt(nextPosition);
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
		 */
		public boolean hasPrevious() {
			return previousPosition < vector.size();
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = vector.elementAt(previousPosition);
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
	
	/** Creates an empty vector with 100 spaces. */
	public VectorStackWithIterator(int capacity) {
		vector = new Vector<>(capacity);
		initialized = true;
	}
	
	/** Creates an empty vector. */
	public VectorStackWithIterator() {
		this(10);
	}
	
	
	/** Adds a new entry to the top of this stack.
    @param newEntry  An object to be added to the stack. */
	public void push(T newEntry) {
		checkInitialization();
		vector.addElement(newEntry);
	}

	/** Removes and returns this stack's top entry.
    @return  The object at the top of the stack. 
    @throws  EmptyStackException if the stack is empty before the operation. */
	public T pop() {
		if (isEmpty())
			throw new EmptyStackException();
		else
			return vector.remove(vector.size() - 1);		
	}
	

	/** Retrieves this stack's top entry.
    @return  The object at the top of the stack.
    @throws  EmptyStackException if the stack is empty. */
	public T peek() {
		if (isEmpty())
			throw new EmptyStackException();
		else
			return vector.get(vector.size() - 1);
		
	}

	/** Detects whether this stack is empty.
    @return  True if the stack is empty. */
	public boolean isEmpty() {
		return vector.isEmpty();
	}
	/** Removes all entries from this stack. */
	public void clear() {
		vector.clear();
	} // end StackInterface

	/** Throws an exception if this object is not initialized.
     * 
     */
    private void checkInitialization()
    {
        if (!initialized)
             throw new SecurityException("VectorStack object is not initialized " +
                                        "properly.");
   }
    
    /**
     * this method
     * @return the size of the vector
     */
    public int currentSize() {
    	return vector.size();
    }

	@Override
	public IteratorInterface<T> iterator() {
		return new IteratorForVectorStack();
	}
	
}
