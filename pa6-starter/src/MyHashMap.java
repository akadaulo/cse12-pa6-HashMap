import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		// TODO Finish initializing instance fields
		if(initialCapacity < 0){
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		}

		if(loadFactor <= 0){
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		}

		this.loadFactor = loadFactor; 
		this.capacity = initialCapacity;
		this.size = 0;

		// if you use Separate Chaining
		buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];

		// if you use Linear Probing
		entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// can also use key.hashCode() assuming key is not null
		int keyHash = Objects.hashCode(key); 
		// TODO Auto-generated method stub
		if(key == null){
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
			}
		int index = getIndex(key);
		if(buckets[index] == null){
			buckets[index] = new ArrayList<>();
		}

		for(HashMapEntry<K,V> entry : buckets[index]){
			if(entry.getKey().equals(key)){
				return false;
			}
		}

		buckets[index].add(new HashMapEntry<>(key, value));
		size++;

		if((double) size/ capacity > loadFactor){
			rehash();
		}






		return  true;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		// TODO Auto-generated method stub
			if(key == null){
				throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
			}
			int index = getIndex(key);
			if(buckets[index] == null){
				return false;
			}

			for(HashMapEntry<K,V> entry : buckets[index]){
				if(entry.getKey().equals(key)){
					entry.setValue(newValue);
					return true;
				}
			}

		return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null){
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}

		int index = getIndex(key);
		if(buckets[index] == null){
			return false;
		}

		for(HashMapEntry<K,V> entry : buckets[index]){
			if(entry.getKey().equals(key)){
				buckets[index].remove(entry);
				size--;
				return true;
			}
		}


		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null){
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int index = getIndex(key);
		if(buckets[index] == null){
			buckets[index] = new ArrayList<>();
		}

		for(HashMapEntry<K,V> entry: buckets[index]){
			if(entry.getKey().equals(key)){
				entry.setValue(value);
				return;
			}
		}

		buckets [index].add(new HashMapEntry<>(key, value));
		size++;

		if((double) size / capacity > loadFactor){
			rehash();
		}
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null){
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}

		int index = getIndex(key);
		if(buckets[index] == null){
			return null;
		}

		for(HashMapEntry<K, V> entry : buckets[index]){
			if(entry.getKey().equals(key)){
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if(key == null){
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}

		int index = getIndex(key);
		if(buckets[index] == null){
			return false;
		}

		for(HashMapEntry<K,V> entry : buckets[index]){
			if(entry.getKey().equals(key)){
				return true;
			}
		}


		return false;
	}

	@Override
	public List<K> keys() {
		// TODO Auto-generated method stub
		List<K> keysList = new ArrayList<>();
        for (List<HashMapEntry<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (HashMapEntry<K, V> entry : bucket) {
                    keysList.add(entry.getKey());
                }
            }
        }
        return keysList;
    }

	private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }


	@SuppressWarnings("unchecked")
    private void rehash() {
        List<HashMapEntry<K, V>>[] oldBuckets = buckets;
        capacity = capacity * 2;
        buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];
        size = 0;

        for (List<HashMapEntry<K, V>> bucket : oldBuckets) {
            if (bucket != null) {
                for (HashMapEntry<K, V> entry : bucket) {
                    put(entry.getKey(), entry.getValue());
                }
            }
        }
    }
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
