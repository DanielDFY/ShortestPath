package Kernel;

import java.util.Stack;

public class PathUtils {
    public static class Result {
        public final int[] path;
        public final double dist;

        public Result(int[] path, double dist) {
            this.path = path;
            this.dist = dist;
        }
    }

    // Dijkstra’s Algorithm
    public static Result shortestPath(Graph graph, int start, int end) {
        if (start == end) {
            int[] path = new int[2];
            path[0] = path[1] = start;
            return new Result(path, 0);
        }

        double[] dist = new double[graph.n()];
        for (int i = 0; i < graph.n(); ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        dist[start] = 0;

        int[] edgeFrom = new int[graph.n()];
        for (int i = 0; i < graph.n(); ++i) {
            edgeFrom[i] = i;
        }

        graph.clearMarks();
        for (int i = 0; i < graph.n(); ++i) {
            int v = minVertex(graph, dist);
            graph.setMark(v, true);

            if (Integer.MAX_VALUE == dist[v])
                break;    // Unreachable

            for (Edge w = graph.first(v); graph.isEdge(w); w = graph.next(w)) {
                if (dist[v] + graph.weight(w) < dist[graph.v2(w)]) {
                    dist[graph.v2(w)] = dist[v] + graph.weight(w);
                    edgeFrom[graph.v2(w)] = v;
                }
            }
        }

        if (edgeFrom[end] == end)
            return null;

        Stack<Integer> stack = new Stack<>();
        for (int i = end; i != start; i = edgeFrom[i]) {
            stack.push(i);
        }
        stack.push(start);

        int[] path = new int[stack.size()];
        for (int i = 0; i < path.length; ++i) {
            path[i] = stack.pop();
        }

        return new Result(path, dist[end]);
    }

    private static int minVertex(Graph graph, double[] dist) {
        int v = 0;

        for (int i = 0; i < graph.n(); ++i) {
            if (!graph.getMark(i)) {
                v = i;
                break;
            }
        }

        for (int i = 1; i < graph.n(); ++i) {
            if (!graph.getMark(i) && dist[i] < dist[v])
                v = i;
        }

        return v;
    }

    // Floyd’s Algorithm
    public static double[][] allPairShortestPath(Graph graph) {
        int n = graph.n();
        double[][] distMatrix = new double[n][n];

        for (int i = 0; i < graph.n(); ++i)
            for (int j = 0; j < graph.n(); ++j)
                distMatrix[i][j] = graph.isEdge(i, j) ? graph.weight(i, j) : Double.MAX_VALUE;

        for (int k = 0; k < graph.n(); ++k) {
            for (int i = 0; i < graph.n(); ++i) {
                for (int j = 0; j < graph.n(); ++j) {
                    boolean ikReachable = distMatrix[i][k] != Double.MAX_VALUE;
                    boolean kjReachable = distMatrix[k][j] != Double.MAX_VALUE;
                    boolean ijShorter = distMatrix[i][j] > (distMatrix[i][k] + distMatrix[k][j]);

                    if (ikReachable && kjReachable && ijShorter)
                        distMatrix[i][j] = distMatrix[i][k] + distMatrix[k][j];
                }
            }
        }
        return distMatrix;
    }
}
