//package com.lanqiao.test.generator;
//
//import com.lanqiao.test.DataInput;
//import com.lanqiao.test.RandomUtils;
//
//import java.util.function.Function;
//
///**
//     * 矩阵测试数据生成器工厂
//     */
//    public  class MatrixGenerators {
//        /**
//         * 创建随机矩阵生成器
//         */
//        public static <T extends DataInput> Generator<T> createRandomMatrixGenerator(
//                int minRows, int maxRows, int minCols, int maxCols, int minValue, int maxValue,
//                Function<int[][], T> inputFactory) {
//
//            return () -> {
//                int rows = RandomUtils.nextInt(minRows, maxRows);
//                int cols = RandomUtils.nextInt(minCols, maxCols);
//
//                int[][] matrix = new int[rows][cols];
//
//                for (int i = 0; i < rows; i++) {
//                    for (int j = 0; j < cols; j++) {
//                        matrix[i][j] = RandomUtils.nextInt(minValue, maxValue);
//                    }
//                }
//
//                return inputFactory.apply(matrix);
//            };
//        }
//
//        /**
//         * 创建对称矩阵生成器
//         */
//        public static <T extends DataInput> Generator<T> createSymmetricMatrixGenerator(
//                int minSize, int maxSize, int minValue, int maxValue,
//                Function<int[][], T> inputFactory) {
//
//            return () -> {
//                int size = RandomUtils.nextInt(minSize, maxSize);
//
//                int[][] matrix = new int[size][size];
//
//                for (int i = 0; i < size; i++) {
//                    for (int j = i; j < size; j++) {
//                        int value = RandomUtils.nextInt(minValue, maxValue);
//                        matrix[i][j] = value;
//                        matrix[j][i] = value;
//                    }
//                }
//
//                return inputFactory.apply(matrix);
//            };
//        }
//    }