package deque;
import java.util.Comparator;

public class Maximizer61B {
    /**
     * Returns the maximum element from the given iterable of comparables.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @return          the maximum element
     */
    public static <T extends Comparable<T>> T max(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        T max = iterable.iterator().next();
        for (T item : iterable) {
            if (max.compareTo(item) < 0) {
                max = item;
            }
        }
        return max;
    }

    /**
     * Returns the maximum element from the given iterable according to the specified comparator.
     * You may assume that the iterable contains no nulls.
     *
     * @param iterable  the Iterable of T
     * @param comp      the Comparator to compare elements
     * @return          the maximum element according to the comparator
     */
    public static <T> T max(Iterable<T> iterable, Comparator<T> comp) {
        if (iterable == null) {
            return null;
        }
        T max = iterable.iterator().next();
        for (T item : iterable) {
            if (comp.compare(max, item) < 0) {
                max = item;
            }
        }
        return max;
    }
}
