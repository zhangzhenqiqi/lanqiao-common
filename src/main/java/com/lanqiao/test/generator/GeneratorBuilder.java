package com.lanqiao.test.generator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class GeneratorBuilder<T> {
    private final Supplier<T> factory;
    private final List<FieldConfig<T, ?>> fieldConfigs = new ArrayList<>();


    // 构造函数，接收一个工厂方法来创建目标对象
    private GeneratorBuilder(Supplier<T> factory) {
        this.factory = factory;
    }


    /**
     * 创建builder的静态方法
     *
     * @param factory 获取一个empty instance
     * @param <T>
     * @return
     */
    public static <T> GeneratorBuilder<T> of(Supplier<T> factory) {
        return new GeneratorBuilder<>(factory);
    }

    // 添加字段生成器
    public <V> GeneratorBuilder<T> with(Generator<V> generator, BiConsumer<T, V> setter) {
        fieldConfigs.add(new FieldConfig<>(generator, setter));
        return this;
    }

    public <V, D> GeneratorBuilder<T> withDependent(
            Function<T, D> dependencyGetter,
            Function<D, Generator<V>> generatorProvider,
            BiConsumer<T, V> setter) {
        fieldConfigs.add(new DependentFieldConfig<>(dependencyGetter, generatorProvider, setter));
        return this;
    }

    // 添加到GeneratorBuilder类中
    public <V, D1, D2> GeneratorBuilder<T> withMultiDependents(
            Function<T, D1> dependency1Getter,
            Function<T, D2> dependency2Getter,
            BiFunction<D1, D2, Generator<V>> generatorProvider,
            BiConsumer<T, V> setter) {
        fieldConfigs.add(new MultiDependentFieldConfig<>(
                dependency1Getter,
                dependency2Getter,
                generatorProvider,
                setter));
        return this;
    }

    // 可变参数版本，支持任意数量的依赖
    public <V> GeneratorBuilder<T> withDependents(
            List<Function<T, ?>> dependencyGetters,
            Function<List<?>, Generator<V>> generatorProvider,
            BiConsumer<T, V> setter) {
        fieldConfigs.add(new FlexibleDependentFieldConfig<>(
                dependencyGetters,
                generatorProvider,
                setter));
        return this;
    }

    // 生成方法
    public Generator<T> build() {
        return () -> {
            //创建一个empty实例
            T instance = factory.get();

            //遍历所有的配置
            for (FieldConfig<T, ?> config : fieldConfigs) {
                config.applyTo(instance);
            }

            return instance;
        };
    }


    /**
     * 配置类，存储 generator 和 setter
     * 通过generator生产value并用setter进目标字段中
     *
     * @param <T>
     * @param <V>
     */
    @Getter
    private static class FieldConfig<T, V> {
        private final Generator<V> generator;
        private final BiConsumer<T, V> setter;

        public FieldConfig(Generator<V> generator, BiConsumer<T, V> setter) {
            this.generator = generator;
            this.setter = setter;
        }

        public void applyTo(T instance) {
            V value = generator.generate();
            setter.accept(instance, value);
        }
    }

    // 依赖其他字段的配置类
    private static class DependentFieldConfig<T, V, D> extends FieldConfig<T, V> {
        private final Function<T, D> dependency;
        private final Function<D, Generator<V>> generatorProvider;

        public DependentFieldConfig(
                Function<T, D> dependency,
                Function<D, Generator<V>> generatorProvider,
                BiConsumer<T, V> setter) {
            super(null, setter);
            this.dependency = dependency;
            this.generatorProvider = generatorProvider;
        }

        @Override
        public void applyTo(T instance) {
            //获取其他值
            D dependencyValue = dependency.apply(instance);
            //根据获取到的值获取指定的generator
            Generator<V> generator = generatorProvider.apply(dependencyValue);
            V value = generator.generate();
            getSetter().accept(instance, value);
        }
    }

    // 两个新的配置类
    private static class MultiDependentFieldConfig<T, V, D1, D2> extends FieldConfig<T, V> {
        private final Function<T, D1> dependency1;
        private final Function<T, D2> dependency2;
        private final BiFunction<D1, D2, Generator<V>> generatorProvider;

        public MultiDependentFieldConfig(
                Function<T, D1> dependency1,
                Function<T, D2> dependency2,
                BiFunction<D1, D2, Generator<V>> generatorProvider,
                BiConsumer<T, V> setter) {
            super(null, setter);
            this.dependency1 = dependency1;
            this.dependency2 = dependency2;
            this.generatorProvider = generatorProvider;
        }

        @Override
        public void applyTo(T instance) {
            D1 value1 = dependency1.apply(instance);
            D2 value2 = dependency2.apply(instance);
            Generator<V> generator = generatorProvider.apply(value1, value2);
            V value = generator.generate();
            getSetter().accept(instance, value);
        }
    }

    private static class FlexibleDependentFieldConfig<T, V> extends FieldConfig<T, V> {
        private final List<Function<T, ?>> dependencyGetters;
        private final Function<List<?>, Generator<V>> generatorProvider;

        public FlexibleDependentFieldConfig(
                List<Function<T, ?>> dependencyGetters,
                Function<List<?>, Generator<V>> generatorProvider,
                BiConsumer<T, V> setter) {
            super(null, setter);
            this.dependencyGetters = dependencyGetters;
            this.generatorProvider = generatorProvider;
        }

        @Override
        public void applyTo(T instance) {
            List<Object> dependencyValues = new ArrayList<>();
            for (Function<T, ?> getter : dependencyGetters) {
                dependencyValues.add(getter.apply(instance));
            }
            Generator<V> generator = generatorProvider.apply(dependencyValues);
            V value = generator.generate();
            getSetter().accept(instance, value);
        }
    }

}