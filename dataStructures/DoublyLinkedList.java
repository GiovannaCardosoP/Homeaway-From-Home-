package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * Doubly Linked List
 *
 * @author AED team
 * @version 1.0
 *
 * @param <E> Generic Element
 */
public class DoublyLinkedList<E> implements TwoWayList<E>,Serializable {
    /**
     *  Node at the head of the list.
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
     * Complexity: O(n)
     *
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
    }

    /**
     * Complexity: O(n)
     *
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addLast(element);
        }
    }

    /**
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        //TODO: Left as an exercise.
        head = null;
        tail = null;
        currentSize =0;

    }

    /**
     * Returns true iff the list contains no elements.
     * @return true if list is empty
     * Complexity: O(1)
     */
    public boolean isEmpty() {
        //TODO: Left as an exercise.
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * @return number of elements in the list
     * omplexity: O(1)
     */

    public int size() {
        //TODO: Left as an exercise.
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     * @return Two-Way Iterator of the elements in the list
     * omplexity: O(1)
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * @return Iterator of the elements in the list
     * omplexity: O(1)
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * @param element - Element to be inserted
     * Complexity: O(1)
     */
    public void addFirst( E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        if (currentSize == 0){
            tail = newNode;
            head = newNode;
        }else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
        }
        currentSize++;
    }

    /**
     * Inserts the element at the last position in the list.
     * @param element - Element to be inserted
     * Complexity: O(1)
     */
    public void addLast( E element ) {
        //TODO: Left as an exercise.
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        if (currentSize == 0){
            head = newNode;
            head.setPrevious(null);
            tail = newNode;
            tail.setNext(null);
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        currentSize++;

    }

    /**
     * Returns the first element of the list.
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E getFirst( ) throws NoSuchElementException{
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E getLast( ) throws NoSuchElementException{
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return tail.getElement();
    }


    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     * Complexity: best-case -> O(1) and worst-case -> O(n)
     */
    public E get( int position ) throws InvalidPositionException{
        //TODO: Left as an exercise.
        if (position <0 || position> size() -1) throw new InvalidPositionException();
        DoublyListNode<E> current = head;
        int i=0;
        while (i < position){
            current = current.getNext();
            i++;
        }
        return current.getElement();
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     * Complexity: best-case -> O(1) and worst-case -> O(n)
     */
    public int indexOf( E element ) {
        //TODO: Left as an exercise.
        int i=0;
        DoublyListNode<E> current = head;
        while(current != null && !current.getElement().equals(element)){
            current = current.getNext();
            i++;
        }
        if (current == null) i =-1;
        return i;

    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     * Complexity: best-case -> O(1) and worst-case -> O(n)
     */
    public void add( int position, E element ) throws InvalidPositionException {
        //TODO: Left as an exercise.
        if (position <0 || position> size()) throw new InvalidPositionException();
        if (position == 0) {
            addFirst(element);
        } else if (position == size()){
            addLast(element);
        }
        else {
            DoublyListNode<E> newNode = new DoublyListNode<>(element);
            DoublyListNode<E> oldNode = head;
            int i = 0;
            while (i < position) {
                oldNode = oldNode.getNext();
                i++;
            }
            DoublyListNode<E> previousNode = oldNode.getPrevious();
            previousNode.setNext(newNode);
            oldNode.setPrevious(newNode);
            newNode.setNext(oldNode);
            newNode.setPrevious(previousNode);
            currentSize++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E removeFirst( ) throws NoSuchElementException {
        //TODO: Left as an exercise.
       if (size() == 0) throw new NoSuchElementException();
       E element = head.getElement();
       if(currentSize == 1){
           head = null;
           tail = null;
       } else{
           DoublyListNode<E> secondNode = head.getNext();
           secondNode.setPrevious(null);
           head = secondNode;
       }
       currentSize--;
       return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     * Complexity: O(1)
     */
    public E removeLast( ) throws NoSuchElementException {
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        E element = tail.getElement();
        if(currentSize == 1){
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

    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     * Complexity: best-case -> O(1)  and worst-case -> O(n)
     */
    public E remove( int position ) throws InvalidPositionException{
        //TODO: Left as an exercise.
        if (position <0 || position> size() -1) throw new InvalidPositionException();
        E element = null;
        if (position == 0) { element = removeFirst();
        }else if (position == size() -1){ element = removeLast();
        } else {
            DoublyListNode<E> removedNode = head;

            int i = 0;
            while (i < position) {
                removedNode = removedNode.getNext();
                i++;
            }
            DoublyListNode<E> nextNode = removedNode.getNext();
            DoublyListNode<E> previousNode = removedNode.getPrevious();
            element = removedNode.getElement();

            previousNode.setNext(nextNode);
            nextNode.setPrevious(previousNode);
            currentSize--;
        }
        return element;
    }

}
