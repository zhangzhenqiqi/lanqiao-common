//package com.lanqiao.test.generator;
//
//import com.lanqiao.test.DataInput;
//import com.lanqiao.test.RandomUtils;
//
//import java.util.function.Function;
//
///**
// * 数组测试数据生成器工厂
// * inputFactory 用于生产数据后回调方法，进行后置赋值操作
// */
//public class ArrayGenerators {
//    /**
//     * 创建随机整数数组生成器
//     */
//    public static <T extends DataInput> Generator<T> createRandomIntArrayGenerator(
//            int minSize, int maxSize, int minValue, int maxValue,
//            Function<int[], T> inputFactory) {
//
//        return () -> {
//            int size = RandomUtils.nextInt(minSize, maxSize);
//            int[] array = RandomUtils.nextIntArray(size, minValue, maxValue);
//            return inputFactory.apply(array);
//        };
//    }
//
//    /**
//     * 创建排序数组生成器
//     */
//    public static <T extends DataInput> Generator<T> createSortedArrayGenerator(
//            int minSize, int maxSize, int minValue, int maxValue, boolean ascending,
//            Function<int[], T> inputFactory) {
//
//        return () -> {
//            int size = RandomUtils.nextInt(minSize, maxSize);
//            int[] array = RandomUtils.nextSortedIntArray(size, minValue, maxValue, ascending);
//            return inputFactory.apply(array);
//        };
//    }
//
//    /**
//     * 创建随机排列生成器
//     */
//    public static <T extends DataInput> Generator<T> createPermutationGenerator(
//            int minSize, int maxSize,
//            Function<int[], T> inputFactory) {
//
//        return () -> {
//            int size = RandomUtils.nextInt(minSize, maxSize);
//            int[] array = RandomUtils.nextPermutation(size);
//
//            // 有时候需要从1开始的排列
//            for (int i = 0; i < size; i++) {
//                array[i] += 1;
//            }
//
//            return inputFactory.apply(array);
//        };
//    }
//}
