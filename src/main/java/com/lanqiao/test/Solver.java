package com.lanqiao.test;

/**
 * 通用求解接口
 */
public interface Solver<I, O, DI extends DataInput<? extends I>, DO extends DataOutput<? extends O>> {
    DO solve(DI input);
}