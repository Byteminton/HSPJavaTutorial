package com.chapter28_dfs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 计算了有几种不同的走法
 */

public class HorseChessboardV2 {
    private static int m;
    private static int n;
    private static ChessBoard chessBoard;
    private static Stack stack;
    private static int step = 0;
    private static Coordinate[] direction = new Coordinate[8];
    private static String choice = "c"; // c-单步演示, a-全局演示
    private static int mode = 300;// count % mode == 0时，打印棋盘
    private static int count = 0;// 记录共有几种走法

    static {
        direction[0] = new Coordinate(-1, -2);
        direction[1] = new Coordinate(-2, -1);
        direction[2] = new Coordinate(-2, 1);
        direction[3] = new Coordinate(-1, 2);
        direction[4] = new Coordinate(1, 2);
        direction[5] = new Coordinate(2, 1);
        direction[6] = new Coordinate(2, -1);
        direction[7] = new Coordinate(1, -2);
    }

    public static void main(String[] args) {
        System.out.print("请输入棋盘长度m(m>=3):");
        m = Utility.readInt();
        System.out.print("请输入棋盘长度n(n>=3):");
        n = Utility.readInt();
        chessBoard = new ChessBoard(m, n);
        stack = new Stack(1000);
        long start = System.currentTimeMillis();
        for (int i = 0; i < (m + 1) / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
//        由于对称性，考虑1/4
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
                Coordinate source = new Coordinate(i, j);
                stack.push(source);
                DFS();
            }
        }
        long end = System.currentTimeMillis();
        if (count > 0) {
            System.out.println("共有" + count + "种走法,上面是其中的" + (count / mode) + "种走法");
            System.out.println("耗时:" + (end - start));
        } else {
            System.out.println(m + "*" + n + "的棋盘无解!");
        }
    }

    public static void DFS() {
        confirmPrint();
        while (!stack.isEmpty()) {
            if (step == m * n) {
                count++;
                if (count % mode == 0) {
                    chessBoard.print();
                }
            }
            Coordinate current = stack.peek();
            if (chessBoard.getState(current) == 0) {
                chessBoard.setExploring(current);
                chessBoard.setBoard(current, ++step);
                List<Coordinate> neighbors = getValidNeighbors(current);
                sortNeighbors(neighbors);
                for (Coordinate neighbor : neighbors) {
                    stack.push(neighbor);
                }
            } else {
                chessBoard.setLive(current);
                chessBoard.setBoard(current, 0);
                stack.pop();
                step--;
            }
            confirmPrint();
        }
    }

    public static boolean isValid(Coordinate target) {
        int row = target.getR();
        int col = target.getC();
        return row >= 0 && row <= m - 1 && col >= 0 && col <= n - 1;
    }

    public static List<Coordinate> getValidNeighbors(Coordinate current) {
        ArrayList<Coordinate> neighbors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Coordinate target = new Coordinate(
                    current.getR() + direction[i].getR(),
                    current.getC() + direction[i].getC());
            if (isValid(target) && chessBoard.getState(target) == 0) {
                neighbors.add(target);
            }
        }
        return neighbors;
    }

    public static void sortNeighbors(List<Coordinate> neighbors) {
        neighbors.sort(new Comparator<Coordinate>() {
            @Override
            public int compare(Coordinate c1, Coordinate c2) {
                return getValidNeighbors(c2).size() - getValidNeighbors(c1).size();
            }
        });
    }

    public static void confirmPrint() {
        if ("c".equals(choice)) {
            chessBoard.print();
            System.out.print("继续调试--c  全局演示--a:");
            choice = Utility.readString(1);
        }
    }
}
