//package com.lanqiao.test;
//// ========================================================================================
//// 通用随机数据生成工具
//// ========================================================================================
//
//import java.util.*;
//
//public class RandomUtils {
//    public static final Random random = new Random();
//
//    public static Random getRandom() {
//        return random;
//    }
//
//    /**
//     * 生成指定范围内的随机整数
//     */
//    public static int nextInt(int min, int max) {
//        return min + random.nextInt(max - min + 1);
//    }
//
//    /**
//     * 生成指定范围内的随机长整数
//     */
//    public static long nextLong(long min, long max) {
//        return min + (long) (random.nextDouble() * (max - min + 1));
//    }
//
//    /**
//     * 生成指定范围内的随机浮点数
//     */
//    public static double nextDouble(double min, double max) {
//        return min + random.nextDouble() * (max - min);
//    }
//
//    /**
//     * 生成随机小写字母字符串
//     */
//    public static String nextLowerLetterString(int length) {
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append((char) ('a' + random.nextInt(26)));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成随机大写字母字符串
//     */
//    public static String nextUpperLetterString(int length) {
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append((char) ('A' + random.nextInt(26)));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成随机数字字符串
//     */
//    public static String nextDigitString(int length) {
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append((char) ('0' + random.nextInt(10)));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成随机字母数字字符串
//     */
//    public static String nextAlphanumericString(int length) {
//        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        StringBuilder sb = new StringBuilder(length);
//        for (int i = 0; i < length; i++) {
//            sb.append(chars.charAt(random.nextInt(chars.length())));
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 生成随机排列数组
//     */
//    public static int[] nextPermutation(int n) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < n; i++) {
//            list.add(i);
//        }
//        Collections.shuffle(list);
//
//        int[] result = new int[n];
//        for (int i = 0; i < n; i++) {
//            result[i] = list.get(i);
//        }
//        return result;
//    }
//
//    /**
//     * 生成随机数组
//     */
//    public static int[] nextIntArray(int size, int min, int max) {
//        int[] array = new int[size];
//        for (int i = 0; i < size; i++) {
//            array[i] = nextInt(min, max);
//        }
//        return array;
//    }
//
//    /**
//     * 生成随机排序数组
//     */
//    public static int[] nextSortedIntArray(int size, int min, int max, boolean ascending) {
//        int[] array = nextIntArray(size, min, max);
//        Arrays.sort(array);
//        if (!ascending) {
//            for (int i = 0; i < size / 2; i++) {
//                int temp = array[i];
//                array[i] = array[size - i - 1];
//                array[size - i - 1] = temp;
//            }
//        }
//        return array;
//    }
//}
