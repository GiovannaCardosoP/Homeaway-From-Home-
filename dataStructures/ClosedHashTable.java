package dataStructures;

/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable(){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable( int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));

        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;

        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    // from classes

    private int hash1(String key) {
        int h = 0;
        for (char c : key.toCharArray()) h += c;
        return h % table.length;
    }
    private int hash2(String key) {
        int h = 0;
        for (char c : key.toCharArray()) h += c;
        int num = table.length - 1;

        while (num > 1 && !isPrime(num)) {
            num--;
        }
        return num - (h %  num);
    }

    public int insertPos(String key) {
        int h1 = hash1(key);
        int h2 = hash2(key);
        int i = 0;

        while (table[ (h1 + i * h2) % table.length] != null && table[ (h1 + i * h2) % table.length] != REMOVED_CELL ) {
            i++;
        }
        return (h1 + i * h2) % table.length;
    }


    /**
     * Linear Proving
     * @param key to search
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        //TODO: Left as an exercise.

        String ks = key.toString();
        int h1 = hash1(ks);
        int h2 = hash2(ks);
        int m  = table.length;


        for (int i = 0; i < m; i++) {
            int pos = (h1 + i * h2) % m;
            Entry<K, V> e = table[pos];

            if (e == null) {
                return NOT_FOUND;
            }

            if (e != REMOVED_CELL && e.key().equals(key)) {
                return pos;
            }

        }

        return NOT_FOUND;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        //TODO: Left as an exercise.
        int index = searchLinearProving(key);
        if(index != NOT_FOUND) return table[index].value();
        return null;
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
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();
        //TODO: Left as an exercise.
        int index = searchLinearProving(key);
        if (index != NOT_FOUND) {
            V oldVal = table[index].value();
            table[index] = new Entry<>(key, value);
            return oldVal;
        } else {
            index = insertPos(key.toString());
            table[index] = new Entry<>(key, value);
        }
        currentSize++;
        return null;
    }


    @SuppressWarnings("unchecked")
     private void rehash() {
        //TODO: Left as an exercise.

        Entry<K,V>[] old = table;

        int newArraySize = HashTable.nextPrime(2 * table.length);
        table = (Entry<K, V>[]) new Entry[newArraySize];
        currentSize = 0;
        maxSize = (int)(newArraySize * MAX_LOAD_FACTOR);

        for (Entry<K,V> e : old) {

            if (e != null && e != REMOVED_CELL) {
                put(e.key(),e.value());
            }
        }
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
    @Override
    @SuppressWarnings("unchecked")
    public V remove(K key) {
        //TODO: Left as an exercise.
        int index = searchLinearProving(key);
        if (index != NOT_FOUND) {
            V oldValue = table[index].value();
            table[index] = (Entry<K, V>) REMOVED_CELL;
            currentSize--;
            return oldValue;
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Iterator<Entry<K, V>> iterator() {
         //TODO: Left as an exercise.
        return new FilterIterator(new ArrayIterator(table,table.length), m -> m!=null && m!= REMOVED_CELL);
    }

}
