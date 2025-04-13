package com.lanqiao.common.io;
/**
 * @author zhenqi.zhang
 * @date 2023/10/4 23:19
 */
public enum FileType {
    In("in"), Out("out");
    String name;

    FileType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}