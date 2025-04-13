package com.lanqiao.test.generator;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;
import java.util.function.Function;

/**
 * @Author zzq
 * @Date 2025/4/13 10:35
 */
@Slf4j
public class GeneratorBuilderTest {
    @Data
    public static class Clothes {
        private int type;
        private double price;
    }

    @Data
    public static class Person {
        private int age;
        private long id;
        private Clothes clothes;
    }

    @Data
    class Employee {
        private String name;
        private int level;      // 职位级别：1-初级，2-中级，3-高级，4-专家
        private double salary;  //
    }

    @Test
    public void test() {
        Generator<Clothes> clothesGenerator = GeneratorBuilder.of(Clothes::new)
                .with(NumberGeneratorUtils.intGenerator(0, 3), Clothes::setType)
                .with(NumberGeneratorUtils.doubleGenerator(), Clothes::setPrice)
                .build();

        Generator<Person> personGenerator = GeneratorBuilder.of(Person::new)
                .with(NumberGeneratorUtils.intGenerator(0, 1000), Person::setAge)
                .with(NumberGeneratorUtils.longGenerator(), Person::setId)
                .with(clothesGenerator, Person::setClothes)
                .build();

        Person p1 = personGenerator.generate();
        Person p2 = personGenerator.generate();

        log.info("p1={} p2={}", p1, p2);

    }


    @Test
    public void testDependency() {
        // 名字生成器
        Generator<String> nameGenerator = () -> {
            String[] names = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九"};
            return names[new Random().nextInt(names.length)];
        };

        // 职位级别生成器：1-4
        Generator<Integer> levelGenerator = NumberGeneratorUtils.intGenerator(1, 4);

        // 基于职位级别的薪资生成器
        Function<Integer, Generator<Double>> salaryGeneratorProvider = level -> {
            switch (level) {
                case 1:
                    return NumberGeneratorUtils.doubleGenerator(5000.0, 10000.0);
                case 2:
                    return NumberGeneratorUtils.doubleGenerator(10000.0, 15000.0);
                case 3:
                    return NumberGeneratorUtils.doubleGenerator(15000.0, 25000.0);
                case 4:
                    return NumberGeneratorUtils.doubleGenerator(25000.0, 40000.0);
                default:
                    return NumberGeneratorUtils.doubleGenerator(5000.0, 8000.0); // 默认情况
            }
        };

        // 创建员工生成器
        Generator<Employee> employeeGenerator = GeneratorBuilder.of(Employee::new)
                .with(nameGenerator, Employee::setName)
                .with(levelGenerator, Employee::setLevel)
                .withDependent(
                        Employee::getLevel,           // 依赖的字段访问器
                        salaryGeneratorProvider,      // 根据依赖值提供相应的生成器
                        Employee::setSalary           // 设置生成的值
                )
                .build();

        // 生成10个员工并打印
        for (int i = 0; i < 10; i++) {
            Employee employee = employeeGenerator.generate();
            System.out.println(employee);
        }
    }
}
