package misc.learning.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheUsingLinkedHashMap<K, V> {

	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		// cache with seize 4
		LRUCacheUsingLinkedHashMap<String, String> cache = new LRUCacheUsingLinkedHashMap<String, String>(4);
		
		cache.put("1", "A");
		cache.put("2", "B");
		cache.put("3", "C");
		cache.put("4", "D");
		cache.put("5", "E");

		// drops 'A' from the cache since it is the LRU	element
		// access order instead of insertion order
		System.out.println(cache.get("1")); // returns null
	}
	
	
	private static final float hashTableLoadFactor = 0.75f; // default load factor of 0.75
	private LinkedHashMap<K, V> map;
	private int cacheSize;

	/**
	 * Creates a new LRU cache.
	 * 
	 * @param size
	 *            the maximum number of entries that will be kept in this cache.
	 */
	public LRUCacheUsingLinkedHashMap(int size) {
		
		this.cacheSize = size;
		
		int hashTableCapacity = (int) Math.ceil(size / hashTableLoadFactor) + 1;
		
		//access order instead of insertion order
		map = new LinkedHashMap<K, V>(hashTableCapacity, hashTableLoadFactor, true) {
			// (an anonymous inner class)
			private static final long serialVersionUID = 1;

			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > LRUCacheUsingLinkedHashMap.this.cacheSize;
			}
		};
	}

	/**
	 * Retrieves an entry from the cache.<br>
	 * The retrieved entry becomes the MRU (most recently used) entry.
	 * 
	 * @param key
	 *            the key whose associated value is to be returned.
	 * @return the value associated to this key, or null if no value with this
	 *         key exists in the cache.
	 */
	public synchronized V get(K key) {
		return map.get(key);
	}

	/**
	 * Adds an entry to this cache. The new entry becomes the MRU (most recently
	 * used) entry. If an entry with the specified key already exists in the
	 * cache, it is replaced by the new entry. If the cache is full, the LRU
	 * (least recently used) entry is removed from the cache.
	 * 
	 * @param key
	 *            the key with which the specified value is to be associated.
	 * @param value
	 *            a value to be associated with the specified key.
	 */
	public synchronized void put(K key, V value) {
		map.put(key, value);
	}

	public synchronized void clear() {
		map.clear();
	}

	public synchronized int usedEntries() {
		return map.size();
	}

} 

