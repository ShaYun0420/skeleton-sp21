package deque;

import java.util.Iterator;
import java.util.Objects;

public class LinkedListDeque<T> {
    /** helper class: ListNode of LinkedListDeque. */
    private class ListNode {
        public T item;
        public ListNode next;
        public ListNode pre;

        /** Constructor of ListNode. */
        public ListNode(T item, ListNode pre, ListNode next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

    private ListNode sentinel;
    private int size;

    /** Constructor: create empty list. */
    public LinkedListDeque() {
        sentinel = new ListNode(null, null, null);
        sentinel.pre = sentinel.next = sentinel;
        size = 0;
    }

    /**  Adds an item of type T to the front of the deque.
     * You can assume that item is never null. */
    public void addFirst(T item) {
        if (isEmpty()) {
            sentinel.pre = sentinel.next = new ListNode(item, sentinel, sentinel);
        } else {
            ListNode oldFirst = sentinel.next;
            oldFirst.pre =  sentinel.next = new ListNode(item, sentinel, oldFirst);
        }
        size += 1;
    }

    /** Adds an item of type T to the back of the deque.
     * You can assume that item is never null. */
    public void addLast(T item) {
        if (isEmpty()) {
            sentinel.pre = sentinel.next = new ListNode(item, sentinel, sentinel);
        } else {
            ListNode oldLast = sentinel.pre;
            oldLast.next = sentinel.pre = new ListNode(item, oldLast, sentinel);
        }
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size() == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        ListNode cur = sentinel.next;
        while (cur != sentinel) {
            if (cur.next == sentinel)
                System.out.println(cur.item);
            else
                System.out.print(cur.item + " ");
            cur = cur.next;
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null. */
    public T removeFirst() {
        if (isEmpty())
            return null;
        ListNode oldFirst = sentinel.next;
        if (oldFirst == null)
            return null;
        ListNode newFirst = oldFirst.next;
        sentinel.next = newFirst;
        if (newFirst != null)
            newFirst.pre = sentinel;
        --size;
        return oldFirst.item;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public T removeLast() {
        if (isEmpty())
            return null;
        ListNode oldLast = sentinel.pre;
        if (oldLast == null)
            return null;
        ListNode newLast = oldLast.pre;
        sentinel.pre = newLast;
        newLast.next = sentinel;
        --size;
        return oldLast.item;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque! */
    public T get(int index) {
        if (isEmpty())
            return null;
        ListNode p = sentinel.next;
        for (int i = 0; i < index; i += 1) {
            p = p.next;
        }
        return p.item;
    }

    /** Helper method of getRecursive
     * Get item at given index, begin with the node. */
    private T helper(ListNode node, int index) {
        if (index == 0)
            return node.item;
        return helper(node.next, index - 1);
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        if (isEmpty() || index >= size() || index < 0)
            return null;
        return helper(sentinel.next, index);
    }

    /** The Deque objects we’ll make are iterable (i.e. Iterable<T>)
     * so we must provide this method to return an iterator. */
//    public Iterator<T> iterator() {
//
//    }

    /** Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents
     * (as goverened by the generic T’s equals method) in the same order.
     * (ADDED 2/12: You’ll need to use the instance of keywords for this. */
    public boolean equals(Object o) {
        if (!(o instanceof LinkedListDeque))
            return false;
        LinkedListDeque<?> other = (LinkedListDeque<?>) o;
        if (this.size() != other.size()) {
            return false;
        }
        ListNode p1 = this.sentinel.next;
        LinkedListDeque<?>.ListNode p2 = other.sentinel.next;
        while (p1 != this.sentinel && p2 != other.sentinel) {
            if (p1.item != p2.item)
                return false;
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }
}
