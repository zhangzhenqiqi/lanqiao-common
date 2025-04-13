package com.lanqiao.test.generator;

import org.junit.Assert;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author zhenqi.zhang
 * @date 2024/9/15 18:16
 */
public class DataUtils {
	@FunctionalInterface
	public static interface AlgorithmInputGenerator {
		AlgorithmInput generate();
	}


	public static String getInFilePath(String rootPath, int i) {
		return rootPath + "/" + i + ".in";
	}

	public static String getOutFilePath(String rootPath, int i) {
		return rootPath + "/" + i + ".out";
	}

	/**
	 * 生成数据到文件
	 *
	 * @param path         文件路径
	 * @param index        文件序号
	 * @param generator    输入数据生成器
	 * @param solveAbility solve方法，根据输入得到输出
	 * @throws FileNotFoundException
	 */
	public static void generateDataToFile(
			String path,
			int index,
			AlgorithmInputGenerator generator,
			SolveAbility solveAbility
	) throws FileNotFoundException {
		String inFilePath = getInFilePath(path, index);
		PrintStream inWriter = new PrintStream(inFilePath);

		String outFilePath = getOutFilePath(path, index);
		PrintStream outWriter = new PrintStream(outFilePath);

		AlgorithmInput input = generator.generate();
		AlgorithmOutput output = solveAbility.solve(input);

		input.write(inWriter);
		output.write(outWriter);

		inWriter.close();
		outWriter.close();
	}

	public static void checkAllCases(String path,
	                                 SolveAbility solveAbility) throws IOException {
		for (int i = 0; i < 20; i++) {
			String inFilePath = getInFilePath(path, i);
			String outFilePath = getOutFilePath(path, i);
			AlgorithmInput input = new AlgorithmInputData().read(new Scanner(Files.newInputStream(Paths.get(inFilePath))));
			AlgorithmOutput output = new AlgorithmOutputData().read(new Scanner(Files.newInputStream(Paths.get(outFilePath))));
			AlgorithmOutput result = solveAbility.solve(input);
			Assert.assertEquals(output, result);
			System.out.println("case " + i + " passed");
			System.out.println("\tinput=" + input);
			System.out.println("\toutput=" + output);
			System.out.println("\tresult=" + result);
		}
	}
}
/*
1blqbq
 */

