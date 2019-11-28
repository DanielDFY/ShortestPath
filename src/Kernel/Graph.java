package Kernel;

public class Graph {
    private double[][] matrix;
    private boolean[] mark;
    private int numEdge;

    public Graph(int n) {
        this.matrix = new double[n][n];
        this.mark = new boolean[n];
        this.numEdge = 0;
    }

    public Graph(Graph src) {
        int n = src.n();
        this.matrix = new double[n][n];
        this.mark = new boolean[n];
        this.numEdge = 0;

        // copy matrix
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                this.matrix[i][j] = src.matrix[i][j];
            }
        }

        // copy mark
        for (int i = 0; i < n; ++i) {
            this.mark[i] = src.mark[i];
        }

        // copy numEdge
        this.numEdge = src.numEdge;
    }

    public int n() {
        return mark.length;
    }

    public int e() {
        return numEdge;
    }

    public Edge first(int v) {
        for (int i = 0; i < mark.length; ++i) {
            if (matrix[v][i] != 0)
                return new Edge(v, i);
        }
        return null;
    }

    public Edge next(Edge w) {
        if (null == w)
            return null;
        for (int i = w.v2() + 1; i < mark.length; ++i) {
            if (matrix[w.v1()][i] != 0)
                return new Edge(w.v1(), i);
        }
        return null;
    }

    public boolean isEdge(Edge w) {
        if (null == w)
            return false;
        else
            return matrix[w.v1()][w.v2()] != 0;
    }

    public boolean isEdge(int i, int j) {
        return matrix[i][j] != 0;
    }

    public int v1(Edge w) {
        return w.v1();
    }

    public int v2(Edge w) { return w.v2(); }

    public void setEdge(Edge w, double weight) {
        assert weight != 0 : "weight should not be 0";
        if (matrix[w.v1()][w.v2()] == 0)
            ++numEdge;
        matrix[w.v1()][w.v2()] = weight;
    }

    public void setEdge(int v1, int v2, double weight) {
        assert weight != 0 : "cannot set weight to 0";
        if (matrix[v1][v2] == 0)
            ++numEdge;
        matrix[v1][v2] = weight;
    }

    public void delEdge(Edge w) {
        if (matrix[w.v1()][w.v2()] != 0)
            --numEdge;
        matrix[w.v1()][w.v2()] = 0;
    }

    public void delEdge(int v1, int v2) {
        if (matrix[v1][v2] != 0)
            --numEdge;
        matrix[v1][v2] = 0;
    }

    public double weight(Edge w) {
        return matrix[w.v1()][w.v2()];
    }

    public double weight(int v1, int v2) {
        return matrix[v1][v2];
    }

    public void setMark(int v, boolean val) {
        mark[v] = val;
    }

    public boolean getMark(int v) {
        return mark[v];
    }

    public void clearMarks() {
        mark = new boolean[mark.length];
    }
}
