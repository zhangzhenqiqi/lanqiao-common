package com.lanqiao.test.processor;

import com.lanqiao.test.DataInput;
import com.lanqiao.test.DataOutput;

/**
 * 测试数据后置处理器接口
 * 用于验证生成的输入数据及其求解结果是否满足特定条件
 */
public interface TestPostProcessor<I extends DataInput, O extends DataOutput> {
    /**
     * 检查生成的输入和对应的输出是否满足要求
     *
     * @param input  生成的输入数据
     * @param output 通过求解器得到的输出数据
     * @return 如果满足要求返回true，否则返回false
     */
     boolean validate(I input, O output);
}