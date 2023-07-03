class Q1Tests {
    static double averageCost = 0;
    static int carCost = 14999;
    static int dropCost = 6139;
    static double[] costAtEachFloor;
    static int[] numTestsAtEachFloor;
    static int currentNumTests = 0;
    static int currentNumCarsBroken = 0;
    public static void main(String[] args) {
        q1Test();
    }

// ---------------------
// Best Floor By Cost: Floor 0 cost $42276.0 |
// Worst Floor By Cost: Floor 97 cost $115944.0 |
// Best Floor By Number of Tests: Floor 0 required 2 tests |
// Worst Floor By Number of Tests: Floor 98 required 14.0 tests |
// ---------------------

    public static void q1Test() {
        System.out.println("---------------------");
        int numberOfFloors = 100;
        costAtEachFloor = new double[numberOfFloors + 2];
        numTestsAtEachFloor = new int[numberOfFloors + 2];
        System.out.println("Floor" + ",Number of Tests," + "Total Cost");
        for(int i = 0; i <= numberOfFloors + 1; i++) {
            costForFloorQ1(numberOfFloors, i);
        }
        System.out.println("---------------------");
        double lowestCost = 1000000.0;
        String lowestCostData = "";
        double highestCost = 0.0;
        String highestCostData = "";
        for(int targetFloor = 0; targetFloor < costAtEachFloor.length; targetFloor++) {
            if(costAtEachFloor[targetFloor] <= lowestCost) {
                lowestCost = costAtEachFloor[targetFloor];
                lowestCostData = "Floor " + targetFloor + " cost $" + lowestCost + " | ";
            }
            if(costAtEachFloor[targetFloor] >= highestCost) {
                highestCost = costAtEachFloor[targetFloor];
                highestCostData = "Floor " + targetFloor + " cost $" + highestCost + " | ";
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

    public static void costForFloorQ1(int numFloors, int targetFloor) {
        currentNumTests = 0;
        currentNumCarsBroken = 0;
        int currentFloor = 14;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 1;
            while(currentFloor < 14) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 27;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 15;
            while(currentFloor < 27) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 39;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 28;
            while(currentFloor < 39) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 50;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 40;
            while(currentFloor < 50) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 60;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 51;
            while(currentFloor < 60) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 69;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 61;
            while(currentFloor < 69) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 77;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 70;
            while(currentFloor < 77) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 84;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 78;
            while(currentFloor < 84) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 90;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 85;
            while(currentFloor < 90) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 95;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 91;
            while(currentFloor < 95) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
            return;
        }
        currentFloor = 99;
        if(dropCarIsBroken(currentFloor, targetFloor)) {
            currentFloor = 96;
            while(currentFloor < 99) {
                if(dropCarIsBroken(currentFloor, targetFloor)) {
                    getResults(targetFloor);
                    return;
                } else {
                    currentFloor++;
                }
            }
            getResults(targetFloor);
           return;
        }
        currentFloor = 100;
        currentNumTests++;
        if(currentFloor == targetFloor) {
            getResults(targetFloor);
            return;
        }
        currentNumCarsBroken++;
        getResults(targetFloor);
        return;
    }

    public static void getResults(int targetFloor) {
        int totalCost = (currentNumTests*dropCost) + (currentNumCarsBroken*carCost);
        costAtEachFloor[targetFloor] = totalCost;
        numTestsAtEachFloor[targetFloor] = currentNumTests;
        //System.out.println("Floor " + targetFloor + ", Number of Tests: " + currentNumTests + ", Total Cost: " + totalCost);
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
