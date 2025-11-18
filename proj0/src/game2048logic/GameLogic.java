package game2048logic;

import game2048rendering.Side;

import static game2048logic.MatrixUtils.rotateLeft;
import static game2048logic.MatrixUtils.rotateRight;

/**
 * @author  Josh Hug
 */
public class GameLogic {
    /** Moves the given tile up as far as possible, subject to the minR constraint.
     *
     * @param board the current state of the board
     * @param r     the row number of the tile to move up
     * @param c -   the column number of the tile to move up
     * @param minR  the minimum row number that the tile can land in, e.g.
     *              if minR is 2, the moving tile should move no higher than row 2.
     * @return      if there is a merge, returns the 1 + the row number where the merge occurred.
     *              if no merge occurs, then return 0.
     */
    public static int moveTileUpAsFarAsPossible(int[][] board, int r, int c, int minR) {
        int initialValue = board[r][c];
        int topr = r;
        if (initialValue == 0 || r == minR) {
            return 0;
        }
        while (topr > 0 && topr > minR) {
            if (board[topr - 1][c] == 0) {
                topr--;
            } else if (board[topr - 1][c] == initialValue) {
                board[topr - 1][c] = initialValue * 2;
                board[r][c] = 0;
                return topr;
            } else if (board[topr - 1][c] > 0 && board[topr - 1][c] !=  initialValue) {
                break;
            }
        }
        if (topr == r) {
            return 0;
        }
        board[topr][c] = initialValue;
        board[r][c] = 0;
        return  0;
    }

    /**
     * Modifies the board to simulate the process of tilting column c
     * upwards.
     *
     * @param board     the current state of the board
     * @param c         the column to tilt up.
     */
    public static void tiltColumn(int[][] board, int c) {
        int i = 0;
        int currentMinR = 0;
        while (i < board.length) {
            if (currentMinR != 0) {
                moveTileUpAsFarAsPossible(board, i, c, currentMinR);
            } else {
                currentMinR = moveTileUpAsFarAsPossible(board, i, c, 0);
            }
            i++;
        }
    }

    /**
     * Modifies the board to simulate tilting all columns upwards.
     *
     * @param board     the current state of the board.
     */
    public static void tiltUp(int[][] board) {
        for (int c = 0; c < board.length; c++) {
            tiltColumn(board, c);
        }
    }

    /**
     * Modifies the board to simulate tilting the entire board to
     * the given side.
     *
     * @param board the current state of the board
     * @param side  the direction to tilt
     */
    public static void tilt(int[][] board, Side side) {
        if (side == Side.EAST) {
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
        } else if (side == Side.WEST) {
            rotateRight(board);
            tiltUp(board);
            rotateLeft(board);
        } else if (side == Side.SOUTH) {
            rotateLeft(board);
            rotateLeft(board);
            tiltUp(board);
            rotateRight(board);
            rotateRight(board);
        } else {
            tiltUp(board);
        }
    }
}
