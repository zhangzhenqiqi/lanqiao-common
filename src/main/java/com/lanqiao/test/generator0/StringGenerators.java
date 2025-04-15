//package com.lanqiao.test.generator;
//
//import com.lanqiao.test.DataInput;
//import com.lanqiao.test.RandomUtils;
//
//import java.util.function.Function;
//
//import static com.lanqiao.test.RandomUtils.random;
//
///**
// * 字符串测试数据生成器工厂
// */
//public class StringGenerators {
//    /**
//     * 创建随机字符串生成器
//     */
//    public static <T extends DataInput> Generator<T> createRandomStringGenerator(
//            int minLength, int maxLength,
//            Function<String, T> inputFactory) {
//
//        return () -> {
//            int length = RandomUtils.nextInt(minLength, maxLength);
//            String s = RandomUtils.nextLowerLetterString(length);
//            return inputFactory.apply(s);
//        };
//    }
//
//    /**
//     * 创建带特定字符的随机字符串生成器
//     */
//    public static <T extends DataInput> Generator<T> createStringWithSpecialCharsGenerator(
//            int minLength, int maxLength, String specialChars, double probability,
//            Function<String, T> inputFactory) {
//
//        return () -> {
//            int length = RandomUtils.nextInt(minLength, maxLength);
//            StringBuilder sb = new StringBuilder(length);
//
//            // 确保至少有一个特殊字符
//            int specialPos = random.nextInt(length);
//
//            for (int i = 0; i < length; i++) {
//                if (i == specialPos || random.nextDouble() < probability) {
//                    // 使用特殊字符
//                    char c = specialChars.charAt(random.nextInt(specialChars.length()));
//                    sb.append(c);
//                } else {
//                    // 使用普通字符
//                    sb.append((char) ('a' + random.nextInt(26)));
//                }
//            }
//
//            return inputFactory.apply(sb.toString());
//        };
//    }
//
//    /**
//     * 创建回文字符串生成器
//     */
//    public static <T extends DataInput> Generator<T> createPalindromeGenerator(
//            int minLength, int maxLength,
//            Function<String, T> inputFactory) {
//
//        return () -> {
//            int length = RandomUtils.nextInt(minLength, maxLength);
//            StringBuilder sb = new StringBuilder(length);
//
//            for (int i = 0; i < (length + 1) / 2; i++) {
//                char c = (char) ('a' + random.nextInt(26));
//                sb.append(c);
//            }
//
//            for (int i = length / 2 - 1; i >= 0; i--) {
//                sb.append(sb.charAt(i));
//            }
//
//            return inputFactory.apply(sb.toString());
//        };
//    }
//}
