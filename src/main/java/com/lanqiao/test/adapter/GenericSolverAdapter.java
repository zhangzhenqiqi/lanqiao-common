package com.lanqiao.test.adapter;

import com.lanqiao.test.DataInput;
import com.lanqiao.test.DataOutput;
import com.lanqiao.test.Solver;

import java.util.function.Function;


/**
 * 泛型适配器类 目的是将ACM工程中自定义的输入输出类 转化为本工程内部的输入输出类
 * <p>
 * 这个类在测试工具项目中实现，不在ACM代码中使用
 *
 * @param <I> ACM输入类型
 * @param <O> ACM输出类型
 */
public class GenericSolverAdapter<I, O>
        implements Solver<I, O, DataInput<? extends I>, DataOutput<? extends O>> {
    // 求解方法名称
    Function<I, O> solver;
    //将输入转为此类型 eg.AlgorithmInput.class
    private final Class<I> inputClass;
    //将输出转为此类型 eg.AlgorithmOutput.class
    private final Class<O> outputClass;
    // 输出类型适配器
    private final OutputAdapter<O> outputAdapter;


    // 反射方法名称

    public GenericSolverAdapter(Function<I, O> solver, Class<I> inputClass, Class<O> outputClass
            , OutputAdapter<O> outputAdapter) {
        this.solver = solver;
        this.inputClass = inputClass;
        this.outputClass = outputClass;
        this.outputAdapter = outputAdapter;
    }

    @Override
    public DataOutput solve(DataInput input) {
        try {
            // 将DataInput转换为实际的ACM输入类型
            I acmInput = inputClass.cast(input);
            O acmOutput = solver.apply(acmInput);
            // 将ACM输出转换为DataOutput类型
            // 使用适配器转换输出
            return this.outputAdapter.adapt(acmOutput);
        } catch (Exception e) {
            throw new RuntimeException("调用求解方法失败", e);
        }
    }
}