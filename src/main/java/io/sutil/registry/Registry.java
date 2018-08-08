package io.sutil.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

import io.sutil.LazyLoadValue;
import io.sutil.TextBuilder;

/**
 * 
 * This class is similar to a map but with unique value and key can be find from their values
 * 
 * @author Mindstorm38
 *
 * @param <K> Key type
 * @param <V> Value type
 */
@SuppressWarnings( { "unchecked" } )
public class Registry<K, V> implements Iterable<Entry<K, V>>, RandomAccess {
	
	// private final SortedMap<K, V> map;
	private RegistryEntry<K, V>[] entries;
	private int size;
	
	private final LazyLoadValue<List<Entry<K, V>>> entrySet;
	private final LazyLoadValue<List<K>> keySet;
	private final LazyLoadValue<List<V>> valueSet;
	
	public Registry() {
		
		this.entries = new RegistryEntry[0];
		this.size = 0;
		
		this.entrySet = new LazyLoadValue<List<Entry<K,V>>>() {

			@Override
			public List<Entry<K, V>> create() {
				return new EntriesMappedList();
			}
			
		};
		
		this.keySet = new LazyLoadValue<List<K>>() {

			@Override
			public List<K> create() {
				return new KeyMappedList();
			}
			
		};
		
		this.valueSet = new LazyLoadValue<List<V>>() {

			@Override
			public List<V> create() {
				return new ValueMappedList();
			}
			
		};
		
		// this.map = new TreeMap<>();
		
	}
	
	private void checkIndex(int idx) {
		if ( idx < 0 || idx >= this.size ) throw new IndexOutOfBoundsException( "Index must be between 0 and " + ( this.entries.length - 1 ) );
	}
	
	public int indexOfKey(Object key) {
		if ( this.size == 0 ) return -1;
		if ( key == null ) return -1;
		for ( int i = 0; i < this.size; i++ )
			if ( key.equals( this.entries[ i ].key ) )
				return i;
		return -1;
	}
	
	public int indexOfValue(Object value) {
		if ( this.size == 0 ) return -1;
		if ( value == null ) {
			for ( int i = 0; i < this.size; i++ )
				if ( this.entries[ i ].value == null )
					return i;
		} else {
			for ( int i = 0; i < this.size; i++ )
				if ( value.equals( this.entries[ i ].value ) )
					return i;
		}
		return -1;
	}
	
	public int indexOfEntry(Entry<?, ?> entry) {
		if ( this.size == 0 ) return -1;
		if ( entry == null ) return -1;
		for ( int i = 0; i < this.size; i++ )
			if ( entry.equals( this.entries[ i ] ) )
				return i;
		return -1;
	}
	
	public int lastIndexOfKey(Object key) {
		if ( this.size == 0 ) return -1;
		if ( key == null ) return -1;
		for ( int i = this.size - 1; i != 0; i-- )
			if ( key.equals( this.entries[ i ].key ) )
				return i;
		return -1;
	}
	
	public int lastIndexOfValue(Object value) {
		if ( this.size == 0 ) return -1;
		if ( value == null ) {
			for ( int i = this.size - 1; i != 0; i-- )
				if ( this.entries[ i ].value == null )
					return i;
		} else {
			for ( int i = this.size - 1; i != 0; i-- )
				if ( value.equals( this.entries[ i ].value ) )
					return i;
		}
		return -1;
	}
	
	public int lastIndexOfEntry(Entry<?, ?> entry) {
		if ( this.size == 0 ) return -1;
		if ( entry == null ) return -1;
		for ( int i = this.size - 1; i != 0; i-- )
			if ( entry.equals( this.entries[ i ] ) )
				return i;
		return -1;
	}
	
	public V get(K key) {
		int idx = this.indexOfKey( key );
		return idx == -1 ? null : this.entries[ idx ].value;
		// return this.map.get( key );
	}
	
	public void register(K key, V value) {
		
		if ( key == null || value == null ) throw new IllegalArgumentException("Can't use null keys/values");
		if ( this.indexOfValue( value ) != -1 ) throw new IllegalStateException("This value is already used");
		
		int keyIndex = this.indexOfKey( key );
		
		if ( keyIndex == -1 ) {
			
			keyIndex = this.size;
			RegistryEntry<K, V>[] newEntries = new RegistryEntry[ keyIndex + 1 ];
			System.arraycopy( this.entries, 0, newEntries, 0, keyIndex );
			newEntries[ keyIndex ] = new RegistryEntry<K, V>( key, value );
			this.entries = newEntries;
			this.size++;
			
		} else {
			
			this.entries[ keyIndex ].value = value;
			
		}
		
		// if ( this.getKey( value ) != null ) throw new IllegalStateException("This value is already used"); // Check if the value is unique
		// this.map.put( key, value );
		
	}
	
