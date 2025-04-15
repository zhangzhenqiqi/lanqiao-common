package com.lanqiao.test.generator;

/**
 * 测试数据生成器接口
 */
@FunctionalInterface
public interface Generator<T> {
    T generate();
}