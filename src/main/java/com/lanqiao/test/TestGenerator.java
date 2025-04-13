package com.lanqiao.test;

import com.lanqiao.test.generator.Generator;
import com.lanqiao.test.processor.TestPostProcessor;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;

/**
 * ACM测试数据生成库
 * 设计为可与ACM代码结构兼容的通用工具
 */
public class TestGenerator {
    private static final Random random = new Random();


    /**
     * 测试范围定义
     */
    public static class TestRange<T extends DataInput> {
        private final int count;
        private final Generator<? extends T> generator;
        private final String description;

        public TestRange(int count, Generator<? extends T> generator, String description) {
            this.count = count;
            this.generator = generator;
            this.description = description;
        }

        public int getCount() {
            return count;
        }

        public Generator<? extends T> getGenerator() {
            return generator;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * 测试套件构建器
     */
    public static class TestSuiteBuilder<I extends DataInput, O extends DataOutput> {
        private final String basePath;
        private final Solver<I, O> solver;
        private final List<TestRange<I>> ranges = new ArrayList<>();
        private final Supplier<I> inputFactory;
        private final Supplier<O> outputFactory;
        // 现有属性
        private TestPostProcessor<I, O> postProcessor;

        private int startIndex = 0;
        private int maxAttempts = Integer.MAX_VALUE;  // 默认最大尝试次数


        public TestSuiteBuilder(String basePath,
                                Solver<I, O> solver,
                                Supplier<I> inputFactory,
                                Supplier<O> outputFactory) {
            this.basePath = basePath;
            this.solver = solver;
            this.inputFactory = inputFactory;
            this.outputFactory = outputFactory;
        }

        /**
         * 设置后置处理器
         */
        public TestSuiteBuilder<I, O> setPostProcessor(TestPostProcessor<I, O> postProcessor) {
            this.postProcessor = postProcessor;
            return this;
        }

        /**
         * 添加测试范围
         */
        public TestSuiteBuilder<I, O> addRange(int count, Generator<? extends I> generator, String description) {
            ranges.add(new TestRange<>(count, generator, description));
            return this;
        }

        /**
         * 设置起始索引
         */
        public TestSuiteBuilder<I, O> setStartIndex(int startIndex) {
            this.startIndex = startIndex;
            return this;
        }

        /**
         * 生成所有测试用例
         */
        public void generateAllTestData() throws FileNotFoundException {
            int currentIndex = startIndex;

            for (TestRange<I> range : ranges) {
                System.out.println("正在生成 " + range.getDescription() + " 的 " + range.getCount() + " 个测试用例");

                for (int i = 0; i < range.getCount(); i++) {
                    System.out.println("  正在生成测试用例 " + currentIndex);

                    I input = null;
                    O output = null;
                    boolean isValidTestCase = false;
                    int attempts = 0;

                    // 使用后置处理器进行验证，不满足则重新生成
                    while (!isValidTestCase && attempts < maxAttempts) {
                        attempts++;
                        if (attempts > 1) {
                            System.out.println("  重新生成第 " + attempts + " 次尝试");
                        }

                        input = range.getGenerator().generate();
                        output = solver.solve(input);

                        // 如果没有后置处理器或者通过了验证，则接受此测试用例
                        if (postProcessor == null || postProcessor.validate(input, output)) {
                            isValidTestCase = true;
                        }
                    }

                    if (!isValidTestCase) {
                        System.out.println("  警告：达到最大尝试次数 " + maxAttempts + "，使用最后一次生成的测试用例");
                    }

                    String inPath = basePath + "/" + currentIndex + ".in";
                    String outPath = basePath + "/" + currentIndex + ".out";

                    try (PrintStream inWriter = new PrintStream(inPath);
                         PrintStream outWriter = new PrintStream(outPath)) {
                        input.writeData(inWriter);
                        output.writeData(outWriter);
                    }

                    System.out.println("  结果: " + output +
                            (isValidTestCase ? " (通过验证)" : " (未通过验证，但已达到最大尝试次数)"));
                    currentIndex++;
                }
            }

            System.out.println("共生成 " + (currentIndex - startIndex) + " 个测试用例");
        }

        /**
         * 验证所有测试用例
         */
        public void verifyAllTestData(int count) throws Exception {
            for (int i = startIndex; i < startIndex + count; i++) {
                String inPath = basePath + "/" + i + ".in";
                String outPath = basePath + "/" + i + ".out";

                I input = inputFactory.get();
                input.readData(new Scanner(Files.newInputStream(Paths.get(inPath))));

                O expectedOutput = outputFactory.get();
                expectedOutput.readData(new Scanner(Files.newInputStream(Paths.get(outPath))));

                O actualOutput = solver.solve(input);

                boolean passed = expectedOutput.equals(actualOutput);
                System.out.println("测试用例 " + i + ": " + (passed ? "通过" : "失败"));
                if (!passed) {
                    System.out.println("  输入: " + input);
                    System.out.println("  预期输出: " + expectedOutput);
                    System.out.println("  实际输出: " + actualOutput);
                }
            }
        }
    }

    /**
     * 创建测试套件构建器的工厂方法
     */
    public static <I extends DataInput, O extends DataOutput> TestSuiteBuilder<I, O> createTestSuite(
            String basePath,
            Solver<I, O> solver,
            Supplier<I> inputFactory,
            Supplier<O> outputFactory) {
        return new TestSuiteBuilder<>(basePath, solver, inputFactory, outputFactory);
    }
}