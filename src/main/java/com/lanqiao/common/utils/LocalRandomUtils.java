package com.lanqiao.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author zhenqi.zhang
 * @date 2023/12/31 18:22
 */
public class LocalRandomUtils {
    public static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static char[] floatDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
    public static final char[] LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static ThreadLocalRandom getLocalRandom() {
        return ThreadLocalRandom.current();
    }

    public static int nextInt() {
        return getLocalRandom().nextInt();
    }

    public static int nextInt(int bound) {
        return getLocalRandom().nextInt(bound);
    }

    public static int nextInt(int origin, int bound) {
        return getLocalRandom().nextInt(origin, bound);
    }

    public static int nextIntNotInclude(Collection<Integer> notInclude) {
        int val = nextInt();
        while (notInclude != null && notInclude.contains(val)) {
            val = nextInt();
        }
        return val;
    }

    public static int nextIntNotInclude(int bound, Collection<Integer> notInclude) {
        int val = nextInt(bound);
        while (notInclude != null && notInclude.contains(val)) {
            val = nextInt(bound);
        }
        return val;
    }

    public static <T> T choicesByWeight(List<T> numbers, List<Integer> weights) {
        long sum = 0;
        for (int i = 0; i < weights.size(); i++) {
            sum += weights.get(i);
        }
        long finalSum = sum;
        List<Double> probabilities = weights.stream()
                .mapToDouble(i -> i * 1.0 / finalSum)
                .boxed()
                .collect(java.util.stream.Collectors.toList());
        return choicesByProb(numbers, probabilities);
    }

    public static <T> T choicesByProb(List<T> numbers, List<Double> probabilities) {
        Random rand = getLocalRandom();
        double randomValue = rand.nextDouble();

        double cumulativeProbability = 0.0;
        for (int i = 0; i < probabilities.size(); i++) {
            cumulativeProbability += probabilities.get(i);
            if (randomValue <= cumulativeProbability) {
                return numbers.get(i);
            }
        }

        // If no number is selected, return null or throw an exception
        return null;
    }


    public static <T> T getRamdomElement(Collection<T> collection) {
        int index = nextInt(collection.size());
        int i = 0;
        for (T t : collection) {
            if (i == index) {
                return t;
            }
            i++;
        }
        return null;
    }

    public static <T> T getAndRemoveRamdomElement(Collection<T> collection) {
        int index = nextInt(collection.size());
        int i = 0;
        for (T t : collection) {
            if (i == index) {
                collection.remove(t);
                return t;
            }
            i++;
        }
        return null;
    }

    //-----------------------double-----------------------
    public static double nextDouble() {
        return getLocalRandom().nextDouble();
    }

    public static double nextDouble(double bound) {
        return getLocalRandom().nextDouble(bound);
    }

    public static double nextDouble(double origin, double bound) {
        return getLocalRandom().nextDouble(origin, bound);
    }

    //-----------------------char-----------------------
    public static char nextChar() {
        return (char) nextInt(0, 128);
    }

    public static char nextLowerChar() {
        return LOWER_LETTERS[nextInt(LOWER_LETTERS.length)];
    }

    public static char nextChar(char[] chars) {
        return chars[nextInt(chars.length)];
    }


    //----------------------- String -----------------------
    public static String nextString(int length, char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars[nextInt(chars.length)]);
        }
        return sb.toString();
    }

    public static String nextPalindromeString(int length) {
        return nextPalindromeString(length, LOWER_LETTERS);
    }

    public static String nextPalindromeString(int length, char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length / 2; i++) {
            sb.append(chars[nextInt(chars.length)]);
        }
        String s = sb.toString();
        return s + (length % 2 == 0 ? "" : chars[nextInt(chars.length)]) + new StringBuilder(s).reverse().toString();
    }

    public static String nextDigitString(int length) {
        return nextString(length, digits);
    }

    public static String nextLowerLetterString(int length) {
        return nextString(length, LOWER_LETTERS);
    }

    public static String nextFloatString(int length) {
        String res = nextString(length, digits);
        int index = nextInt(length);
        String ans = res.substring(0, index) + "." + res.substring(index);
        if (ans.startsWith(".")) {
            ans = "0" + ans;
        } else {
            int i = 0;
            while (i + 1 < ans.length() && ans.charAt(i) == '0' && ans.charAt(i + 1) != '.') {
                i++;
            }
            ans = ans.substring(i);
        }
        return ans;
    }

    //-----------------array-------------------------

    public static int[] generateArray(int n, int bound) {
        return generateArray(n, 0, bound);
    }

    /**
     * 生成一个长度为n的随机数组，范围在origin到bound之间
     * @param n
     * @param origin
     * @param bound
     * @return
     */
    public static int[] generateArray(int n, int origin, int bound) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = nextInt(origin, bound);
        }
        return arr;
    }

    //----------------list-------------------------
    public static List<Integer> nextIntList(int n, int bound) {
        return nextIntList(n, 0, bound);
    }

    public static List<Integer> nextIntList(int n, int origin, int bound) {
        List<Integer> list = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            list.add(nextInt(origin, bound));
        }
        return list;
    }

    //-----------------tree-------------------------
    public static int[] generateTree(int n) {
        int[] tree = new int[n];
        tree[0] = -1; // root node has no parent
        for (int i = 1; i < n; i++) {
            tree[i] = nextInt(i); // parent node is a random node before current node
        }
        return tree;
    }

    public static void main(String[] args) {
        getLocalRandom().nextInt();
        for (int i = 0; i < 200; i++) {
            System.out.println(nextFloatString(10));
        }
    }

    //-------------------common-----------------------
//	public static <T> T (double probability, T t1, T t2) {
//		return getLocalRandom().nextDouble() < probability ? t1 : t2;
//	}
    @SuppressWarnings("unchecked")
    public static <T extends Number> T generateRandomNumber(Class<T> clazz, T left, T right) {
        if (clazz == Integer.class) {
            int min = left.intValue();
            int max = right.intValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            // nextInt(n)生成0到n-1的随机数，所以需要加1来包含上边界
            return (T) Integer.valueOf(getLocalRandom().nextInt(min, max));
        } else if (clazz == Double.class) {
            double min = left.doubleValue();
            double max = right.doubleValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            // random.nextDouble()生成[0,1)的随机数
            return (T) Double.valueOf(getLocalRandom().nextDouble(min, max));
        } else if (clazz == Float.class) {
            double min = left.doubleValue();
            double max = right.doubleValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            return (T) Float.valueOf((float) getLocalRandom().nextDouble(min, max));
        } else if (clazz == Long.class) {
            long min = left.longValue();
            long max = right.longValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            return (T) Long.valueOf(getLocalRandom().nextLong(min, max));
        } else if (clazz == Byte.class) {
            byte min = left.byteValue();
            byte max = right.byteValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            return (T) Byte.valueOf((byte) (getLocalRandom().nextInt(min, max)));
        } else if (clazz == Short.class) {
            short min = left.shortValue();
            short max = right.shortValue();
            if (min >= max) {
                throw new IllegalArgumentException("左边界必须小于右边界");
            }
            return (T) Short.valueOf((short) (getLocalRandom().nextInt(min, max)));
        } else {
            throw new IllegalArgumentException("不支持的数字类型: " + clazz.getName());
        }
    }

    public static <T extends Number> List<T> generateRandomNumberList(Class<T> clazz, int size, T left, T right) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(generateRandomNumber(clazz, left, right));
        }
        return list;
    }
}