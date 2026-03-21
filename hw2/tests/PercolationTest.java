import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    // TODO: Using the given tests above as a template,
    //       write some more tests and delete the fail() line
    @Test
    public void yourFirstTestHere() {
                // 极限测试1：大网格全部打开
                int bigN = 100;
                Percolation bigP = new Percolation(bigN);
                for (int r = 0; r < bigN; r++) {
                    for (int c = 0; c < bigN; c++) {
                        bigP.open(r, c);
                    }
                }
                assertThat(bigP.percolates()).isTrue();
                assertThat(bigP.numberOfOpenSites()).isEqualTo(bigN * bigN);

                // 极限测试2：重复open同一格子
                Percolation p2 = new Percolation(3);
                p2.open(1, 1);
                p2.open(1, 1);
                p2.open(1, 1);
                assertThat(p2.numberOfOpenSites()).isEqualTo(1);

                // 极限测试3：只开一条竖直通路
                Percolation p3 = new Percolation(5);
                for (int i = 0; i < 5; i++) {
                    p3.open(i, 2);
                }
                assertThat(p3.percolates()).isTrue();
                for (int i = 0; i < 5; i++) {
                    assertThat(p3.isFull(i, 2)).isTrue();
                }

                // 极限测试4：只开角落
                Percolation p4 = new Percolation(3);
                p4.open(0, 0);
                p4.open(0, 2);
                p4.open(2, 0);
                p4.open(2, 2);
                assertThat(p4.percolates()).isFalse();

                // 极限测试5：回流检测（修正版）
                Percolation p5 = new Percolation(3);
                // 打开顶行和底行
                for (int c = 0; c < 3; c++) {
                    p5.open(0, c);
                    p5.open(2, c);
                }
                // 打开中间一列
                p5.open(1, 1);
                // 按照题目定义，(2,0) 和 (2,1) 连通，(2,1) 又和顶行连通，所以 (2,0) 也应为 full
                assertThat(p5.isFull(2, 0)).isTrue();
                assertThat(p5.isFull(2, 1)).isTrue();
                assertThat(p5.isFull(2, 2)).isTrue();
                // 额外测试：只打开 (2,2)，不连通顶行，应为 false
                Percolation p6 = new Percolation(3);
                p6.open(2, 2);
                assertThat(p6.isFull(2, 2)).isFalse();

                // 极限测试6：构造函数非法参数
                try {
                    new Percolation(0);
                    fail("Should throw exception for N=0");
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    // pass
                }
                try {
                    new Percolation(-5);
                    fail("Should throw exception for N<0");
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    // pass
                }
        // 边界测试：测试左上角、右下角、边界越界等
        int N = 3;
        Percolation p = new Percolation(N);
        // 打开左上角
        p.open(0, 0);
        assertThat(p.isOpen(0, 0)).isTrue();
        assertThat(p.isFull(0, 0)).isTrue();
        // 打开右下角
        p.open(2, 2);
        assertThat(p.isOpen(2, 2)).isTrue();
        assertThat(p.isFull(2, 2)).isFalse();
        // 打开一条通路
        p.open(1, 0);
        p.open(2, 0);
        assertThat(p.isFull(2, 0)).isTrue();
        // 测试越界
        try {
            p.open(-1, 0);
            fail("Should throw exception for negative row");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        try {
            p.isOpen(0, 3);
            fail("Should throw exception for col >= N");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        try {
            p.isFull(3, 0);
            fail("Should throw exception for row >= N");
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
        // 1x1边界
        Percolation p1 = new Percolation(1);
        assertThat(p1.percolates()).isFalse();
        p1.open(0, 0);
        assertThat(p1.percolates()).isTrue();
    }

}
