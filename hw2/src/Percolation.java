import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Percolation {
    // TODO: Add any necessary instance variables.
    public boolean[][] grid;
    public WeightedQuickUnionUF uf;
    public WeightedQuickUnionUF ufFull;
    public int numOpen;
    public int top;
    public int bottom;


    public Percolation(int N) {
        // TODO: Fill in this constructor.
        if (N <= 0) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N*N + 2);
        ufFull = new WeightedQuickUnionUF(N*N + 1);
        top = grid.length* grid.length;
        bottom = grid.length* grid.length + 1;
    }

    public int xyTo1D(int r, int c) {
        return r * grid.length + c;
    }

    public void open(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (grid[row][col]) {
            return;
        }
        numOpen++;
        grid[row][col] = true;
        if (row == 0) {
            uf.union(top,xyTo1D(row, col) );
            ufFull.union(top,xyTo1D(row, col));
        }
        if (row == grid.length - 1) {
            uf.union(bottom,xyTo1D(row, col) );
        }
        if (row < grid.length - 1 && isOpen(row + 1, col)) {
            uf.union(xyTo1D(row + 1, col), xyTo1D(row, col));
            ufFull.union(xyTo1D(row + 1, col), xyTo1D(row, col));
        }
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            ufFull.union(xyTo1D(row - 1, col), xyTo1D(row, col));
        }
        if (col < grid.length - 1 && isOpen(row, col + 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            ufFull.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            ufFull.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // TODO: Fill in this method.
        if (row < 0 || row >= grid.length || col < 0 || col >= grid.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return ufFull.connected(top, xyTo1D(row, col));
        }
        return false;
    }

    public int numberOfOpenSites() {
        // TODO: Fill in this method.
        return numOpen;
    }

    public boolean percolates() {
        // TODO: Fill in this method.
        return uf.connected(top, bottom);
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

}
