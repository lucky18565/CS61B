import deque.ArrayDeque61B;
import deque.Deque61B;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BEqualsTest {

    private static class LinkedListDequeWithoutToList<T> extends LinkedListDeque61B<T> {
        @Override
        public List<T> toList() {
            throw new UnsupportedOperationException("toList should not be called by toString");
        }
    }

    private static class ArrayDequeWithoutToList<T> extends ArrayDeque61B<T> {
        @Override
        public List<T> toList() {
            throw new UnsupportedOperationException("toList should not be called by toString");
        }
    }

    @Test
    public void equalsSameReferenceIsTrue() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        a.addLast(1);
        a.addLast(2);

        assertThat(a.equals(a)).isTrue();
    }

    @Test
    public void equalsDifferentImplementationsSameContentIsTrue() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        Deque61B<Integer> b = new ArrayDeque61B<>();

        a.addLast(1);
        a.addLast(2);
        a.addLast(3);

        b.addLast(1);
        b.addLast(2);
        b.addLast(3);

        assertThat(a.equals(b)).isTrue();
        assertThat(b.equals(a)).isTrue();
    }

    @Test
    public void equalsDifferentOrderIsFalse() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        Deque61B<Integer> b = new ArrayDeque61B<>();

        a.addLast(1);
        a.addLast(2);
        a.addLast(3);

        b.addLast(1);
        b.addLast(3);
        b.addLast(2);

        assertThat(a.equals(b)).isFalse();
    }

    @Test
    public void equalsDifferentSizeIsFalse() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        Deque61B<Integer> b = new ArrayDeque61B<>();

        a.addLast(1);
        a.addLast(2);

        b.addLast(1);

        assertThat(a.equals(b)).isFalse();
    }

    @Test
    public void equalsNullAndNonDequeAreFalse() {
        LinkedListDeque61B<Integer> a = new LinkedListDeque61B<>();
        a.addLast(1);

        assertThat(a.equals(null)).isFalse();
        assertThat(a.equals("not a deque")).isFalse();
    }

    @Test
    public void iteratorLinkedListDequeForeachOrder() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void iteratorArrayDequeForeachOrder() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d).containsExactly("front", "middle", "back").inOrder();
    }

    @Test
    public void iteratorArrayDequeWrapAroundStillInOrder() {
        Deque61B<Integer> d = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            d.addLast(i);
        }
        for (int i = 0; i < 3; i++) {
            d.removeFirst();
        }
        d.addLast(8);
        d.addLast(9);

        assertThat(d).containsExactly(3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    public void toStringLinkedListDequePrintsBracketedList() {
        Deque61B<String> d = new LinkedListDeque61B<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void toStringArrayDequePrintsBracketedList() {
        Deque61B<String> d = new ArrayDeque61B<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void toStringLinkedListDequeDoesNotUseToList() {
        Deque61B<String> d = new LinkedListDequeWithoutToList<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d.toString()).isEqualTo("[front, middle, back]");
    }

    @Test
    public void toStringArrayDequeDoesNotUseToList() {
        Deque61B<String> d = new ArrayDequeWithoutToList<>();
        d.addLast("front");
        d.addLast("middle");
        d.addLast("back");

        assertThat(d.toString()).isEqualTo("[front, middle, back]");
    }
}
