package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SortedDoublyLinkedList<E> implements SortedList<E>, Serializable {

    /**
     * Node at the head of the list.
     */
    private transient DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private transient DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;
    /**
     * Comparator of elements.
     */
    private final Comparator<E> comparator;

    //Complexity: O(n)
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
    }

    //Complexity: O(n^2) -> O(n) * O(n) (because each add is O(n))
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            //addLast(element);
            add(element);
        }
    }

    //ter um metodo addlast para compelxidade temporal para o readObject

    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        //TODO: Left as an exercise.
        this.comparator = comparator;
        head = null;
        tail = null;
        currentSize = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     * Complexity: O(1)
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     * Complexity: O(1)
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     * Complexity: O(1)
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E getMin() throws NoSuchElementException {
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E getMax() {
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return tail.getElement();
    }

    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     *
     * @return element in the list or null
     * Complexity: O(n)
     */
    public E get(E element) {
        //TODO: Left as an exercise.
        E found = null;
        DoublyListNode<E> current = head;
        int i = 0;
        //current pode ser NULL
        while (i < size() && found == null) {
            if (comparator.compare(current.getElement(),element) == 0) {
                found = current.getElement();
            }
            if (current != null) current = current.getNext();
            i++;
        }

        return found;
    }

    /**
     * Returns true iff the element exists in the list.
     *
     * @param element to be found
     * @return true iff the element exists in the list.
     * Complexity: O(n)
     */
    public boolean contains(E element) {
        //TODO: Left as an exercise.
        return get(element) != null;
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted
     * Complexity: O(n)
     */
    public void add(E element) {
        //TODO: Left as an exercise.
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        if (currentSize == 0) {
            tail = newNode;
            head = newNode;
        } else {
            DoublyListNode<E> current = head;
            if (comparator.compare(newNode.getElement(), head.getElement()) < 0) {
                addFirst(newNode, current);
            } else if (comparator.compare(newNode.getElement(), tail.getElement()) > 0) {
                addLast(newNode);
            } else {

                while (current.getNext() != null && comparator.compare(newNode.getElement(), current.getNext().getElement()) >= 0) {
                    current = current.getNext();
                }
                DoublyListNode<E> nextNode = current.getNext();
                newNode.setNext(nextNode);
                newNode.setPrevious(current);
                current.setNext(newNode);

                if (nextNode != null) {
                    nextNode.setPrevious(newNode);
                } else {
                    tail = newNode;
                }
            }

        }
        currentSize++;

    }

    private void addFirst(DoublyListNode<E> newNode, DoublyListNode<E> current) {
        newNode.setPrevious(null);
        newNode.setNext(current);
        current.setPrevious(newNode);
        head = newNode;
    }

    private void addLast(DoublyListNode<E> newNode) {
        newNode.setNext(null);
        newNode.setPrevious(tail);
        tail.setNext(newNode);
        tail = newNode;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     *
     * @return element removed from the list or null if !belongs(element)
     * Complexity: O(n)
     */
    public E remove(E element) {
        //TODO: Left as an exercise.
        E removed;
        int index = indexOf(element);
        if(index == -1) return null;
        if (index == 0) {
            removed = removeFirst();
        } else if (index == size() - 1) {
            removed = removeLast();
        } else {
            DoublyListNode<E> removedNode = head;

            int j = 0;
            while (j < index) {
                removedNode = removedNode.getNext();
                j++;
            }
            DoublyListNode<E> nextNode = removedNode.getNext();
            DoublyListNode<E> previousNode = removedNode.getPrevious();
            previousNode.setNext(removedNode.getNext());
            nextNode.setPrevious(previousNode);
            removed = removedNode.getElement();
            currentSize--;
        }
        return removed;

    }

    private E removeFirst() {
        //TODO: Left as an exercise.
        E element = head.getElement();
        if (currentSize == 1) {
            head = null;
            tail = null;
        } else {
            DoublyListNode<E> secondNode = head.getNext();
            secondNode.setPrevious(null);
            head = secondNode;
        }
        currentSize--;
        return element;
    }

    private E removeLast() {
        //TODO: Left as an exercise.
        E element = tail.getElement();
        if (currentSize == 1) {
            head = null;
            tail = null;
        } else {
            DoublyListNode<E> penultimateNode = tail.getPrevious();
            penultimateNode.setNext(null);
            tail = penultimateNode;
        }
        currentSize--;
        return element;
    }


    private int indexOf(E element) {
        //TODO: Left as an exercise.
        int currentIndex = -1;
        int i=0;
        DoublyListNode<E> current = head;

        while ( current != null && currentIndex == -1){
            if(comparator.compare(current.getElement(), element) == 0){
                currentIndex = i;
            }
            current = current.getNext();
            i++;
        }

        return currentIndex;
    }

}


