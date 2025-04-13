package com.lanqiao.test;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * 通用数据输入接口
 * 注意：这个接口定义在库中，但在实际使用时会与ACM代码中的接口形成对应关系
 */
public interface DataInput {
    <T> T getData();

    DataInput readData(Scanner scanner);

    void writeData(PrintStream out);
}