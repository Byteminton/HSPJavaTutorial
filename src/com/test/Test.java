package com.test;

import java.util.HashMap;

public class Test {
    static HashMap<Integer, Long> map = new HashMap<>();
    static long Fibonacci(int n) {
        // f(n) = f(n - 1) + f(n - 2)
        // 1, 1, 2, 3, 5, 8, 13, 21, ...
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 1;
        }
        return Fibonacci(n - 1) + Fibonacci(n - 2);
    }

    static long FibonacciPro(int n) {
        if (n == 1) {
            map.put(1, 1l);
            return 1l;
        }
        if (n == 2) {
            map.put(2, 1l);
            return 1l;
        }

        if (map.get(n) != null) {
            return map.get(n);
        }
        map.put(n, FibonacciPro(n - 1) + FibonacciPro(n - 2));
        return map.get(n);
    }
    public static void main(String[] args) {
        // f(n) = f(n - 1) + f(n - 2) 斐波那契数列
        // 1, 1, 2, 3, 5, 8, 13, 21, ...
        // f(50) = 12586269025 time: 24417
        long start = System.currentTimeMillis();
        System.out.println(FibonacciPro(50));
        long end = System.currentTimeMillis();
        System.out.println("程序运行时间:" + (end - start));

    }

}
