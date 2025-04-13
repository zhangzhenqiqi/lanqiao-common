package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;

import java.util.Random;

public class NumberGeneratorUtils {
    // 随机数生成器
    private static final Random random = LocalRandomUtils.getLocalRandom();

    // 整数生成器
    public static Generator<Integer> intGenerator() {
        return random::nextInt;
    }

    public static Generator<Integer> intGenerator(int min, int max) {
        return () -> min + random.nextInt(max - min + 1);
    }

    // 长整型生成器
    public static Generator<Long> longGenerator() {
        return random::nextLong;
    }

    public static Generator<Long> longGenerator(long min, long max) {
        return () -> min + (long) (random.nextDouble() * (max - min + 1));
    }

    // 浮点型生成器
    public static Generator<Float> floatGenerator() {
        return random::nextFloat;
    }

    public static Generator<Float> floatGenerator(float min, float max) {
        return () -> min + random.nextFloat() * (max - min);
    }

    // 双精度浮点型生成器
    public static Generator<Double> doubleGenerator() {
        return random::nextDouble;
    }

    public static Generator<Double> doubleGenerator(double min, double max) {
        return () -> min + random.nextDouble() * (max - min);
    }
}