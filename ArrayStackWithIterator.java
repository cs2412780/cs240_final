package cs240_final;

import java.util.NoSuchElementException;
import homework2.ArrayStack;


/**
 * A class of stack with iterator whose entries are stored in a fixed-size array.
 * @author iang Dong
 *
 */
public class ArrayStackWithIterator<T> extends ArrayStack<T> {
	
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
		private T[] arr;
		
		private IteratorForArrayStack() {
			arr = toArray();
			nextPosition = getIndexOfTopElement();
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
				T temp = arr[nextPosition];
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
			return previousPosition <= getIndexOfTopElement();
		}
		
		/**
		 * Retrieve the previous entry and move the iterator back by one position.
		 * @return The previous entry.
		 * @throws NoSuchElementException if the iterator is before the fisrt entry.
		 */
		public T previous() {
			if(hasPrevious()) {
				T temp = arr[previousPosition];
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

}
