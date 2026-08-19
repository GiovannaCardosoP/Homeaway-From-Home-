package dataStructures;

import dataStructures.exceptions.EmptyMapException;

import java.io.Serializable;

/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{
    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }

    /**
     * Returns the entry with the smallest key in the dictionary.
     *
     * @return min Entry
     * @throws EmptyMapException if the dictionary has no entries
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     *
     * @return max Entry
     * @throws EmptyMapException if the dictionary has no entries
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
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
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }


    BTNode<Entry<K,V>> getNode(BTNode<Entry<K, V>> node, K key) {
        //TODO: Left as an exercise.
        BTNode<Entry<K,V>> current = node;

        while(current!=null){
            if(key.compareTo(current.getElement().key()) == 0){
                return current;
            } else if(key.compareTo(current.getElement().key()) > 0){
                current = (BTNode<Entry<K, V>>) current.getRightChild();
            } else{
                current = (BTNode<Entry<K, V>>) current.getLeftChild();
            }
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
    @Override
    public V put(K key, V value) {
        //TODO: Left as an exercise.
        // FIRST NODE
        if(root ==null){
            //BTNode<Entry<K,V>> newRoot = new BTNode<>(new Entry<>(key,value));
            root= new BTNode<>(new Entry<>(key,value));
            currentSize++;
            return null;
        } else {
            BTNode<Entry<K, V>> current = (BTNode<Entry<K, V>>) root;
            while (current != null) {
                if (key.compareTo(current.getElement().key()) == 0) {
                    V oldValue = current.getElement().value();
                    current.setElement(new Entry<>(key, value));
                    return oldValue;
                }
                else if (key.compareTo(current.getElement().key()) > 0) {
                    if (current.getRightChild() == null) {
                        BTNode<Entry<K, V>> newNode = new BTNode<>(new Entry<>(key,value));
                        current.setRightChild(newNode);
                        newNode.setParent(current);
                        currentSize++;
                        return null;
                    } else {
                        current = (BTNode<Entry<K, V>>) current.getRightChild();
                    }
                }
                else {
                    if (current.getLeftChild() == null) {
                        BTNode<Entry<K, V>> newNode = new BTNode<>(new Entry<>(key,value));
                        current.setLeftChild(newNode);
                        newNode.setParent(current);
                        currentSize++;
                        return null;
                    } else {
                        current = (BTNode<Entry<K, V>>) current.getLeftChild();
                    }

                }
            }
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

    @Override
    public V remove(K key) {
        //TODO: Left as an exercise.
        BTNode<Entry<K, V>> removed = getNode((BTNode<Entry<K, V>>) root, key);
        if (root == null || removed == null) return null;
        V oldValue = removed.getElement().value();
        BTNode<Entry<K, V>> parentOfRemovedNode = (BTNode<Entry<K, V>>) removed.getParent();
        if (currentSize == 1) {
            root = null;
        } else if ((removed.getLeftChild() == null && removed.getRightChild() == null)) { //não tem filhos
            if (parentOfRemovedNode.getLeftChild() == removed) { //.
                parentOfRemovedNode.setLeftChild(null);
            } else {
                parentOfRemovedNode.setRightChild(null);
            }
        } else if(removed.getLeftChild() == null || removed.getRightChild() == null){
            BTNode<Entry<K, V>> onlyChild = getOnlyChild(removed);

            if(removed == root){
                root=onlyChild;
                onlyChild.setParent(null);
            } else if( parentOfRemovedNode.getRightChild() != null && parentOfRemovedNode.getRightChild() == removed ){ // há rigthChild
                parentOfRemovedNode.setRightChild(onlyChild);
                onlyChild.setParent(parentOfRemovedNode);
            }else{
                parentOfRemovedNode.setLeftChild(onlyChild);
                onlyChild.setParent(parentOfRemovedNode);
            }

        }else {
            BTNode<Entry<K, V>> temp = (BTNode<Entry<K, V>>) removed.getRightChild();
            temp = temp.furtherLeftElement();
            BTNode<Entry<K, V>> parentOfTemp = (BTNode<Entry<K, V>>) temp.getParent();
            BTNode<Entry<K, V>> onlyChild = getOnlyChild(temp);
            if (onlyChild != null) {
                if(parentOfTemp.getLeftChild() == temp) parentOfTemp.setLeftChild(onlyChild);
                else parentOfTemp.setRightChild(onlyChild);
                onlyChild.setParent(parentOfTemp);
            } else {
                if (parentOfTemp.getLeftChild() == temp) parentOfTemp.setLeftChild(null);
                else parentOfTemp.setRightChild(null);
            }
            removed.setElement(temp.getElement());
        }
        currentSize--;
        return oldValue;

    }

    private BTNode<Entry<K,V>> getOnlyChild(BTNode<Entry<K,V>> parent) {
        BTNode<Entry<K,V>> leftChild = (BTNode<Entry<K,V>>) parent.getLeftChild();
        BTNode<Entry<K,V>> rightChild = (BTNode<Entry<K,V>>) parent.getRightChild();
        if (parent.getRightChild() != null && parent.getLeftChild() == null ) return rightChild;
        else return leftChild;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }
}
