package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;

import java.util.Random;

/**
 * @Author zzq
 * @Date 2025/4/13 11:23
 */
public class StringGeneratorUtils {
    private static final Random random = LocalRandomUtils.getLocalRandom();


    // 固定长度随机字符串生成器
    public static Generator<String> lowerStringGenerator(int length) {
        return () -> {
            StringBuilder sb = new StringBuilder(length);
            Generator<Character> charGen = CharGeneratorUtils.lowercaseLetterGenerator();
            for (int i = 0; i < length; i++) {
                sb.append(charGen.generate());
            }
            return sb.toString();
        };
    }

    // 可变长度随机字符串生成器
    public static Generator<String> lowerStringGenerator(int minLength, int maxLength) {
        return () -> {
            int length = random.nextInt(maxLength - minLength + 1) + minLength;
            return lowerStringGenerator(length).generate();
        };
    }


    // 数字字符串生成器
    public static Generator<String> numericStringGenerator(int length) {
        return () -> {
            StringBuilder sb = new StringBuilder(length);
            Generator<Character> digitGen = CharGeneratorUtils.digitGenerator();
            for (int i = 0; i < length; i++) {
                sb.append(digitGen.generate());
            }
            return sb.toString();
        };
    }

    // 带特殊字符的字符串生成器
    public static Generator<String> mixedStringGenerator(int length, double specialCharProbability) {
        return () -> {
            StringBuilder sb = new StringBuilder(length);
            Generator<Character> letterGen = CharGeneratorUtils.lowercaseLetterGenerator();
            Generator<Character> specialGen = CharGeneratorUtils.specialCharGenerator();

            for (int i = 0; i < length; i++) {
                if (random.nextDouble() < specialCharProbability) {
                    sb.append(specialGen.generate());
                } else {
                    sb.append(letterGen.generate());
                }
            }
            return sb.toString();
        };
    }

    // 指定字符集合的字符串生成器
    public static Generator<String> customCharSetStringGenerator(int length, char[] charSet) {
        return () -> {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(charSet[random.nextInt(charSet.length)]);
            }
            return sb.toString();
        };
    }

    // 指定字符集合的可变长度字符串生成器
    public static Generator<String> customCharSetStringGenerator(int minLength, int maxLength, char[] charSet) {
        return () -> {
            int length = random.nextInt(maxLength - minLength + 1) + minLength;
            return customCharSetStringGenerator(length, charSet).generate();
        };
    }

    // 指定字符串的字符串生成器（从给定的字符串集合中随机选择一个）
    public static Generator<String> fromStringsGenerator(String... options) {
        return () -> options[random.nextInt(options.length)];
    }

    // 前缀加随机字符串生成器
    public static Generator<String> prefixedStringGenerator(String prefix, int length) {
        return () -> prefix + lowerStringGenerator(length).generate();
    }

    // 后缀加随机字符串生成器
    public static Generator<String> suffixedStringGenerator(int length, String suffix) {
        return () -> lowerStringGenerator(length).generate() + suffix;
    }

    // 模板字符串生成器（将模板中的特定标记替换为随机字符串）
    public static Generator<String> templateStringGenerator(String template, String placeholder, int length) {
        return () -> template.replace(placeholder, lowerStringGenerator(length).generate());
    }

    // 邮箱地址生成器
    public static Generator<String> emailGenerator() {
        return () -> {
            String username = lowerStringGenerator(5, 10).generate();
            String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "163.com", "qq.com", "example.com"};
            String domain = domains[random.nextInt(domains.length)];
            return username + "@" + domain;
        };
    }

    // 手机号码生成器（中国格式）
    public static Generator<String> chinesePhoneNumberGenerator() {
        return () -> {
            String[] prefixes = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139",
                    "150", "151", "152", "153", "155", "156", "157", "158", "159",
                    "170", "171", "172", "173", "175", "176", "177", "178", "179",
                    "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};
            String prefix = prefixes[random.nextInt(prefixes.length)];
            return prefix + numericStringGenerator(8).generate();
        };
    }

    // UUID生成器
    public static Generator<String> uuidGenerator() {
        return () -> java.util.UUID.randomUUID().toString();
    }

    // 汉字字符串生成器
    public static Generator<String> chineseCharacterGenerator(int length) {
        return () -> {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                // 基本汉字的Unicode编码范围是0x4E00-0x9FA5
                char c = (char) (0x4E00 + random.nextInt(0x9FA5 - 0x4E00 + 1));
                sb.append(c);
            }
            return sb.toString();
        };
    }

    // 单词生成器（从预定义词典中选择）
    public static Generator<String> wordGenerator() {
        return () -> {
            String[] commonWords = {"apple", "banana", "orange", "grapes", "car", "bus", "train", "plane",
                    "house", "building", "computer", "phone", "table", "chair", "book", "pen",
                    "water", "coffee", "tea", "milk", "bread", "butter", "cheese", "meat",
                    "dog", "cat", "bird", "fish", "red", "green", "blue", "yellow"};
            return commonWords[random.nextInt(commonWords.length)];
        };
    }

    // 句子生成器（生成一个包含指定单词数量的句子）
    public static Generator<String> sentenceGenerator(int wordCount) {
        return () -> {
            StringBuilder sb = new StringBuilder();
            Generator<String> wordGen = wordGenerator();

            for (int i = 0; i < wordCount; i++) {
                if (i > 0) {
                    sb.append(" ");
                }
                sb.append(wordGen.generate());
            }

            // 首字母大写并加上句号
            if (sb.length() > 0) {
                char firstChar = Character.toUpperCase(sb.charAt(0));
                sb.setCharAt(0, firstChar);
                sb.append(".");
            }

            return sb.toString();
        };
    }
}
