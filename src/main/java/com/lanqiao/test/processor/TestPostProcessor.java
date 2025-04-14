package com.lanqiao.test.processor;

import com.lanqiao.test.DataInput;
import com.lanqiao.test.DataOutput;

/**
 * 测试数据后置处理器接口
 */
public interface TestPostProcessor<I, O> {
    /**
     * 检查生成的输入和对应的输出是否满足要求
     *
     * @param input  生成的输入数据
     * @param output 通过求解器得到的输出数据
     * @return 如果满足要求返回true，否则返回false
     */
    boolean validate(DataInput<? extends I> input, DataOutput<? extends O> output);
}