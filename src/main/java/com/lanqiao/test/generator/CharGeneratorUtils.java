package com.lanqiao.test.generator;

import com.lanqiao.common.utils.LocalRandomUtils;

import java.util.Random;

/**
 * @Author zzq
 * @Date 2025/4/13 11:23
 */
public class CharGeneratorUtils {
    private static final Random random = LocalRandomUtils.getLocalRandom();
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    // 随机字符生成器
    public static Generator<Character> charGenerator() {
        return () -> (char) (random.nextInt(128));
    }

    // 小写字母生成器
    public static Generator<Character> lowercaseLetterGenerator() {
        return () -> LOWERCASE_LETTERS.charAt(random.nextInt(LOWERCASE_LETTERS.length()));
    }

    // 大写字母生成器
    public static Generator<Character> uppercaseLetterGenerator() {
        return () -> UPPERCASE_LETTERS.charAt(random.nextInt(UPPERCASE_LETTERS.length()));
    }

    // 数字字符生成器
    public static Generator<Character> digitGenerator() {
        return () -> DIGITS.charAt(random.nextInt(DIGITS.length()));
    }

    // 特殊字符生成器
    public static Generator<Character> specialCharGenerator() {
        return () -> SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
    }
}
