package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.*;

/**
 * List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E>, Serializable {
    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private  E[] elems;

    /**
     * Number of elements in array.
     */
    private  int counter;

    /**
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Returns true iff the list contains no elements.
     *
     * @return true if list is empty
     * Compelxity: O(1)
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return number of elements in the list
     * Compelxity: O(1)
     */
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     *
     * @return Iterator of the elements in the list
     * Compelxity: O(1)
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Returns the first element of the list.
     *
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     * Compelxity: O(1)
     */
    public E getFirst() {
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return elems[0];
    }

    /**
     * Returns the last element of the list.
     *
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     * Compelxity: O(1)
     */
    public E getLast() {
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        return elems[counter -1];
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     *
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     * Compelxity: O(1)
     */
    public E get(int position) throws InvalidPositionException {
        //TODO: Left as an exercise.
        if (position <0 || position> size()) throw new InvalidPositionException();
        return elems[position];
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     *
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     * Compelxity: O(n)
     */
    public int indexOf(E element) {
        //TODO: Left as an exercise.
        int i =0;
        int result = -1;
        boolean found = false;
        while (i<counter && !found)
            if (elems[i].equals(element))
                found = true;
            else
                i++;
        if (found) result = i;
        return result;
    }

    /**
     * Inserts the specified element at the first position in the list.
     *
     * @param element to be inserted
     * Compelxity: O(n)
     */
    public void addFirst(E element) {
        //TODO: Left as an exercise.
        for(int i = counter-1; i >= 0; i--)
            elems[i+1] = elems[i];
        elems[0] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     *
     * @param element to be inserted
     * Compelxity: O(1)
     */
    public void addLast(E element) {
        //TODO: Left as an exercise.
        elems[counter++] = element;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     *
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     * Compelxity: O(n)
     */
    public void add(int position, E element) throws InvalidPositionException {
        //TODO: Left as an exercise.
        if (position <0 || position> size() -1) throw new InvalidPositionException();
        if (position == 0) addFirst(element);
        else if(position == counter) addLast(element);
        else {
            for (int i = counter - 1; i >= position; i--)
                elems[i + 1] = elems[i];
            elems[position] = element;
            counter++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     *
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     * Compelxity: O(n)
     */
    public E removeFirst() {
        //TODO: Left as an exercise.
        E removed = getFirst();
        elems[0] = null;
        for(int i = 0; i <= counter -1; i++)
            elems[i] = elems[i + 1];
        counter--;
        return removed;
    }

    /**
     * Removes and returns the element at the last position in the list.
     *
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     * Compelxity: O(1)
     */
    public E removeLast() throws NoSuchElementException{
        //TODO: Left as an exercise.
        if (size() == 0) throw new NoSuchElementException();
        E removed = getLast();
        elems[--counter] = null;
        return removed;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     *
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     * Compelxity: O(n)
     */
    public E remove(int position) {
        //TODO: Left as an exercise.
        if (position <0 || position> size() -1) throw new InvalidPositionException();
        E removed = get(position);
        if (position == 0) removeFirst();
        else if(position == counter -1) removeLast();
        else {
            elems[position] = null;
            for(int i = position; i <= counter; i++)
                elems[i] = elems[i + 1];
            counter--;
            return removed;
        }
        return removed;
    }
}