	public void unregister(K key) {
		
		int keyIndex = this.indexOfKey( key );
		if ( keyIndex == -1 ) return;
		
		if ( this.entries.length == 1 ) { // If keyIndex != -1, minimum length is 1
			
			this.entries = new RegistryEntry[0];
			
		} else {
			
			RegistryEntry<K, V>[] newEntries = new RegistryEntry[ keyIndex - 1 ];
			
			if ( keyIndex != 0 )
				System.arraycopy( this.entries, 0, newEntries, 0, keyIndex );
			
			if ( keyIndex != ( this.entries.length - 1 ) )
				System.arraycopy( this.entries, keyIndex + 1, newEntries, keyIndex, this.size - keyIndex - 1 );
			
			this.entries = newEntries;
			
		}
		
		this.size--;
		
		// this.map.remove( key );
		
	}
	
	public K getKey(V value) {
		int idx = this.indexOfValue( value );
		return idx == -1 ? null : this.entries[ idx ].key;
		/*
		for ( Entry<K, V> e : this.map.entrySet() )
			if ( e.getValue().equals( value ) ) return e.getKey();
		return null;
		*/
	}
	
	public boolean containsKey(Object key) {
		return this.indexOfKey( key ) != -1;
		// return this.map.containsKey( key );
	}
	
	public boolean containsValue(Object value) {
		return this.indexOfValue( value ) != -1;
		// return this.map.containsValue( value );
	}
	
	public boolean containsEntry(Entry<?, ?> entry) {
		if ( entry == null ) return false;
		if ( this.size == 0 ) return false;
		for ( int i = 0; i < this.size; i++ )
			if ( entry.equals( this.entries[ i ] ) )
				return true;
		return false;
	}
	
	public int size() {
		// return this.map.size();
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	public List<Entry<K, V>> entries() {
		return this.entrySet.get();
		// return this.map.entrySet();
	}
	
	public List<K> keys() {
		return this.keySet.get();
		// return this.map.keySet();
	}
	
	/*
	public List<V> values() {
		return new ArrayList<>( this.map.values() );
	}
	*/
	
	public List<V> values() {
		return this.valueSet.get();
	}
	
	public Entry<K, V>[] entriesArray() {
		RegistryEntry<K, V>[] copy = new RegistryEntry[ this.size ];
		System.arraycopy( this.entries, 0, copy, 0, this.size );
		return copy;
	}
	
	public K[] keysArray() {
		Object[] copy = new Object[ this.size ];
		for ( int i = 0; i < this.size; i++ )
			copy[ i ] = this.entries[ i ].key;
		return (K[]) copy;
	}
	
	public V[] valuesArray() {
		Object[] copy = new Object[ this.size ];
		for ( int i = 0; i < this.size; i++ )
			copy[ i ] = this.entries[ i ].value;
		return (V[]) copy;
	}
	
	public Entry<K, V> getEntryAt(int idx) {
		this.checkIndex( idx );
		return this.entries[ idx ];
	}
	
	public V getAt(int idx) {
		this.checkIndex( idx );
		return this.entries[ idx ].value;
		// return this.values().get( 0 );
	}
	
	public K getKeyAt(int idx) {
		this.checkIndex( idx );
		return this.entries[ idx ].key;
	}
	
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return new EntryIterator();
	}
	
	@Override
	public String toString() {
		
		TextBuilder b = new TextBuilder();
		
		for ( RegistryEntry<K, V> entry : this.entries ) {
			
			b.append( entry.key.toString() ).append(" = ").append( entry.value.toString() ).nl();
			
		}
		
		return b.toString();
		
	}
	
	private static class RegistryEntry<K, V> implements Entry<K, V> {

		private final K key;
		private V value;
		
