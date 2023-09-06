package com.chapter28_dfs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HorseChessboardV1 {
    private static int m;
    private static int n;
    private static ChessBoard chessBoard;
    private static Stack stack;
    private static boolean isFind = false;
    private static int step = 0;
    private static List<Coordinate> direction = new ArrayList();
    private static String choice = "c"; // c-单步演示, a-全局演示

    static {
        direction.add(new Coordinate(-1, -2));
        direction.add(new Coordinate(-2, -1));
        direction.add(new Coordinate(-2, 1));
        direction.add(new Coordinate(-1, 2));
        direction.add(new Coordinate(1, 2));
        direction.add(new Coordinate(2, 1));
        direction.add(new Coordinate(2, -1));
        direction.add(new Coordinate(1, -2));
    }

    public static void main(String[] args) {
        System.out.print("请输入棋盘长度m(m>=3):");
        m = Utility.readInt();
        System.out.print("请输入棋盘宽度n(n>=3):");
        n = Utility.readInt();
        chessBoard = new ChessBoard(m, n);
        stack = new Stack(1000);

        long start = System.currentTimeMillis();
        for (int i = 0; i < (m + 1) / 2; i++) {
            for (int j = 0; j < (n + 1) / 2; j++) {
                Coordinate source = new Coordinate(i, j);
                stack.push(source);
                isFind = DFS();
                if (isFind) {
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        long end = System.currentTimeMillis();
        if (isFind) {
            System.out.println("成功!");
            chessBoard.print();
            System.out.println("耗时:" + (end - start));
        } else {
            System.out.println(m + "*" + n + "的棋盘无解!");
        }
    }

    public static boolean DFS() {
        confirmPrint();
        while (!stack.isEmpty()) {
            if (step == m * n) {
                return true;
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
        return false;
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
                    current.getR() + direction.get(i).getR(),
                    current.getC() + direction.get(i).getC());
            if (isValid(target) && chessBoard.getState(target) == 0) {
                neighbors.add(target);
            }
        }
        return neighbors;
    }
    // 优化: 每次在栈中放置邻居(目标)时，优先探索邻居较少的目标，可以避免一些特殊情况
    // 因为栈是先进后出，所以将邻居列表按各自的邻居多少降序排列，这样邻居多的先入栈,后搜索
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
            chessBoard.colorPrint(step);
            System.out.print("继续调试--c  全局演示--a:");
            choice = Utility.readString(1);
        }
    }
}
