package dataStructures;
/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{
    /**
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        //TODO: Left as an exercise.
        // If exists a entry with this Key, update the node with new element
        // and return the old value of the entry
        // otherwise, insert the newNode, "rebalance" from the insertion position
        // and return value

        if(root == null){
            root= new AVLNode<>(new Entry<>(key,value));
            currentSize++;
            return null;
        } else {
            AVLNode<Entry<K, V>> current = (AVLNode<Entry<K, V>>) root;
            while (current != null) {// continue until one of the returns
                int cmp = key.compareTo(current.getElement().key());

                if (cmp == 0) {
                    V oldValue = current.getElement().value();
                    current.setElement(new Entry<>(key, value));
                    return oldValue;
                }
                if (cmp > 0) {
                    //rebalance
                    if (current.getRightChild() == null) {
                        AVLNode<Entry<K, V>> newNode = new AVLNode<>(new Entry<>(key, value));
                        current.setRightChild(newNode);
                        newNode.setParent(current);
                        currentSize++;
                        rebalance(newNode);
                        return null;
                    }

                    current = (AVLNode<Entry<K, V>>) current.getRightChild();

                } else {
                    if (current.getLeftChild() == null) {
                        AVLNode<Entry<K, V>> newNode = new AVLNode<>(new Entry<>(key, value));
                        current.setLeftChild(newNode);
                        newNode.setParent(current);
                        currentSize++;
                        rebalance(newNode);
                        return null;
                    }
                    current = (AVLNode<Entry<K, V>>) current.getLeftChild();
                }
            }
            return current.getElement().value();
        }
    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @return
     */
    public V remove(K key) {
        //TODO: Left as an exercise.
        // If does not exist a entry with this Key, return null
        // otherwise, remov8e the node where is the element with this key,
        // "rebalance" from the removal position and return value
        AVLNode<Entry<K, V>> removed = (AVLNode<Entry<K, V>>) getNode((AVLNode<Entry<K, V>>) root, key);
            if (root == null || removed == null) return null;
            V oldValue = removed.getElement().value();
            AVLNode<Entry<K, V>> parentOfRemovedNode = (AVLNode<Entry<K,V>>) removed.getParent();
            if (currentSize == 1) {root = null;
            } else if ((removed.getLeftChild() == null && removed.getRightChild() == null)) { //não tem filhos
                if (parentOfRemovedNode.getLeftChild() == removed) { //.
                    parentOfRemovedNode.setLeftChild(null);
                } else {
                    parentOfRemovedNode.setRightChild(null);
                }
            }

            else if (removed.getLeftChild() == null || removed.getRightChild() == null) {
                AVLNode<Entry<K, V>> onlyChild =  getOnlyChild(removed);

                if (removed == root) {
                    root = onlyChild;
                    onlyChild.setParent(null);
                } else if (parentOfRemovedNode.getRightChild() != null && parentOfRemovedNode.getRightChild() == removed) { // há rigthChild
                    parentOfRemovedNode.setRightChild(onlyChild);
                    onlyChild.setParent(parentOfRemovedNode);
                } else {
                    parentOfRemovedNode.setLeftChild(onlyChild);
                    onlyChild.setParent(parentOfRemovedNode);
                }

            } else {
                AVLNode<Entry<K, V>> temp = (AVLNode<Entry<K, V>>) removed.getRightChild();
                temp = (AVLNode<Entry<K, V>>) temp.furtherLeftElement();
                AVLNode<Entry<K, V>> parentOfTemp = (AVLNode<Entry<K,V>>) temp.getParent();
                AVLNode<Entry<K, V>> onlyChild =  getOnlyChild(temp);
                if (onlyChild != null) {
                    if (parentOfTemp.getLeftChild() == temp) {
                        parentOfTemp.setLeftChild(onlyChild);
                    } else {
                        parentOfTemp.setRightChild(onlyChild);
                    }
                    onlyChild.setParent(parentOfTemp);
                } else {
                    if (parentOfTemp.getLeftChild() == temp) {
                        parentOfTemp.setLeftChild(null);
                    } else {
                        parentOfTemp.setRightChild(null);
                    }
                }
                removed.setElement(temp.getElement());
            }
            rebalance(removed);
            currentSize--;
            return oldValue;
        }

        private AVLNode<Entry<K,V>> getOnlyChild(BTNode<Entry<K,V>> parent) {
            AVLNode<Entry<K,V>> leftChild = (AVLNode<Entry<K,V>>) parent.getLeftChild();
            AVLNode<Entry<K,V>> rightChild = (AVLNode<Entry<K,V>>) parent.getRightChild();

            if (parent.getRightChild() != null && parent.getLeftChild() == null ) return rightChild;
            else return leftChild;
        }


    private void rebalance(AVLNode<Entry<K, V>> current) {
        int bf = 0;
        AVLNode<Entry<K, V>> temp = current;
        do{
            int hl  = 0;
            int hr = 0;
            if(temp.getLeftChild() != null){
                hl = ((AVLNode<Entry<K, V>>) temp.getLeftChild()).getHeight();
            }
            if(temp.getRightChild() != null) {
                hr = ((AVLNode<Entry<K,V>>) temp.getRightChild()).getHeight();
            }
            bf = hl - hr;
            if(bf > 1 || bf < -1){
                AVLNode<Entry<K,V>> newRoot = (AVLNode<Entry<K, V>>) restructure(temp);
                if (newRoot.getParent() == null) {
                    root = newRoot;
                }
                newRoot.setHeight((AVLNode<Entry<K, V>>) newRoot.getLeftChild(),
                        (AVLNode<Entry<K, V>>) newRoot.getRightChild());

                temp = newRoot;
            }
            temp.setHeight((AVLNode<Entry<K,V>>) temp.getLeftChild(),
                    (AVLNode<Entry<K,V>>) temp.getRightChild());
            temp = (AVLNode<Entry<K, V>>) temp.getParent();
        }while(temp != null);
    }
}
