package com.lanqiao.test.adapter;

import com.lanqiao.test.DataInput;
import com.lanqiao.test.DataOutput;
import com.lanqiao.test.Solver;


/**
 * 泛型适配器类 目的是将ACM工程中自定义的输入输出类 转化为本工程内部的输入输出类
 * <p>
 * 这个类在测试工具项目中实现，不在ACM代码中使用
 *
 * @param <I> ACM输入类型
 * @param <O> ACM输出类型
 */
public class GenericSolverAdapter<I, O> implements Solver<DataInput, DataOutput> {
    private final Object solver; // 实际的ACM求解器
    //将输入转为此类型 eg.AlgorithmInput.class
    private final Class<I> inputClass;
    //将输出转为此类型 eg.AlgorithmOutput.class
    private final Class<O> outputClass;
    // 输出类型适配器
    private final OutputAdapter<O> outputAdapter;

    // 反射方法名称
    private final String solveMethodName;

    public GenericSolverAdapter(Object solver, Class<I> inputClass, Class<O> outputClass
            , String solveMethodName, OutputAdapter<O> outputAdapter) {
        this.solver = solver;
        this.inputClass = inputClass;
        this.outputClass = outputClass;
        this.solveMethodName = solveMethodName;
        this.outputAdapter = outputAdapter;

    }

    @Override
    public DataOutput solve(DataInput input) {
        try {
            // 将DataInput转换为实际的ACM输入类型
            I acmInput = inputClass.cast(input);
            // 使用反射调用solve方法
            java.lang.reflect.Method solveMethod = solver.getClass().getMethod(solveMethodName, inputClass);
            // 转为实际的ACM输出类型
            O acmOutput = outputClass.cast(solveMethod.invoke(solver, acmInput));
            // 将ACM输出转换为DataOutput类型
            return this.outputAdapter.adapt(acmOutput); // 使用适配器转换输出
        } catch (Exception e) {
            throw new RuntimeException("调用求解方法失败", e);
        }
    }
}