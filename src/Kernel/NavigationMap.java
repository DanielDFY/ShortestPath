package Kernel;

public class NavigationMap {
    public static Graph base;
    public static Graph pedestrianMap;
    public static Graph vehicleMap;
    public static Graph busLineMap;

    public static void init() {
        // Map base
        base = new Graph(Data.vertexNameList.length);
        for (int i = 0; i < Data.distanceVertexPairs.length; ++i) {
            int v = Data.distanceVertexPairs[i][0];
            int w = Data.distanceVertexPairs[i][1];

            double weight = Data.distanceValueList[i];

            base.setEdge(v, w, weight);
            base.setEdge(w, v, weight);
        }

        // Pedestrian map
        pedestrianMap = new Graph(base);
        for (int i = 0; i < Data.onlyVehiclePairs.length; ++i) {
            int v = Data.onlyVehiclePairs[i][0];
            int w = Data.onlyVehiclePairs[i][1];
            pedestrianMap.delEdge(v, w);
            pedestrianMap.delEdge(w, v);
        }

        // Vehicle map
        vehicleMap = new Graph(base);
        for (int i = 0; i < Data.onlyPedestrianPairs.length; ++i) {
            int v = Data.onlyPedestrianPairs[i][0];
            int w = Data.onlyPedestrianPairs[i][1];
            vehicleMap.delEdge(v, w);
            vehicleMap.delEdge(w, v);
        }
        for (int i = 0; i < Data.oneWayTrafficPairs.length; ++i) {
            int v = Data.oneWayTrafficPairs[i][0];
            int w = Data.oneWayTrafficPairs[i][1];
            vehicleMap.delEdge(w, v);    // remove reverse path
        }

        // BusLine map
        busLineMap = new Graph(Data.vertexNameList.length);
        for (int i = 0; i < Data.busLineVertexPairs.length; ++i) {
            int v = Data.busLineVertexPairs[i][0];
            int w = Data.busLineVertexPairs[i][1];

            double weight = Data.busLineValueList[i];

            busLineMap.setEdge(v, w, weight);
            busLineMap.setEdge(w, v, weight);
        }
    }
}
