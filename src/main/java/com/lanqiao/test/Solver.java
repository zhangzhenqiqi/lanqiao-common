package com.lanqiao.test;

/**
     * 通用求解接口
     * 注意：这个接口定义在库中，但在实际使用时会与ACM代码中的接口形成对应关系
     */
    public interface Solver<I extends DataInput, O extends DataOutput> {
        O solve(I input);
    }
    
