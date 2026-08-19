package dataStructures;
/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class MapSinglyList<K,V> implements Map<K, V> {


    private SinglyListNode<Entry<K,V>> head;

    private int size;

    public MapSinglyList() {
        //TODO: Left as exercise
        size = 0;
        head = null;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     * Time Complexity: O(1)
     */
  
    public boolean isEmpty() {
	//TODO: Left as exercise
        return size == 0;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     * Time Complexity: O(1)
     */
    @Override
    public int size() {
	//TODO: Left as exercise
        return size;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     *
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */

    //Time Complexity: best-case -> O(1) and worst-case -> O(n)
    @Override
    public V get(K key) {
        //TODO: Left as exercise
        SinglyListNode<Entry<K,V>> current = head;
        while(current != null){
            Entry<K,V> study = current.getElement();
            if(study.key().equals(key)){
                return study.value();
            }
            current = current.getNext();
        }
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
    //Time Complexity: best-case -> O(1) and worst-case -> O(n)
    public V put(K key, V value) {
        //TODO: Left as an exercise.
        SinglyListNode<Entry<K,V>> found = getSinglyList(key);

        if(head == null){
            head = new SinglyListNode<>(new Entry<>(key, value));
        } else if(found != null){
            V oldValue = found.getElement().value();
            found.setElement(new Entry<>(key, value));
            return oldValue;
        }else {
            head = new SinglyListNode<>(new Entry<>(key, value), head);
        }

        size++;
        return  null;
    }

    //Time Complexity: best-case -> O(1) and worst-case -> O(n)
    private SinglyListNode<Entry<K,V>> getSinglyList(K key) {
        SinglyListNode<Entry<K,V>> current = head;
        while(current != null){
            Entry<K,V> study = current.getElement();
            if(study.key().equals(key)){
               return current;
            }
            current = current.getNext();
        }
        return null;
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
    //Time Complexity: best-case -> O(1) and worst-case -> O(n)
    public V remove(K key) {
        //TODO: Left as an exercise.
        SinglyListNode<Entry<K,V>> current = head;
        SinglyListNode<Entry<K,V>> previousNode = null;

        while(current != null){
            Entry<K,V> study = current.getElement();
            if(study.key().equals(key)){
                V oldValue = study.value();

                if(previousNode == null){
                    head = current.getNext();

                }else{
                    previousNode.setNext(current.getNext());
                }
                size--;
                return oldValue;
            }
            previousNode = current;
            current = current.getNext();

        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
@SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }

}
