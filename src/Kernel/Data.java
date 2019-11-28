package Kernel;

public class Data {
    public static final char[] vertexNameList;
    public static final int[][] distanceVertexPairs;
    public static final double[] distanceValueList;
    public static final int[][] busLineVertexPairs;
    public static final int[] busLineValueList;
    public static final int[][] oneWayTrafficPairs;
    public static final int[][] onlyVehiclePairs;
    public static final int[][] onlyPedestrianPairs;

    static {
        // init vertex name
        vertexNameList = new char[26];
        for (int i = 0; i < vertexNameList.length; ++i) {
            vertexNameList[i] = (char) ('A' + i);
        }

        // init distance
        distanceVertexPairs = new int[][]{
                {0, 1}, {0, 3}, {1, 2}, {1, 6}, {2, 7}, {3, 4}, {3, 8}, {4, 5},
                {5, 6}, {5, 10}, {6, 7}, {6, 11}, {7, 12}, {8, 9}, {8, 13}, {9, 10},
                {10, 11}, {10, 15}, {11, 12}, {11, 17}, {13, 14}, {14, 15}, {14, 20},
                {15, 16}, {15, 19}, {16, 17}, {16, 18}, {16, 19}, {17, 18}, {18, 25},
                {19, 20}, {19, 25}, {20, 21}, {20, 23}, {20, 25}, {21, 23}, {21, 22},
                {22, 23}, {23, 24}, {24, 25}
        };

        distanceValueList = new double[] {
                1.70, 1.20, 0.81, 1.12, 1.03, 0.51, 1.32, 0.68, 0.74, 1.14, 0.97,
                1.01, 0.94, 0.95, 0.90, 0.62, 0.66, 1.33, 0.92, 1.10, 0.83, 0.55,
                1.30, 0.62, 0.62, 0.73, 0.51, 0.93, 0.87, 0.38, 1.12, 1.89, 0.99,
                0.65, 1.70, 0.58, 1.10, 0.57, 1.40, 0.97
        };

        busLineVertexPairs = new int[][] {
                {5, 10}, {10, 19},
                {1, 6}, {6, 11}, {11, 17}, {17, 25}, {25, 24}
        };

        busLineValueList = new int[] {
                4, 5, 2, 3, 2, 2, 1
        };

        oneWayTrafficPairs = new int[][] {
                {20, 21}, {21, 22}, {22, 23}, {23, 20}
        };

        onlyVehiclePairs = new int[][] {
                {0, 1}, {1, 2}
        };

        onlyPedestrianPairs = new int[][] {
                {15, 19}, {19, 16}, {15, 16}, {16, 17}, {16, 18}
        };
    }
}
