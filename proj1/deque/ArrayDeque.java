package deque;

import java.util.Arrays;
import java.util.Iterator;

/** add and remove must take constant time, except during resizing operations.
    get and size must take constant time.
    The starting size of your array should be 8.
    front always points at the first element.
    back always points at the position behind the last element.
 */
public class ArrayDeque<T> implements Iterable<T>, Deque<T> {

    private T[] items;
    private int size;

    /** point at the first element in items. */
    private int front;

    /** point at the last element in items. */
    private int back;

    /** Get the items from outside the class. */
    public T[] getItems() {
        return items;
    }

    /** Get front. */
    public int getFront() {
        return front;
    }

    /** Get back. */
    public int getBack() {
        return back;
    }

    /** Create an empty deque. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = front = back = 0;
    }

    /** Resize the items when deque is full. */
    public void resize(int capacity) {
        T[] new_items = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            int oldIndex = (front + i) % items.length;
            new_items[i] = items[oldIndex];
        }
        items = new_items;
        back = size - 1;
        front = 0;
    }

    /** Get the position of next front. */
    private int getNextFront(int front) {
        return front - 1 >= 0 ? front - 1 : items.length - 1;
    }

    /** Get the position of next back. */
    private int getNextBack(int back) {
        return back + 1 == items.length ? 0 : back + 1;
    }

    /**  Adds an item of type T to the front of the deque.
     * You can assume that item is never null. */
    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        if (isEmpty()) {
            items[front] = item;
        } else {
            int nextFront = getNextFront(front);
            items[nextFront] = item;
            front = nextFront;
        }
        ++size;
    }

    /** Adds an item of type T to the back of the deque.
     * You can assume that item is never null. */
    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (isEmpty()) {
            items[back] = item;
        } else {
            int nextBack = getNextBack(back);
            items[nextBack] = item;
            back = nextBack;
        }
        ++size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    @Override
    public void printDeque() {
        for (int i = front; i != back; i = (i + 1) % items.length) {
            int next = (i + 1) % items.length;
            if (next != back) {
                System.out.print(items[i] + " ");
            }
            else {
                System.out.println(items[i]);
            }
        }
    }

    /** Compute usagefactor. */
    private double usageFactor() {
        return (double) size / items.length;
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T first = items[front];
        items[front] = null;
        --size;
        if (!isEmpty()) {
            front = front + 1 == items.length ? 0 : front + 1;
        } else {
            back = front = 0;
        }
        if (items.length >= 16 && usageFactor() < 0.25) {
            resize(items.length / 2);
        }
        return first;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T last = items[back];
        items[back] = null;
        --size;
        if (!isEmpty()) {
            back = back - 1 < 0 ? items.length - 1 : back - 1;
        } else {
            back = front = 0;
        }
        if (items.length >= 16 && usageFactor() < 0.25) {
            resize(items.length / 2);
        }
        return last;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    @Override
    public T get(int index) {
        if (isEmpty() || index > size() || index < 0) {
            return null;
        }
        int position = (front + index) % items.length;
        return items[position];
    }

//    /** Returns true if deque is empty, false otherwise. */
//    @Override
//    public boolean isEmpty() {
//        return size() == 0;
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator. */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int p;

        public ArrayDequeIterator() {
            p = 0;
        }

        @Override
        public boolean hasNext() {
            return p < size;
        }

        @Override
        public T next() {
            return items[p++];
        }
    }

    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        if (this.size() != ((Deque<T>) o).size()) {
            return false;
        }
        for (int i = 0; i < size(); ++i) {
            T o1 = this.get(i);
            T o2 = other.get(i);
            if (!o1.equals(o2)) {
                return false;
            }
        }
        return true;
    }
}
