package com.lanqiao.test.generator;

import java.util.Arrays;
import java.util.List;

public class GenericsUpperBoundedWildcardDemo {
    public static double sumOfList(List<? extends Number> list) {
        double s = 0.0;
        for (Number n : list) {
            s += n.doubleValue();
        }
        return s;
    }

    public static void main(String[] args) {
        List<Integer> li = Arrays.asList(1, 2, 3);
        List<Number> ln = (List<Number>) (List<?>) li;
        System.out.println("sum = " + sumOfList(li));
    }
}
// Output:
// sum = 6.0
