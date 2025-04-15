package com.lanqiao.test.adapter;

import com.lanqiao.test.DataOutput;

public interface OutputAdapter<O> {
    DataOutput adapt(O output);
}