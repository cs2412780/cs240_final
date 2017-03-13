package cs240_final;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * A dictionary that stores <key, value list> pairs.
 * @author liang dong
 */
public class Dictionary<K, V> implements DictionaryInterface<K, V> {
	
	private final Vector<Entry<K, V>> dictionary;
	private static final int DEFAULT_CAPACITY = 20;
	
	/**
	 * Initialize a vetcor with a personalized capacity.
	 * @param capacity A personalized capacity
	 */
	public Dictionary(int capacity) {
		Vector<Entry<K, V>> temp = (Vector<Entry<K, V>>)new Vector<Entry<K, V>>();
		dictionary = temp;
	}
	
	/**
	 * Initialize a vetcor with a personalized capacity.
	 */
	public Dictionary() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * A pair that contains a key and a list associatd with the key.	 *
	 */
	private class Entry<S, D> {
		private S key;
		private VectorListWithIterator<D> value = new VectorListWithIterator<>(20);
		private Entry(S key, D value) {
			this.key = key;
			if(value != null)
				this.value.addToEnd(value);
		}
		
		private S getKey() {
			return key;
		}
		private VectorListWithIterator<D> getValue() {
			return value;
		}
		
		
		private void setValue(D newValue) {
			if(newValue != null)
				value.addToEnd(newValue);
		}
	}
	
	/**
	 * Creates a iterator that traverses all keys in the dictionary.
	 */
	private class IteratorForKeys implements Iterator<K>{
		private int next;

		private IteratorForKeys() {
			next = 0;
		}
		@Override
		public boolean hasNext() {
			return next < dictionary.size();
		}

		@Override
		public K next() {
			if(hasNext()) {
				next++;
				return dictionary.elementAt(next - 1).getKey();
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	
	/**
	 * Creates a iterator that traverses all values in the dictionary.
	 */
	private class IteratorForValues implements Iterator<V>{
		private int next;
		private ListIteratorInterface<V> iterator;
		private boolean hasNext;

		private IteratorForValues() {
			next = 0;
			iterator = dictionary.elementAt(next).getValue().iterator();
			hasNext = false;
		}
		
		/**
		 * Checks if there is a next value in the dictionary. 
		 * @return True if there is a next value.
		 */
		public boolean hasNext() {
			hasNext = iterator.hasNext();
			if(!hasNext) {
				while(next < dictionary.size() - 1) {
					next++;
					iterator = dictionary.elementAt(next).getValue().iterator();		
					if(iterator.hasNext()) {
						break;
					}
				}
			}
			return iterator.hasNext();
		}

		/**
		 * @return Retrun the next value in this dictionary.
		 */
		public V next() {
			if(hasNext()) {
				if(iterator.hasNext()) {
					return iterator.next();
				}
				else {
					next++;
					return iterator.next();
				}
			}
			else {
				throw new NoSuchElementException();
			}
		}
	}
	

	/** Adds a new entry to this dictionary. If the given search key already
    exists in the dictionary, add new value into the corresponding value list. 
    Do nothing if the pair of key and value exist.
    @param key    An object search key of the new entry.
    @param value  An object associated with the search key.
    @return True if add successfully */
	public boolean add(K key, V value) {
		if(key == null) {
			return false;
		}
		int index = -1;
		index = findIndexOfAKey(key);
		if(index != -1) {
			if(value != null && !checkExistence(index, value)) {
				dictionary.elementAt(index).setValue(value);
				return true;
			}
			return false;
		}
		else {
			Entry<K, V> tempEntry = new Entry<K, V>(key, value);
			dictionary.addElement(tempEntry);
			return true;
		}
	}

	/** Removes a specific entry from this dictionary.
    @param key  An object search key of the entry to be removed.
    @return  Either a list of the values that was associated with the search key
             or null if no such object exists. */
	public VectorListWithIterator<V> remove(K key) {
		int index = -1;
		index = findIndexOfAKey(key);
		if(index != -1) {
			VectorListWithIterator<V> temp = dictionary.elementAt(index).getValue();
			dictionary.set(index, dictionary.elementAt(dictionary.size() - 1));
			dictionary.remove(dictionary.size() - 1);
			return temp;
		}
		return null;
	}

	/** Retrieves from this dictionary the value associated with a given
    search key.
    @param key  An object search key of the entry to be retrieved.
    @return  Either the iteraot of a list that is associated with the search key
             or null if no such object exists. */
	public ListIteratorInterface<V> getValue(K key) {
		int index = -1;
		index = findIndexOfAKey(key);
		if(index != -1) {
			return dictionary.elementAt(index).getValue().iterator();
		}
		return null;
	}

	/** Sees whether a specific entry is in this dictionary.
    @param key  An object search key of the desired entry.
    @return  True if key is associated with an entry in the dictionary. */
	public boolean contains(K key) {
		int index = -1;
		index = findIndexOfAKey(key);
		if(index != -1) {
			return true;
		}
		return false;
	}

	/** Creates an iterator that traverses all search keys in this dictionary.
    @return  An iterator that provides sequential access to the search
             keys in the dictionary. */
	public Iterator<K> getKeyIterator() {
		return new IteratorForKeys();
	}

	/** Creates an iterator that traverses all values in this dictionary.
    @return  An iterator that provides sequential access to the values
             in this dictionary. */
	public Iterator<V> getValueIterator() {
		return new IteratorForValues();
	}

	/** Sees whether this dictionary is empty.
    @return  True if the dictionary is empty. */
	public boolean isEmpty() {
		return dictionary.size() == 0;
	}

	/** Gets the size of this dictionary.
    @return  The number of entries (key-value pairs) currently
             in the dictionary. */
	public int getSize() {
		return dictionary.size();
	}

	/** Removes all entries from this dictionary. */
	public void clear() {
		dictionary.removeAllElements();
	}
	
	/**
	 * Removes a value that associatied with a key.
	 * @param key A reseach key
	 * @param value The targt value
	 * @return true if remove successfully.
	 */
	public boolean removeAValue(K key, V value) {
		int index = -1;
		index = findIndexOfAKey(key);
		if(index != -1) {
			if(dictionary.elementAt(index).getValue() != null && dictionary.elementAt(index).getValue().currentSize() != 0) {
				return dictionary.elementAt(index).getValue().removeByValue(value);
			}
		}
		return false;
	}
	
	/**
	 * Looks for an index of a key in the vector.
	 * @param key The target key
	 * @return The index of the key, or -1 if not found.
	 */
	private int findIndexOfAKey(K key) {
		int index = 0;
		while(index < dictionary.size()) {
			if(dictionary.elementAt(index).getKey().equals(key)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * Checks if a pair exists.
	 * @param index The index of a key
	 * @param value The associated value.
	 * @return True if the pair exists
	 */
	private boolean checkExistence(int index, V value) {
		if(dictionary.elementAt(index).getValue().isEmpty()) {
			return false;
		}
		return dictionary.elementAt(index).getValue().checkExistence(value);
	}

}