		public RegistryEntry(K key, V value) {
			
			this.key = key;
			this.value = value;
			
		}
		
		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}
		
		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean equals(Object obj) {
			if ( obj == this ) return true;
			if ( !( obj instanceof Entry ) ) return false;
			Entry<?, ?> entry = (Entry<?, ?>) obj;
			return entry.getKey().equals( this.key ) && entry.getValue().equals( this.value );
		}
		
	}
	
	private abstract class MappedCollection<E> implements Collection<E> {

		@Override
		public int size() {
			return Registry.this.size;
		}

		@Override
		public boolean isEmpty() {
			return Registry.this.size == 0;
		}

		@Override
		public boolean add(E e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			for ( Object b : c )
				if ( !this.contains( b ) )
					return false;
			return true;
		}
		
	}
	
	private abstract class MappedList<E> extends MappedCollection<E> implements List<E> {
		
		@Override
		public void add(int index, E element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public E set(int index, E element) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public E remove(int index) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private abstract class MappedIterator<E> implements Iterator<E> {
		
		protected int cursor;
		
		public MappedIterator(int cursor) {
			this.cursor = cursor;
		}
		
		public MappedIterator() {
			this( 0 );
		}
		
		public abstract E get(int idx);
		
		@Override
		public boolean hasNext() {
			return this.cursor != Registry.this.size;
		}
		
		@Override
		public E next() {
			
			if ( this.cursor == Registry.this.size )
				throw new NoSuchElementException();
			
			this.cursor++;
			return this.get( this.cursor - 1 );
			
		}
		
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private abstract class MappedListIterator<E> extends MappedIterator<E> implements ListIterator<E> {
		
		public MappedListIterator(int cursor) {
			super( cursor );
		}
		
		public MappedListIterator() {
			super();
		}
		
		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean hasPrevious() {
			return this.cursor != 0;
		}
		
		@Override
		public int nextIndex() {
			return this.cursor + 1;
		}
		
		@Override
		public E previous() {
			
			if ( this.cursor == 0 )
				throw new NoSuchElementException();
			
			this.cursor--;
			return this.get( this.cursor + 1 );
			
		}
		
		@Override
		public int previousIndex() {
			return this.cursor - 1;
		}
		
		@Override
		public void set(E e) {
			throw new UnsupportedOperationException();
		}
		
	}
	
	private class EntriesMappedList extends MappedList<Entry<K, V>> {

		@Override
		public boolean contains(Object o) {
			if ( !( o instanceof Entry<?, ?> ) ) return false;
			return Registry.this.containsEntry( (Entry<?, ?>) o );
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntryIterator();
		}

		@Override
		public Object[] toArray() {
			return Registry.this.entriesArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return (T[]) Registry.this.entriesArray();
		}

		@Override
		public Entry<K, V> get(int index) {
			return Registry.this.getEntryAt( index );
		}

		@Override
		public int indexOf(Object o) {
			if ( !( o instanceof Entry ) ) return -1;
			return Registry.this.indexOfEntry( (Entry<?, ?>) o );
		}

		@Override
		public int lastIndexOf(Object o) {
			if ( !( o instanceof Entry ) ) return -1;
			return Registry.this.lastIndexOfEntry( (Entry<?, ?>) o );
		}

		@Override
		public ListIterator<Entry<K, V>> listIterator() {
			return new EntryListIterator();
		}

		@Override
		public ListIterator<Entry<K, V>> listIterator(int index) {
			Registry.this.checkIndex( index );
			return new EntryListIterator( index );
		}
		
	}
	
	private class EntryIterator extends MappedIterator<Entry<K, V>> {

		@Override
		public Entry<K, V> get(int idx) {
			return Registry.this.entries[ idx ];
		}
		
	}
	
	private class EntryListIterator extends MappedListIterator<Entry<K, V>> {

		public EntryListIterator(int cursor) {
			super( cursor );
		}
		
		public EntryListIterator() {
			super();
		}
		
		@Override
		public Entry<K, V> get(int idx) {
			return Registry.this.entries[ idx ];
		}
		
	}
	
	private class KeyMappedList extends MappedList<K> {

		@Override
		public boolean contains(Object o) {
			return Registry.this.containsKey( o );
		}

		@Override
		public Iterator<K> iterator() {
			return new KeyIterator();
		}

		@Override
		public Object[] toArray() {
			return Registry.this.keysArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return (T[]) Registry.this.entriesArray();
		}

		@Override
		public K get(int index) {
			return Registry.this.getKeyAt( index );
		}

		@Override
		public int indexOf(Object o) {
			return Registry.this.indexOfKey( o );
		}

		@Override
		public int lastIndexOf(Object o) {
			return Registry.this.lastIndexOfKey( o );
		}

		@Override
		public ListIterator<K> listIterator() {
			return new KeyListIterator();
		}

		@Override
		public ListIterator<K> listIterator(int index) {
			return new KeyListIterator( index );
		}
		
	}
	
	private class KeyIterator extends MappedIterator<K> {

		@Override
		public K get(int idx) {
			return Registry.this.entries[ idx ].key;
		}

	}
	
	private class KeyListIterator extends MappedListIterator<K> {

		public KeyListIterator(int cursor) {
			super( cursor );
		}
		
		public KeyListIterator() {
			super();
		}
		
		@Override
		public K get(int idx) {
			return Registry.this.entries[ idx ].key;
		}
		
	}
	
	private class ValueMappedList extends MappedList<V> {

		@Override
		public boolean contains(Object o) {
			return Registry.this.containsValue( o );
		}

		@Override
		public Iterator<V> iterator() {
			return new ValueIterator();
		}

		@Override
		public Object[] toArray() {
			return Registry.this.valuesArray();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			return (T[]) Registry.this.entriesArray();
		}

		@Override
		public V get(int index) {
			return Registry.this.getAt( index );
		}

		@Override
		public int indexOf(Object o) {
			return Registry.this.indexOfValue( o );
		}

		@Override
		public int lastIndexOf(Object o) {
			return Registry.this.lastIndexOfValue( o );
		}

		@Override
		public ListIterator<V> listIterator() {
			return new ValueListIterator();
		}

		@Override
		public ListIterator<V> listIterator(int index) {
			Registry.this.checkIndex( index );
			return new ValueListIterator( index );
		}
		
	}
	
	private class ValueIterator extends MappedIterator<V> {

		@Override
		public V get(int idx) {
			return Registry.this.entries[ idx ].value;
		}
		
	}
	
	private class ValueListIterator extends MappedListIterator<V> {

		public ValueListIterator(int cursor) {
			super( cursor );
		}
		
		public ValueListIterator() {
			super();
		}
		
		@Override
		public V get(int idx) {
			return Registry.this.entries[ idx ].value;
		}
		
	}
	
}
