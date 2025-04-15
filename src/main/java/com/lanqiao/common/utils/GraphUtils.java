package com.lanqiao.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenqi.zhang
 * @date 2024/5/3 13:24
 */
public class GraphUtils {
	public static List<int[]>[] generateGraph(int numOfNodes, int numOfEdges, int minWeight, int maxWeight, boolean selfLoop, boolean startFromOne) {
		List<int[]>[] e = new ArrayList[numOfNodes + (startFromOne ? 1 : 0)];
		for (int i = 0; i < e.length; i++) {
			e[i] = new ArrayList<>();
		}
		for (int i = 0; i < numOfEdges; i++) {
			int u = LocalRandomUtils.nextInt(numOfNodes) + (startFromOne ? 1 : 0);
			int v = LocalRandomUtils.nextInt(numOfNodes) + (startFromOne ? 1 : 0);
			while (selfLoop == false && u == v) {
				v = LocalRandomUtils.nextInt(numOfNodes);
			}
			int w = LocalRandomUtils.nextInt(minWeight, maxWeight);
			e[u].add(new int[]{v, w});
		}
		return e;
	}
}
