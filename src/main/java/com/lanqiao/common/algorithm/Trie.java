package com.lanqiao.common.algorithm;

/**
 * 数字
 *
 * @author zhenqi.zhang
 * @date 2024/3/2 15:01
 */
public class Trie {
	TrieNode root;
	static final int HIGH_BIT = 30;

	public Trie() {
		root = new TrieNode();
	}

	public Trie(int maxn) {
		root = new TrieNode(maxn);
	}

	public void add(int num) {
		TrieNode cur = root;
		for (int k = HIGH_BIT; k >= 0; --k) {
			int bit = (num >> k) & 1;
			if (cur.nexts[bit] == null) {
				cur.nexts[bit] = new TrieNode();
				cur = cur.nexts[bit];
				cur.pass++;
			}
			cur.end++;
		}
	}

	public void remove(int num) {
		TrieNode cur = root;
		for (int k = HIGH_BIT; k >= 0; --k) {
			int bit = (num >> k) & 1;
			if (--cur.nexts[bit].pass == 0) {
				cur.nexts[bit] = null;
				return;
			}
			cur = cur.nexts[bit];
		}
	}

	public int checkMaxXor(int num) {
		TrieNode cur = root;
		int x = 0;
		for (int k = HIGH_BIT; k >= 0; --k) {
			int bit = (num >> k) & 1;
			if (bit == 0) {
				// a_i 的第 k 个二进制位为 0，应当往表示 1 的子节点 right 走
				if (cur.nexts[1] != null) {
					cur = cur.nexts[1];
					x = x * 2 + 1;
				} else {
					cur = cur.nexts[0];
					x = x * 2;
				}
			} else {
				// a_i 的第 k 个二进制位为 1，应当往表示 0 的子节点 left 走
				if (cur.nexts[0] != null) {
					cur = cur.nexts[0];
					x = x * 2 + 1;
				} else {
					cur = cur.nexts[1];
					x = x * 2;
				}
			}
		}
		return x;
	}

	public static class TrieNode {
		public int pass;
		public int end;
		public TrieNode[] nexts;

		public TrieNode() {
			this(26);
		}

		public TrieNode(int maxn) {
			pass = 0;
			end = 0;
			nexts = new TrieNode[maxn];
		}
	}
}
