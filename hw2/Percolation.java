package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] matrixValues;
    private int openValues;
    private int N;
    private int topValue;
    private int baseValue;
    private WeightedQuickUnionUF unionfindlist;
    private WeightedQuickUnionUF noBackwash;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        this.openValues = 0;
        this.matrixValues = new boolean[N][N];
        this.N = N;
        this.topValue = N * N;
        this.baseValue = N * N + 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrixValues[i][j] = false;
            }
        }
        unionfindlist = new WeightedQuickUnionUF(N * N + 2);
        noBackwash = new WeightedQuickUnionUF(N * N + 1);

    }

    public void open(int row, int col) {
        if ((row < 0 || col < 0) || (col >= N || row >= N)) {
            throw new IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        } else {
            matrixValues[row][col] = true;
            this.openValues += 1;
            checkClosestOpenValues(row, col);
        }
        if (row == 0) {
            unionfindlist.union(topValue, xyTo1D(row, col));
            noBackwash.union(topValue, xyTo1D(row, col));
        }
        if (row == N - 1) {
            unionfindlist.union(baseValue, xyTo1D(row, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if ((row < 0 || col < 0) || (col >= N || row >= N)) {
            throw new IndexOutOfBoundsException();
        }
        return matrixValues[row][col];
    }

    public boolean isFull(int row, int col) {
        if ((row < 0 || col < 0) || (col >= N || row >= N)) {
            throw new IndexOutOfBoundsException();
        }
        return noBackwash.connected(this.topValue, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return this.openValues;
    }

    public boolean percolates() {
        return unionfindlist.connected(this.topValue, this.baseValue);
    }

    private void checkClosestOpenValues(int row, int col) {
        int currentLocation = xyTo1D(row, col);
        if (row + 1 < N && isOpen(row + 1, col)) {
            unionfindlist.union(currentLocation, xyTo1D(row + 1, col));
            noBackwash.union(currentLocation, xyTo1D(row + 1, col));
        }
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            unionfindlist.union(currentLocation, xyTo1D(row - 1, col));
            noBackwash.union(currentLocation, xyTo1D(row - 1, col));
        }
        if (col + 1 < N && isOpen(row, col + 1)) {
            unionfindlist.union(currentLocation, xyTo1D(row, col + 1));
            noBackwash.union(currentLocation, xyTo1D(row, col + 1));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            unionfindlist.union(currentLocation, xyTo1D(row, col - 1));
            noBackwash.union(currentLocation, xyTo1D(row, col - 1));
        }
    }

    private int xyTo1D(int row, int col) {
        return (row * N) + col;
    }

    public static void main(String[] args) {

    }
}
