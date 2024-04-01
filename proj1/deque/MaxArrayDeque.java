package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    /** Comparator given by the constructor. */
    private Comparator<T> comp;

    /** Creates a MaxArrayDeque with the given Comparator. */
    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    /** returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null.*/
    public T max() {
        int maxIndex = 0;
        T[] items = getItems();
        for (int i = getFront(); i != getBack(); i = (i + 1) % items.length) {
            if (comp.compare(items[i], items[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return items[maxIndex];
    }

    /** returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null.*/
    public T max(Comparator<T> c) {
        int maxIndex = 0;
        T[] items = getItems();
        for (int i = 0; i < size(); ++i) {
            if (c.compare(items[i], items[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        return items[maxIndex];
    }
}
