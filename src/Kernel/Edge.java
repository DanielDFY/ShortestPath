package Kernel;

public class Edge {
    private int vert1, vert2; // the vertex indices
    public Edge (int vt1, int vt2) {vert1 = vt1; vert2 = vt2;}
    public int v1() {return vert1;}
    public int v2() {return vert2;}
}
