package cs240_final;

import java.util.NoSuchElementException;
import homework3.EmptyListException;
import homework3.GivenLocationOutOfBoundsException;
import homework3.ListInterface;
import homework3.ListIsFullException;


/**
 * A Linked Data List With Iterator
 * @author liang dong
 */
public class LinkedDataListWithIterator<T> implements ListInterface<T> {

	private Node front;
	private Node back;
	private int numberOfEntries;
    private static final int MAX_CAPACITY = 100000;
	
	public LinkedDataListWithIterator() {
		numberOfEntries = 0;
	}
	
	// A iterator that can do actions
	private class IteratorForLinkedDataList implements ListIteratorInterface<T>,MyIterator<T> {

		private Node next;
		private int moves;
		private Node previous;
		
		private IteratorForLinkedDataList() {
			next = front;
			moves = 0;
			previous = null;
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
		@Override
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
		 * Adds a new entry into the list.
		 * @param newEntry The new entry.
		 * @throws ListIsFullException if list is full.
		 */
		public void add(T newEntry) {
			if(numberOfEntries < MAX_CAPACITY) {
				Node newNode = new Node(newEntry, next);
				previous.setNextNode(newNode);
				previous = newNode;
				moves++;
				numberOfEntries++;
			}
			else {
				throw new ListIsFullException();
			}
		}
		
		/**
		 * Get the next index
		 * @return the index of the next entry, return the size of 
		 * 		   the list if no next index. index from 0 to size - 1.
		 */
		public int getNextIndex() {
			return moves;
		}
		
		
		/**
		 * Get the previous index
		 * @return the index of the previous entry, 
		 * 		   return -1 if no previous index. index from 0 to size - 1.
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
			protected Node getNextNode() {
				return next;
			}
			
			/**
			 * a mutator method
			 * @param next the next node.
			 */
			protected void setNextNode(Node next) {
				 this.next = next;
			}
			
		}
		
		// return the first node
		protected final Node getFirst() {
			return front;
		}
				
		//add node at the beginning
		protected final void addFirst(Node node) {
			node.setNextNode(front);
			front = node;
			numberOfEntries++;
		}
		
		//add node after a given node
		protected final void addAfter(Node nodeBefore, Node newNode) {
			newNode.setNextNode(nodeBefore.getNextNode());
			nodeBefore.setNextNode(newNode);
			numberOfEntries++;
		}
		
		//remove the first node
		protected final void removeFirst() {
			front.setData(null);
			front = front.getNextNode();
			numberOfEntries--;
		}
		
		//remove node after a given node
		protected final void removeAfter(Node nodeBefore) {
			nodeBefore.getNextNode().setData(null);
			nodeBefore.setNextNode(nodeBefore.getNextNode().getNextNode());
			numberOfEntries--;
		}
	
	/**
	 * Add an entry to the back of the list.
	 * @param newEntry The object to be added.
	 * @return true if succeeded
	 * @throw ListIsFullException if list is full
	 */
	public boolean addToEnd(T newEntry) {
		if(numberOfEntries < MAX_CAPACITY) {
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
		return true;
		}
		else {
			throw new ListIsFullException();
		}
	}
	
	/**
	 * Add an entry to the specific location.
	 * @param newEntry The object to be added
	 * @param location index of the location
	 * @return true if succeeded
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 * @throw ListIsFullException if list is full
	 */
	public boolean add(T newEntry, int location) {
		
		if(location > numberOfEntries || location < 0) {
			throw new GivenLocationOutOfBoundsException();
		}
		
		if (numberOfEntries >= MAX_CAPACITY) {
			throw new ListIsFullException();
		}
		else {
			Node newNode = new Node(newEntry);
			if(numberOfEntries < MAX_CAPACITY) {
				if(location == 0) {
					newNode.setNextNode(front);
					front = newNode;
					if(isEmpty()) {
						back = front;
					}
				}
				else {
					Node traverser = front;
					for(int i = 1; i < location; i++) {
						traverser = traverser.getNextNode();
					}
					Node pointer = traverser.getNextNode();// index of pointer is equal to location
					newNode.setNextNode(pointer);
					traverser.setNextNode(newNode);
					if(location == numberOfEntries) {
						back = newNode;
					}
				}
				
				numberOfEntries++;
				return true;
			}
		else
			return false;
		}
	}
	
	/**
	 * remove an entry at the specific location.
	 * @param location index of the location.
	 * @return the removed object
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 * @throw EmptyListException if the list is empty.
	 */
	public T removeByLocation(int location) {
		if(location < 0 || location > numberOfEntries - 1) {
			throw new GivenLocationOutOfBoundsException();

		}
		if(isEmpty()) {
			throw new EmptyListException();
		}
		T temp;
		if(location == 0) {
			temp = front.getData();
			front.setData(null);
			front = front.getNextNode();
		}
		else {
			Node traverser = front;
			for(int i = 1; i < location; i++) {
				traverser = traverser.getNextNode();
			}
			Node pointer = traverser.getNextNode();// index of pointer is equal to location
			temp = pointer.getData();
			pointer.setData(null);
			traverser.setNextNode(pointer.getNextNode());
			if(location == numberOfEntries - 1) {
				back = traverser;
			}
		}
		numberOfEntries--;
		return temp;
		
	}
	
