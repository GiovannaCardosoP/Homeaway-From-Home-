package dataStructures;

/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity ){
        //TODO: Left as exercise
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        table = (MapSinglyList<K,V>[]) new MapSinglyList[arraySize];
        for ( int i = 0; i < arraySize; i++ ) {
            table[i] = new MapSinglyList<>();
        }
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);

    }

    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    public V get(K key) {
        //TODO: Left as an exercise.
        V value= table[hash(key)].get(key);
        return value;

    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     *
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        if (isFull())
            rehash();
        //TODO: Left as an exercise.
        int hash = hash(key);
        if(table[hash] == null){
            table[hash] = new MapSinglyList<>();
            table[hash].put(key, value);
            currentSize++;
            return null;
        }
        V oldValue = table[hash].get(key);
        if(oldValue == null) currentSize++;
        table[hash].put(key, value);
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    private void rehash() {
        //TODO: Left as an exercise.
        int newArraySize = HashTable.nextPrime(2 * table.length);
        MapSinglyList<K,V>[] newTable = (MapSinglyList<K,V>[]) new MapSinglyList[newArraySize];

        for (int i = 0; i < newArraySize; i++) {
            newTable[i] = new MapSinglyList<>();
        }

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isEmpty()) {
                Iterator<Entry<K,V>> current = table[i].iterator();

                while (current.hasNext()) {
                    Entry<K,V> study = current.next();
                    int newIndex = Math.abs(study.key().hashCode()) % newArraySize;
                    newTable[newIndex].put(study.key(), study.value());
                }
            }
        }
        table = newTable;
        maxSize = (int)(newArraySize * MAX_LOAD_FACTOR);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     *
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        //TODO: Left as an exercise.
        int hash = hash(key);
        V oldValue = table[hash].get(key);
        if(table[hash] != null && !table[hash].isEmpty()){
           table[hash].remove(key);
           currentSize--;
        }
        return oldValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }


}
