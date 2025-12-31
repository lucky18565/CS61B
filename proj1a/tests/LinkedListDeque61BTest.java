import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

    @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
           assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void isEmptyAndSizeTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        lld1.addLast(0);   // [0]
        assertThat(lld1.isEmpty()).isFalse();
        assertThat(lld1.size()).isEqualTo(1);

        lld1.addLast(1);   // [0, 1]
        assertThat(lld1.size()).isEqualTo(2);

        lld1.addFirst(-1); // [-1, 0, 1]
        assertThat(lld1.size()).isEqualTo(3);

        lld1.addLast(2);   // [-1, 0, 1, 2]
        assertThat(lld1.size()).isEqualTo(4);

        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        assertThat(lld1.size()).isEqualTo(5);
    }

    @Test
    public void getTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        // 空队列
        assertThat(lld1.get(0)).isNull();
        assertThat(lld1.get(-1)).isNull();

        // 只有一个元素
        lld1.addLast(42);
        assertThat(lld1.get(0)).isEqualTo(42);
        assertThat(lld1.get(1)).isNull();         // index == size
        assertThat(lld1.get(28723)).isNull();     // 远超范围
        assertThat(lld1.get(-5)).isNull();        // 负索引

        // 两个元素后，再次检查边界
        lld1.addLast(99);
        assertThat(lld1.get(2)).isNull();         // index == size
        assertThat(lld1.get(1)).isEqualTo(99);
    }
    @Test
    public void getRecursiveTestBasic() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        // 空队列
        assertThat(lld1.getRecursive(0)).isNull();
        assertThat(lld1.getRecursive(-1)).isNull();

        // 只有一个元素
        lld1.addLast(42);
        assertThat(lld1.getRecursive(0)).isEqualTo(42);
        assertThat(lld1.getRecursive(1)).isNull();         // index == size
        assertThat(lld1.getRecursive(28723)).isNull();     // 远超范围
        assertThat(lld1.getRecursive(-5)).isNull();        // 负索引

        // 两个元素后，再次检查边界
        lld1.addLast(99);
        assertThat(lld1.getRecursive(2)).isNull();         // index == size
        assertThat(lld1.getRecursive(1)).isEqualTo(99);
    }

    @Test
    public void removeFirstAndRemoveLastEdgeCases() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        // 空队列移除
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();

        // 只加一个元素
        lld1.addLast(42);
        assertThat(lld1.removeFirst()).isEqualTo(42);
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // 再加一个元素
        lld1.addFirst(99);
        assertThat(lld1.removeLast()).isEqualTo(99);
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);

        // 多次移除到空
        lld1.addLast(1);
        lld1.addLast(2);
        lld1.addLast(3);
        lld1.removeFirst(); // 剩 [2,3]
        lld1.removeFirst(); // 剩 [3]
        lld1.removeFirst(); // 剩 []
        assertThat(lld1.isEmpty()).isTrue();
        assertThat(lld1.size()).isEqualTo(0);
        assertThat(lld1.removeFirst()).isNull();
        assertThat(lld1.removeLast()).isNull();
    }

    @Test
@DisplayName("综合功能统一测试")
public void integratedFunctionalityTest() {
    Deque61B<String> deque = new LinkedListDeque61B<>();

    // 初始状态
    assertThat(deque.isEmpty()).isTrue();
    assertThat(deque.size()).isEqualTo(0);

    // addFirst/addLast
    deque.addFirst("b"); // [b]
    deque.addLast("c");  // [b, c]
    deque.addFirst("a"); // [a, b, c]
    deque.addLast("d");  // [a, b, c, d]
    assertThat(deque.toList()).containsExactly("a", "b", "c", "d").inOrder();
    assertThat(deque.size()).isEqualTo(4);

    // get
    assertThat(deque.get(0)).isEqualTo("a");
    assertThat(deque.get(3)).isEqualTo("d");
    assertThat(deque.get(4)).isNull();
    assertThat(deque.get(-1)).isNull();

    // removeFirst/removeLast
    assertThat(deque.removeFirst()).isEqualTo("a"); // [b, c, d]
    assertThat(deque.removeLast()).isEqualTo("d");  // [b, c]
    assertThat(deque.toList()).containsExactly("b", "c").inOrder();
    assertThat(deque.size()).isEqualTo(2);

    // 再次添加和移除
    deque.addFirst("z"); // [z, b, c]
    assertThat(deque.removeLast()).isEqualTo("c");  // [z, b]
    assertThat(deque.removeFirst()).isEqualTo("z"); // [b]
    assertThat(deque.removeFirst()).isEqualTo("b"); // []
    assertThat(deque.isEmpty()).isTrue();
    assertThat(deque.size()).isEqualTo(0);

    // 空队列移除
    assertThat(deque.removeFirst()).isNull();
    assertThat(deque.removeLast()).isNull();
}
}