	/**
	 * remove an entry that is that same as the given one.
	 * @param newEntry The entry to be removed.
	 * @return true if succeeded
	 * @throw EmptyListException if the list is empty.
	 */
	public boolean removeByValue(T newEntry) {
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			boolean found = false;
			if(front.getData().equals(newEntry)) {
				found = true;
				front.setData(null);
				front = front.getNextNode();
				numberOfEntries--;
			}
			else {
				Node traverser = front;
				int index;
				for(index = 0; index < numberOfEntries - 1; index++) {
					if(traverser.getNextNode().getData().equals(newEntry)) {
						found = true;
						break;
					}
					traverser = traverser.getNextNode();
				}
				if(found) {
					Node pointer = traverser.getNextNode();// index of pointer is equal to location
					pointer.setData(null);
					traverser.setNextNode(pointer.getNextNode());
					numberOfEntries--;
					if(index == numberOfEntries - 2) {
						back = traverser;
					}
				}
			}
			return found;
		}
	}
	
	/**
	 * remove all entries in the list.
	 * @return true if succeeded
	 */
	public boolean removeALl() {
		while(!isEmpty()) {
			removeByLocation(0);
		}
		front = null;
		back = null;
		return true;
		
	}


	/**
	 * replace an entry with the given entry.
	 * @param location the location of the entry to be replaced
	 * @param newEntry The entry to be added
	 * @return true if succeeded
	 * @throw EmptyListException if the list is empty.
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 */
	public boolean replaceByLocation(int location, T newEntry) {
		if(isEmpty()) {
			throw new EmptyListException();
		}
		if(location > numberOfEntries - 1 || location < 0) {
			throw new GivenLocationOutOfBoundsException();
		}
		Node traverser = front;
		for(int i = 0; i < location; i++) {
			traverser = traverser.getNextNode();
		}
		traverser.setData(newEntry);//index of traverser is equal to location
		return true;
	}
	
	/**
	 * replace an entry with the given entry.
	 * @param oldEntry the object to be replaced.
	 * @param newEntry The entry to be added
	 * @return true if succeeded
	 * @throw EmptyListException if the list is empty.
	 */
	public boolean replaceByValue(T oldEntry, T newEntry) {
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			boolean found = false;
			if(front.getData().equals(oldEntry)) {
				found = true;
				front.setData(newEntry);
			}
			else {
				Node traverser = front;
				int index;
				for(index = 0; index < numberOfEntries - 1; index++) {
					if(traverser.getNextNode().getData().equals(oldEntry)) {
						found = true;
						break;
					}
					traverser = traverser.getNextNode();
				}
				if(found) {
					traverser.getNextNode().setData(newEntry);;// index of pointer is equal to location
				}
			}
			return found;
		}
	}
	
	
	/**
	 *look at an entry.
	 * @param location the location of the entry
	 * @return the entry at the given location.
	 * @throw EmptyListException if the list is empty.
	 * @throw GivenLocationOutOfBoundsException is location is greater than (the size of the list + 1).
	 */
	public T look(int location) {
		if(location < 0 || location > numberOfEntries - 1) {
			throw new GivenLocationOutOfBoundsException();
		}
		if(isEmpty()) {
			throw new EmptyListException();
		}
		T temp;
		if(location == 0) {
			temp = front.getData();
		}
		else {
			Node traverser = front;
			for(int i = 1; i < location; i++) {
				traverser = traverser.getNextNode();
			}
			Node pointer = traverser.getNextNode();// index of pointer is equal to location
			temp = pointer.getData();
		}
		return temp;
	}

	
	/**
	 * look at all entries.
	 * @return an array of all entries.
	 * @throw EmptyListException if the list is empty.
	 */
	public T[] lookAll() {
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			Node traverser = front;
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[numberOfEntries];
			for(int i = 0; i < numberOfEntries; i++) {
				temp[i] = traverser.getData();
				traverser = traverser.getNextNode();
			}
			return temp;
		}
	}
	
	/**
	 * check the existence of a given entry.
	 * @param entry The entry to be checked.
	 * @return : true if exists.
	 * @throw EmptyListException if the list is empty.
	 */
	public boolean checkExistence(T entry) {
		if(isEmpty()) {
			throw new EmptyListException();
		}
		else {
			boolean found = false;
			if(front.getData().equals(entry)) {
				found = true;
			}
			else {
				Node traverser = front;
				int index;
				for(index = 0; index < numberOfEntries - 1; index++) {
					if(traverser.getNextNode().getData().equals(entry)) {
						found = true;
						break;
					}
					traverser = traverser.getNextNode();
				}
			}
			return found;
		}
	}
	
	/**
	 * counts the size of the list
	 * @return the size of the list.
	 */
	public int currentSize() {
		return numberOfEntries;
	}
	
	/**
	 * check if the list is empty.
	 * @return true if empty.
	 */
	public boolean isEmpty() {
		return numberOfEntries <= 0;
	}

	/**
	 * @return A iterator
	 */
	public ListIteratorInterface<T> iterator() {
		return new IteratorForLinkedDataList();
	}
	
	/**
	 * @return An iterator.
	 */
	public ListIteratorInterface<T> getIterator() {
		return iterator();
	}
	
}
