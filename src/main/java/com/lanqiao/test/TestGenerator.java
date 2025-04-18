package com.lanqiao.test;

import com.lanqiao.test.generator.Generator;
import com.lanqiao.test.processor.TestPostProcessor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
    public static class TestSuiteBuilder<I, O, DI extends DataInput<? extends I>, DO extends DataOutput<? extends O>> {
        private final String basePath;
        private final Solver<I, O, DI, DO> solver;
        private final List<TestRange<DI>> ranges = new ArrayList<>();
        private final Supplier<DI> inputFactory;
        private final Supplier<DO> outputFactory;
        // 更新后处理器的类型
        private TestPostProcessor<I, O> postProcessor;

        private int startIndex = 0;
        private int maxAttempts = Integer.MAX_VALUE;  // 默认最大尝试次数


        public TestSuiteBuilder(String basePath,
                                Solver<I, O, DI, DO> solver,
                                Supplier<DI> inputFactory,
                                Supplier<DO> outputFactory) {
            this.basePath = basePath;
            this.solver = solver;
            this.inputFactory = inputFactory;
            this.outputFactory = outputFactory;
        }

        /**
         * 设置后置处理器
         */
        public TestSuiteBuilder<I, O, DI, DO> setPostProcessor(TestPostProcessor<I, O> postProcessor) {
            this.postProcessor = postProcessor;
            return this;
        }

        /**
         * 添加测试范围
         */
        public TestSuiteBuilder<I, O, DI, DO> addRange(int count, Generator<? extends DI> generator, String description) {
            ranges.add(new TestRange<>(count, generator, description));
            return this;
        }

        /**
         * 设置起始索引
         */
        public TestSuiteBuilder<I, O, DI, DO> setStartIndex(int startIndex) {
            this.startIndex = startIndex;
            return this;
        }


        /**
         * 生成所有测试用例
         */
        public void generateAllTestData() throws FileNotFoundException {
            log.info("开始生成测试用例，起始索引: {}", startIndex);
            int currentIndex = startIndex;

            for (TestRange<DI> range : ranges) {
                log.info("正在生成 {} 的 {} 个测试用例", range.getDescription(), range.getCount());

                for (int i = 0; i < range.getCount(); i++) {
                    log.info("  正在生成测试用例 {}", currentIndex);

                    DI input = null;
                    DO output = null;
                    boolean isValidTestCase = false;
                    int attempts = 0;
                    long solveCostMs = 0;

                    // 使用后置处理器进行验证，不满足则重新生成
                    while (!isValidTestCase && attempts < maxAttempts) {
                        attempts++;
                        if (attempts > 1) {
                            log.info("  重新生成第 {} 次尝试", attempts);
                        }

                        input = range.getGenerator().generate();
                        long solveStartStamp = System.currentTimeMillis();
                        output = solver.solve(input);
                        solveCostMs = System.currentTimeMillis() - solveStartStamp;

                        // 如果没有后置处理器或者通过了验证，则接受此测试用例
                        if (postProcessor == null || postProcessor.validate(input, output)) {
                            isValidTestCase = true;
                        }
                    }

                    if (!isValidTestCase) {
                        log.warn("  警告：达到最大尝试次数 {}，使用最后一次生成的测试用例", maxAttempts);
                    }

                    String inPath = basePath + "/" + currentIndex + ".in";
                    String outPath = basePath + "/" + currentIndex + ".out";

                    try (PrintStream inWriter = new PrintStream(inPath);
                         PrintStream outWriter = new PrintStream(outPath)) {
                        input.writeData(inWriter);
                        output.writeData(outWriter);
                    }

                    log.info("  结果= {} , 耗时{}ms, {}", output, solveCostMs,
                            (isValidTestCase ? "(通过验证)" : "(未通过验证，但已达到最大尝试次数)"));
                    currentIndex++;
                }
            }

            log.info("共生成 {} 个测试用例", (currentIndex - startIndex));
        }

        /**
         * 验证所有测试用例
         */
        public void verifyAllTestData(int count) throws Exception {
            for (int i = startIndex; i < startIndex + count; i++) {
                String inPath = basePath + "/" + i + ".in";
                String outPath = basePath + "/" + i + ".out";

                DI input = inputFactory.get();
                input.readData(new Scanner(Files.newInputStream(Paths.get(inPath))));

                DO expectedOutput = outputFactory.get();
                expectedOutput.readData(new Scanner(Files.newInputStream(Paths.get(outPath))));

                DO actualOutput = solver.solve(input);

                boolean passed = expectedOutput.equals(actualOutput);
                log.info("测试用例 {}: {}", i, (passed ? "通过" : "失败"));
                if (!passed) {
                    log.error("  输入: {}", input);
                    log.error("  预期输出: {}", expectedOutput);
                    log.error("  实际输出: {}", actualOutput);
                }
            }
        }
    }


    /**
     * 创建测试套件构建器的工厂方法
     */
    public static <I, O, DI extends DataInput<? extends I>, DO extends DataOutput<? extends O>>
    TestSuiteBuilder<I, O, DI, DO> createTestSuite(
            String basePath,
            Solver<I, O, DI, DO> solver,
            Supplier<DI> inputFactory,
            Supplier<DO> outputFactory) {
        return new TestSuiteBuilder<>(basePath, solver, inputFactory, outputFactory);
    }
}