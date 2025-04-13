package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;
import com.lanqiao.test.DataInput;
import com.lanqiao.test.DataOutput;
import com.lanqiao.test.TestGenerator;
import com.lanqiao.test.adapter.GenericSolverAdapter;
import com.lanqiao.test.adapter.OutputAdapter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static com.lanqiao.test.TestGenerator.createTestSuite;

/**
 * @author zhenqi.zhang
 * @date 2024/9/15 18:16
 */
public class DataUtil {
    public static final String path = "/Users/zzq/Library/CloudStorage/OneDrive-njust.edu.cn/20241218-part2/字符串魅力值总和/data";

    public AlgorithmInputData generateData(
            final int MIN_N,
            final int MAX_N
    ) {
        int N = LocalRandomUtils.nextInt(MIN_N, MAX_N + 1);
        AlgorithmInputData result = new AlgorithmInputData();
        result.s = LocalRandomUtils.nextLowerLetterString(N);
        return result;
    }


    @Test
    public void generateDataToFile() throws FileNotFoundException {
        Object[][] req = {
                {4, 1, 10},
                {4, 11, 100},
                {4, 101, 1000},
                {4, 1001, 10000},
                {4, 10001, 100000},
        };
        int i = 0;
        for (Object[] r : req) {
            int caseCount = (int) r[0];
            int MIN_N = (int) r[1];
            int MAX_N = (int) r[2];
            for (int j = 0; j < caseCount; ) {
                System.out.println("generate data " + i);
                AlgorithmInputData inputData = generateData(MIN_N, MAX_N);
                AlgorithmOutputData res = new Main().solve(inputData);
                DataUtils.generateDataToFile(path, i, () -> inputData, new Main());
                System.out.println("result: " + res);
                i++;
                j++;
            }
        }
    }

    @Test
    public void checkAllCases() throws Exception {
        DataUtils.checkAllCases(path, new Main());
    }


    // ====================================================================
    // 包装类 - 这些类存在于测试项目中，作为ACM代码和测试库之间的桥梁
    // ====================================================================

    /**
     * 字符串魅力值问题输入数据包装类
     * 同时实现DataInput和ACM代码中的AlgorithmInput接口
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class AlgorithmInputDataWrapper extends AlgorithmInputData implements AlgorithmInput, DataInput {
        @Override
        public AlgorithmInputData getData() {
            return this;
        }

        @Override
        public DataInput readData(Scanner scanner) {
            super.read(scanner);
            return this;
        }

        @Override
        public void writeData(PrintStream printStream) {
            super.write(printStream);
        }

    }

    /**
     * 字符串魅力值问题输出数据包装类
     * 同时实现DataOutput和ACM代码中的AlgorithmOutput接口
     */
    @Data
    public static class AlgorithmOutputDataWrapper implements AlgorithmOutput, DataOutput {
        private AlgorithmOutputData algorithmOutput;

        public AlgorithmOutputDataWrapper() {
            this.algorithmOutput = new AlgorithmOutputData();
        }

        public AlgorithmOutputDataWrapper(AlgorithmOutputData algorithmOutput) {
            this.algorithmOutput = algorithmOutput;
        }

        @Override
        public AlgorithmOutputData getData() {
            return getAlgorithmOutput();
        }

        @Override
        public DataOutput readData(Scanner scanner) {
            algorithmOutput.read(scanner);
            return this;
        }

        @Override
        public void writeData(PrintStream printStream) {
            algorithmOutput.write(printStream);
        }

        @Override
        public void write(PrintStream out) {
            algorithmOutput.write(out);
        }

        @Override
        public AlgorithmOutput read(Scanner scanner) {
            algorithmOutput.read(scanner);
            return this;
        }

    }

    /**
     * 为字符串魅力值问题创建适配器
     */
    private GenericSolverAdapter<AlgorithmInputData, AlgorithmOutputData> createStringAttractionAdapter() {
        return new GenericSolverAdapter<AlgorithmInputData, AlgorithmOutputData>(
                new Main(),
                AlgorithmInputData.class,
                AlgorithmOutputData.class,
                "solve",
                new AlgorithmOutputAdapter()
        );
    }

    public class AlgorithmOutputAdapter implements OutputAdapter<AlgorithmOutputData> {
        @Override
        public DataOutput adapt(AlgorithmOutputData output) {
            return new AlgorithmOutputDataWrapper(output);
        }
    }

    /**
     * 生成字符串魅力值问题的测试数据
     */
    @Test
    public void generateStringAttractionTestData() throws FileNotFoundException {
        GeneratorBuilder<AlgorithmInputDataWrapper> dataInputGeneratorBuilder = GeneratorBuilder.<AlgorithmInputDataWrapper>of(AlgorithmInputDataWrapper::new)
                .with(StringGeneratorUtils.lowerStringGenerator(10), AlgorithmInputDataWrapper::setS);

        Generator<AlgorithmInputDataWrapper> dataInputGenerator = dataInputGeneratorBuilder.build();


        // 创建适配器
        GenericSolverAdapter<AlgorithmInputData, AlgorithmOutputData> adapter = createStringAttractionAdapter();

        // 创建测试套件构建器
        TestGenerator.TestSuiteBuilder<DataInput, DataOutput> builder = createTestSuite(
                "/Users/zzq/Desktop/t1",
                adapter,
                AlgorithmInputDataWrapper::new,
                AlgorithmOutputDataWrapper::new
        );

        builder.setPostProcessor(
                (DataInput in, DataOutput out) -> {
                    // 这里可以添加后处理逻辑
                    // 比如：检查输出是否符合预期
                    AlgorithmInputData input = in.getData();
                    System.out.println("input: " + input);
                    AlgorithmOutputData output = out.getData();
                    return output.ans > 250;
                }
        );

        // 添加测试范围
        builder.addRange(
                4,
                dataInputGenerator,
                "小字符串测试 (1-10)"
        );
        // 生成测试数据
        builder.generateAllTestData();
    }


}
