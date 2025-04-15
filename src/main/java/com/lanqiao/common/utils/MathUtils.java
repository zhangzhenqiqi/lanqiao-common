package com.lanqiao.common.utils;

import java.math.BigInteger;

/**
 * @author zhenqi.zhang
 * @date 2024/1/19 22:46
 */
public class MathUtils {
	public static long gcd(long a, long b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static long gcd(long... nums) {
		long res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			res = gcd(res, nums[i]);
		}
		return res;
	}

	public static long lcm(long a, long b) {
		return a / gcd(a, b) * b;
	}

	public static long lcm(long... nums) {
		long res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			res = lcm(res, nums[i]);
		}
		return res;
	}

	public static BigInteger lcm(BigInteger a, BigInteger b) {
		return a.divide(a.gcd(b)).multiply(b);
	}

	public static BigInteger lcm(BigInteger... nums) {
		BigInteger res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			res = lcm(res, nums[i]);
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(gcd(2, 3, 4));
		System.out.println(lcm(2, 3, 4));
	}
}
