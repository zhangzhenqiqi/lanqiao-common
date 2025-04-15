package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;

import java.util.Random;

public class NumberGeneratorUtils {
    // 随机数生成器
    private static final Random random = LocalRandomUtils.getLocalRandom();

    public static <T extends Number> Generator<T> numberGenerator(Class<T> clazz, T left, T right) {
        return () -> LocalRandomUtils.generateRandomNumber(clazz, left, right);
    }

    // 整数生成器
    public static Generator<Integer> intGenerator() {
        return random::nextInt;
    }

    public static Generator<Integer> intGenerator(int min, int max) {
        return numberGenerator(Integer.class, min, max);
    }

    // 长整型生成器
    public static Generator<Long> longGenerator() {
        return random::nextLong;
    }

    public static Generator<Long> longGenerator(long min, long max) {
        return numberGenerator(Long.class, min, max);
    }

    // 浮点型生成器
    public static Generator<Float> floatGenerator() {
        return random::nextFloat;
    }

    public static Generator<Float> floatGenerator(float min, float max) {
        return numberGenerator(Float.class, min, max);
    }

    // 双精度浮点型生成器
    public static Generator<Double> doubleGenerator() {
        return random::nextDouble;
    }

    public static Generator<Double> doubleGenerator(double min, double max) {
        return numberGenerator(Double.class, min, max);
    }
}