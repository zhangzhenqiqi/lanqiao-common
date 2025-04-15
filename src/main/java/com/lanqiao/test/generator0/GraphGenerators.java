//package com.lanqiao.test.generator;
//
//import com.lanqiao.test.DataInput;
//import com.lanqiao.test.RandomUtils;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.function.Function;
//
//import static com.lanqiao.test.RandomUtils.random;
//
///**
// * 图测试数据生成器工厂
// * 这里假设图的表示方式是边的列表
// */
//public class GraphGenerators {
//    /**
//     * 表示图的边
//     */
//    public static class Edge {
//        public int from;
//        public int to;
//        public int weight;
//
//        public Edge(int from, int to, int weight) {
//            this.from = from;
//            this.to = to;
//            this.weight = weight;
//        }
//    }
//
//    /**
//     * 创建随机无向图生成器
//     */
//    public static <T extends DataInput> Generator<T> createRandomUndirectedGraphGenerator(
//            int minNodes, int maxNodes, int minEdges, int maxEdges, int minWeight, int maxWeight,
//            Function<Edge[], T> inputFactory) {
//
//        return () -> {
//            int n = RandomUtils.nextInt(minNodes, maxNodes);
//            int maxPossibleEdges = n * (n - 1) / 2;
//            int m = Math.min(maxPossibleEdges, RandomUtils.nextInt(minEdges, Math.min(maxEdges, maxPossibleEdges)));
//
//            Set<String> edgeSet = new HashSet<>();
//            Edge[] edges = new Edge[m];
//            int count = 0;
//
//            while (count < m) {
//                int u = RandomUtils.nextInt(1, n);
//                int v = RandomUtils.nextInt(1, n);
//
//                if (u != v) {
//                    // 对于无向图，边(u,v)和(v,u)是相同的
//                    String edge1 = Math.min(u, v) + "," + Math.max(u, v);
//
//                    if (!edgeSet.contains(edge1)) {
//                        edgeSet.add(edge1);
//                        int weight = RandomUtils.nextInt(minWeight, maxWeight);
//                        edges[count++] = new Edge(u, v, weight);
//                    }
//                }
//            }
//
//            return inputFactory.apply(edges);
//        };
//    }
//
//    /**
//     * 创建随机有向图生成器
//     */
//    public static <T extends DataInput> Generator<T> createRandomDirectedGraphGenerator(
//            int minNodes, int maxNodes, int minEdges, int maxEdges, int minWeight, int maxWeight,
//            Function<Edge[], T> inputFactory) {
//
//        return () -> {
//            int n = RandomUtils.nextInt(minNodes, maxNodes);
//            int maxPossibleEdges = n * (n - 1);
//            int m = Math.min(maxPossibleEdges, RandomUtils.nextInt(minEdges, Math.min(maxEdges, maxPossibleEdges)));
//
//            Set<String> edgeSet = new HashSet<>();
//            Edge[] edges = new Edge[m];
//            int count = 0;
//
//            while (count < m) {
//                int u = RandomUtils.nextInt(1, n);
//                int v = RandomUtils.nextInt(1, n);
//
//                if (u != v) {
//                    // 对于有向图，边(u,v)和(v,u)是不同的
//                    String edge = u + "," + v;
//
//                    if (!edgeSet.contains(edge)) {
//                        edgeSet.add(edge);
//                        int weight = RandomUtils.nextInt(minWeight, maxWeight);
//                        edges[count++] = new Edge(u, v, weight);
//                    }
//                }
//            }
//
//            return inputFactory.apply(edges);
//        };
//    }
//
//    /**
//     * 创建树生成器
//     */
//    public static <T extends DataInput> Generator<T> createTreeGenerator(
//            int minNodes, int maxNodes, int minWeight, int maxWeight,
//            Function<Edge[], T> inputFactory) {
//
//        return () -> {
//            int n = RandomUtils.nextInt(minNodes, maxNodes);
//            int m = n - 1; // 树的边数为节点数减1
//
//            Edge[] edges = new Edge[m];
//            boolean[] connected = new boolean[n + 1];
//            connected[1] = true; // 初始连接节点1
//
//            for (int i = 0; i < m; i++) {
//                // 找一个已连接的节点
//                List<Integer> connectedNodes = new ArrayList<>();
//                for (int j = 1; j <= n; j++) {
//                    if (connected[j]) {
//                        connectedNodes.add(j);
//                    }
//                }
//
//                // 找一个未连接的节点
//                List<Integer> unconnectedNodes = new ArrayList<>();
//                for (int j = 1; j <= n; j++) {
//                    if (!connected[j]) {
//                        unconnectedNodes.add(j);
//                    }
//                }
//
//                int u = connectedNodes.get(random.nextInt(connectedNodes.size()));
//                int v = unconnectedNodes.get(random.nextInt(unconnectedNodes.size()));
//
//                connected[v] = true;
//                int weight = RandomUtils.nextInt(minWeight, maxWeight);
//                edges[i] = new Edge(u, v, weight);
//            }
//
//            return inputFactory.apply(edges);
//        };
//    }
//}