package com.chapter28_dfs;

/**
 * board 记录每个位置对应的步数
 * state 记录当前位置是否访问过，0-unvisited，1-visited
 */
public class ChessBoard {
    private int[][] board;
    private int[][] state;

    public ChessBoard(int m, int n) {
        board = new int[m][n];
        state = new int[m][n];

    }

    public void print() {
        for (int[] rows : board) {
            for (int col : rows) {
                System.out.print(String.format("%02d", col) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void colorPrint(int step) {
        for (int[] rows : board) {
            for (int col : rows) {
                if (step != 0 && col == step) {
                    System.out.print("\u001B[31m" + String.format("%02d", col) + "\t" + "\u001B[0m");
                } else {
                    System.out.print(String.format("%02d", col) + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setBoard(Coordinate coordinate, int step) {
        board[coordinate.getR()][coordinate.getC()] = step;
    }

    public void setLive(Coordinate coordinate) {
        state[coordinate.getR()][coordinate.getC()] = 0;
    }

    public void setExploring(Coordinate coordinate) {
        state[coordinate.getR()][coordinate.getC()] = 1;
    }

    public int getState(Coordinate coordinate) {
        return state[coordinate.getR()][coordinate.getC()];
    }
}
