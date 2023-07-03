import java.util.Arrays;

class Q2Tests {
    static boolean[] floorsTested;
    static double averageCost = 0;
    static int carCost = 14999;
    static int dropCost = 6139;
    static double[] costAtEachFloor;
    static int[] numTestsAtEachFloor;
    static int currentNumTests = 0;
    static int currentNumCarsBroken = 0;
    public static void main(String[] args) {
        q2Test();
    }

    public static void q2Test() {
        System.out.println("---------------------");
        int numberOfFloors = 100;
        floorsTested = new boolean[100 + 2];
        costAtEachFloor = new double[numberOfFloors + 2];
        numTestsAtEachFloor = new int[numberOfFloors + 2];
        System.out.println("Floor" + ",Number of Tests," + "Total Cost");
        for(int targetFloor = 0; targetFloor <= numberOfFloors + 1; targetFloor++) {
            costForFloorBinarySearch(numberOfFloors, targetFloor);
        }
        System.out.println("---------------------");
        double lowestCost = 1000000.0;
        String lowestCostData = "";
        double highestCost = 0.0;
        String highestCostData = "";
        for(int i = 0; i < costAtEachFloor.length; i++) {
            if(costAtEachFloor[i] <= lowestCost) {
                lowestCost = costAtEachFloor[i];
                lowestCostData = "Floor " + i + " cost $" + lowestCost + " | ";
            }
            if(costAtEachFloor[i] >= highestCost) {
                highestCost = costAtEachFloor[i];
                highestCostData = "Floor " + i + " cost $" + highestCost + " | ";
            }
        } 

        int lowestTests = 1000;
        String lowestTestsData = "";
        double highestTests = 0;
        String highestTestsData = "";

        for(int i = 0; i < numTestsAtEachFloor.length; i++) {
            if(numTestsAtEachFloor[i] <= lowestTests) {
                lowestTests = numTestsAtEachFloor[i];
                lowestTestsData = "Floor " + i + " required " + lowestTests + " tests | ";
            }
            if(numTestsAtEachFloor[i] >= highestTests) {
                highestTests = numTestsAtEachFloor[i];
                highestTestsData = "Floor " + i + " required " + highestTests + " tests | ";
            }
        } 

        System.out.println("Best Floors By Cost: " + lowestCostData);
        System.out.println("Worst Floors By Cost: " + highestCostData);
        System.out.println("Best Floors By Number of Tests: " + lowestTestsData);
        System.out.println("Worst Floors By Number of Tests: " + highestTestsData);
        System.out.println("---------------------");
    }

    public static void costForFloorBinarySearch(int numFloors, int targetFloor) {
        Arrays.fill(floorsTested, false);
        currentNumTests = 0;
        currentNumCarsBroken = 0;
        int first = 1;
        int last = numFloors;
        int currentFloor = 0;
        while(first <= last){  
            currentFloor = (int) Math.ceil((double)(first + last)/2);
            if (!dropCarIsBroken(currentFloor, targetFloor)){ 
                if(currentFloor == 1) {
                    getResults(targetFloor, "Case O 1: Floor 1, Didn't Break");
                    return;
                }
                // if(currentFloor == numFloors) {
                //     getResults(targetFloor, "Case O 100: Floor 100, Didn't Break");
                //     return;
                // }  
                first = currentFloor + 1;
            } else { 
                if(currentFloor == 1) {
                    getResults(targetFloor, "Case X 1: Floor 1, Broke");
                    return;
                } else {
                    last = currentFloor - 1;   
                }
            }
            if(floorsTested[currentFloor - 1] && floorsTested[currentFloor + 1]) {
                getResults(targetFloor, "Case 1: Adjacent Floors Tested: " + floorsTested[currentFloor - 1] + " , " + floorsTested[currentFloor] + " , " + floorsTested[currentFloor + 1]);
                return;
            }
        }  
        dropCarIsBroken(currentFloor, targetFloor);
        getResults(targetFloor, "Case 2: Target Not Found");
        return;
    }

    public static void getResults(int targetFloor, String caseString) {
        int totalCost = (currentNumTests*dropCost) + (currentNumCarsBroken*carCost);
        costAtEachFloor[targetFloor] = totalCost;
        numTestsAtEachFloor[targetFloor] = currentNumTests;
        //System.out.println("Floor " + targetFloor + ", Number of Tests: " + currentNumTests + ", Total Cost: " + totalCost);
        //System.out.println(caseString);
        System.out.println(targetFloor + " " + currentNumTests + " $" + String.format("%,d", totalCost));
    }

    public static boolean dropCarIsBroken(int currentFloor, int targetFloor) {
        floorsTested[currentFloor] = true;
        currentNumTests++;
        if(currentFloor > targetFloor) {
            currentNumCarsBroken++;
            return true;
        }
        return false;
    }

}
