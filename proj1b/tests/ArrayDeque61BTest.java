import deque.ArrayDeque61B;

import deque.Deque61B;
import org.junit.jupiter.api.Test;


import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    /** In this test, we have three different assert statements that verify that addFirst works correctly. */
    public void addFirstTestBasic() {
        ArrayDeque61B<String> lld1 = new ArrayDeque61B<>();

        lld1.addFirst("back"); // after this call we expect: ["back"]
        assertThat(lld1.toList()).containsExactly("back").inOrder();
        assertThat(lld1.getFrontIndex()).isEqualTo(7);
        lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
        assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();
        assertThat(lld1.getFrontIndex()).isEqualTo(6);

        lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
        assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

             /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
                to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
                but not ["front", "middle", "back"].
              */
}
    @Test
    public void addLastTestBasic() {
        ArrayDeque61B<String> lld1 = new ArrayDeque61B<>();
        lld1.addLast("back");
        assertThat(lld1.toList()).containsExactly("back").inOrder();
        assertThat(lld1.getBackIndex()).isEqualTo(2);
        lld1.addLast("middle");
        assertThat(lld1.toList()).containsExactly("back", "middle").inOrder();
        assertThat(lld1.getBackIndex()).isEqualTo(3);
        lld1.addLast("front");
        assertThat(lld1.toList()).containsExactly("back", "middle", "front").inOrder();
        assertThat(lld1.getBackIndex()).isEqualTo(4);
    }

    @Test
    public void getTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();
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
    public void isEmptyAndSizeTestBasic() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

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
    public void removeFirstAndRemoveLastEdgeCases() {
        Deque61B<Integer> lld1 = new ArrayDeque61B<>();

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
    public void resizeAddTestBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();

        lld1.addLast(0);   // [0]
        lld1.addLast(1);   // [0, 1]
        lld1.addFirst(-1); // [-1, 0, 1]
        lld1.addLast(2);   // [-1, 0, 1, 2]
        lld1.addFirst(-2); // [-2, -1, 0, 1, 2]
        lld1.addLast(0);
        lld1.addLast(1);
        lld1.addFirst(-1);
        assertThat(lld1.getArrayLength()).isEqualTo(8);
        lld1.addLast(2);
        assertThat(lld1.size()).isEqualTo(9);
        assertThat(lld1.getArrayLength()).isEqualTo(16);
    }
    @Test
    public void resizeRemoveTestBasic() {
        ArrayDeque61B<Integer> lld1 = new ArrayDeque61B<>();

        for (int i=0; i<33; i++) {
            lld1.addLast(i);
        }
        assertThat(lld1.size()).isEqualTo(33);
        assertThat(lld1.getArrayLength()).isEqualTo(64);
        for (int i=0; i<18; i++) {
            lld1.removeLast();
        }
        assertThat(lld1.size()).isEqualTo(15);
        assertThat(lld1.getArrayLength()).isEqualTo(32);

        for (int i=0; i<12; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(lld1.getArrayLength()).isEqualTo(8);
        for (int i=0; i<5; i++) {
            lld1.addFirst(i);
        }
        assertThat(lld1.size()).isEqualTo(8);
        assertThat(lld1.getArrayLength()).isEqualTo(8);
        for (int i=0; i<5; i++) {
            lld1.removeFirst();
        }
        assertThat(lld1.size()).isEqualTo(3);
        assertThat(lld1.getArrayLength()).isEqualTo(8);
    }

    @Test
    public void edgeCasesTest() {
        ArrayDeque61B<Integer> d = new ArrayDeque61B<>();

        // 空队列反复 remove
        assertThat(d.removeFirst()).isNull();
        assertThat(d.removeLast()).isNull();
        assertThat(d.isEmpty()).isTrue();
        assertThat(d.size()).isEqualTo(0);

        // 清空后再添加
        for (int i = 0; i < 8; i++) d.addLast(i);
        for (int i = 0; i < 8; i++) d.removeFirst();
        assertThat(d.isEmpty()).isTrue();
        d.addLast(100);
        assertThat(d.size()).isEqualTo(1);
        assertThat(d.get(0)).isEqualTo(100);

        // wrap around：addFirst + removeLast
        for (int i = 0; i < 20; i++) d.addFirst(i);  // 队列头应为 19
        for (int i = 0; i < 18; i++) d.removeLast(); // 移除尾部 100,0..16
        assertThat(d.size()).isEqualTo(3);
        assertThat(d.toList()).containsExactly(19, 18, 17).inOrder();
    }

    @Test
    public void shrinkBoundaryTest() {
        ArrayDeque61B<Integer> d = new ArrayDeque61B<>();
        for (int i = 0; i < 64; i++) d.addLast(i);
        assertThat(d.getArrayLength()).isAtLeast(64);

        // 移除到 size=15，触发 64->32 缩容
        for (int i = 0; i < 49; i++) d.removeFirst();
        assertThat(d.size()).isEqualTo(15);
        assertThat(d.getArrayLength()).isEqualTo(32);

        // 再移除到 size=7，触发 32->16 缩容
        for (int i = 0; i < 8; i++) d.removeFirst();
        assertThat(d.size()).isEqualTo(7);
        assertThat(d.getArrayLength()).isEqualTo(16);
    }
}
