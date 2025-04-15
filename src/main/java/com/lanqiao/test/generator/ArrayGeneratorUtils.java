package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;

import java.util.List;
import java.util.function.Function;

/**
 * @Author zzq
 * @Date 2025/4/14 23:36
 */
public class ArrayGeneratorUtils {

    /**
     * 创建随机整数数组生成器
     */
    public static Generator<int[]> intArrayGenerator(int size, int minValue, int maxValue) {
        return () -> LocalRandomUtils.generateArray(size, minValue, maxValue);
    }

    /**
     * 创建随机整数数组生成器
     */
    public static Generator<int[]> intArrayGenerator(int sizeL, int sizeR, int minValue, int maxValue) {
        int size = LocalRandomUtils.getLocalRandom().nextInt(sizeL, sizeR);
        return () -> LocalRandomUtils.generateArray(size, minValue, maxValue);
    }

    /**
     * 创建随机Number List生成器
     */
    public static <T extends Number> Generator<List<T>> numberListGenerator(
            Class<T> clazz, int size, T minValue, T maxValue) {

        return () -> {
            List<T> list = LocalRandomUtils.generateRandomNumberList(clazz, size, minValue, maxValue);
            return list;
        };
    }


}
