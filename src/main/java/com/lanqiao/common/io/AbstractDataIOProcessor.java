package com.lanqiao.common.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhenqi.zhang
 * @date 2023/12/31 22:00
 */
public abstract class AbstractDataIOProcessor {
	private String ROOT_PATH;

	public AbstractDataIOProcessor(String ROOT_PATH) {
		this.ROOT_PATH = ROOT_PATH;
	}

	public String getFilePath(FileType fileType, int index) {
		return ROOT_PATH + fileType + "_" + index + ".txt";
	}

	public String getInFilePath(int index) {
		return getFilePath(FileType.In, index);
	}

	public String getOutFilePath(int index) {
		return getFilePath(FileType.Out, index);
	}

	public DataIOHandler generateData(Object... param){
		return null;
	}

//	public void generateDataToFile(Object param, int i) throws IOException {
//		DataIOHandler data = generateData(param);
//		data.writeData(new PrintWriter(Files.newOutputStream(Paths.get(getInFilePath(i)))));
//	}

//	public abstract void checkData();
}
