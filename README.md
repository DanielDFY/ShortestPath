# Development Document

### Design and Implementation

This Java compression project is based on Dijkstra’s Algorithm and Floyd’s Algorithm. GUI is based on  Javafx framework. 

* **Data Structure**
  * Graph : Because the edges between position vertices are dense, matrix version is chosen in this project.
  * Edge : Only contains two end-point vertices.

* **Algorithm Kernel**

  * Dijkstra’s Algorithm

    Because the weight of edge is always positive (zero for not connected), the algorithm can simply be implemented based on DFS:
  
    ```java
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
    ```
  
  * Floyd’s Algorithm
  
    To obtain all the shortest paths between each pair of the vertices, because we only concern about the final length of each path, Floyd's algorithm can be efficient:
  
    ```java
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
    ```
  
* Implementation in each method under different modes

  Before the main calculation, construct different Graph objects for 3 different modes respectively.

  * Method one : the shortest path between chosen departure and destination.

    Given start vertex and end vertex, simply apply Dijkstra’s algorithm to get the path and its length.

  * Method two: the shortest path from any other vertex to the chosen merge point.

    Go through all the vertices and find the shortest path between it and the merge vertex as process above.

  * Method three: the length of the shortest path between each pair of vertices.

    Apply Floyd’s algorithm to the given graph to get the result matrix.

    

  * On-foot or by-car mode: use pedestrianMap or vehicleMap.

  * By-bus mode: first use busLineMap to find intersected bus stations, then use vehicleMap to calculate midway paths.
