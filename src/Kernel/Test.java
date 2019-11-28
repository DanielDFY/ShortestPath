package Kernel;

public class Test {
    public static void main(String[] args) {
        NavigationMap.init();

        // Pedestrian test
        System.out.println("Pedestrian test: A -> A");
        Graph pedestrianMap = NavigationMap.pedestrianMap;
        PathUtils.Result pedestrianResult = PathUtils.shortestPath(pedestrianMap, 'A' - 'A', 'A' - 'A');

        if (null == pedestrianResult) {
            System.out.println("Unreachable");
        } else {
            System.out.println("distance: " + pedestrianResult.dist + "km");
            for (int i = 0; i < pedestrianResult.path.length - 1; ++i) {
                System.out.print(Data.vertexNameList[pedestrianResult.path[i]] + " -> ");
            }
            System.out.println(Data.vertexNameList[pedestrianResult.path[pedestrianResult.path.length - 1]]);
        }

        // Vehicle test
        System.out.println("Vehicle test: A -> X");
        Graph vehicleMap = NavigationMap.vehicleMap;
        PathUtils.Result vehicleResult = PathUtils.shortestPath(vehicleMap, 'A' - 'A', 'X' - 'A');

        if (null == vehicleResult) {
            System.out.println("Unreachable");
        } else {
            System.out.println("distance: " + vehicleResult.dist + "km");
            for (int i = 0; i < vehicleResult.path.length - 1; ++i) {
                System.out.print(Data.vertexNameList[vehicleResult.path[i]] + " -> ");
            }
            System.out.println(Data.vertexNameList[vehicleResult.path[vehicleResult.path.length - 1]]);
        }

        // BusLine test
        System.out.println("BusLine test: F -> T");
        Graph busLineMap = NavigationMap.busLineMap;
        PathUtils.Result busLineResult = PathUtils.shortestPath(busLineMap, 'F' - 'A', 'T' - 'A');
        Graph busMap = NavigationMap.vehicleMap;
        PathUtils.Result busResult = PathUtils.shortestPath(busMap, 'F' - 'A', 'T' - 'A');

        if (null == busLineResult || null == busMap) {
            System.out.println("Unreachable");
        } else {
            System.out.println("distance: " + busLineResult.dist + "min");
            for (int i = 0; i < busResult.path.length - 1; ++i) {
                System.out.print(Data.vertexNameList[busResult.path[i]] + " -> ");
            }
            System.out.println(Data.vertexNameList[busResult.path[busResult.path.length - 1]]);
        }
    }
}
