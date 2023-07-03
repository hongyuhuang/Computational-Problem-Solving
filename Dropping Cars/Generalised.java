class Generalised {
    static boolean[] floorsTested;
    static double averageCost = 0;
    static int carCost = 14999;
    static int dropCost = 6139;
    static double[] costAtEachFloor;
    static int[] numTestsAtEachFloor;
    static int currentNumTests = 0;
    static int currentNumCarsBroken = 0;
    public static void main(String[] args) {
        generalizedTest();
    }

    public static void generalizedTest() {
        System.out.println("---------------------");
        int numberOfFloors = 92;
        costAtEachFloor = new double[numberOfFloors + 2];
        numTestsAtEachFloor = new int[numberOfFloors + 2];
        System.out.println("Floor" + ",Number of Tests," + "Total Cost");
        for(int targetFloor = 0; targetFloor <= numberOfFloors + 1; targetFloor++) {
            costForFloorGeneralisedSearch(numberOfFloors, targetFloor);
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

    public static void costForFloorGeneralisedSearch(int numFloors, int targetFloor) {
        currentNumTests = 0;
        currentNumCarsBroken = 0;
        int currentFloor = 0;
        int prevDropHeight = 0;
        int currentDropHeight = 0;
        while(currentFloor <= numFloors){  
            currentDropHeight += (int) Math.ceil(Math.sqrt(numFloors - prevDropHeight));
            currentFloor = prevDropHeight + 1;
            if (dropCarIsBroken(currentDropHeight, targetFloor)){ 
                while(currentFloor < currentDropHeight) {
                    if (dropCarIsBroken(currentFloor, targetFloor)) {
                        getResults(targetFloor, " ");
                        return;
                    }
                    currentFloor++;
                }
                getResults(targetFloor, " ");
                return;
            }
            prevDropHeight = currentDropHeight;
        } 
        currentFloor = 100;
        currentNumTests++;
        if(currentFloor == targetFloor) {
            getResults(targetFloor, "");
            return;
        }
        currentNumCarsBroken++;
        getResults(targetFloor, "");
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
        currentNumTests++;
        if(currentFloor > targetFloor) {
            currentNumCarsBroken++;
            return true;
        }
        return false;
    }

}
