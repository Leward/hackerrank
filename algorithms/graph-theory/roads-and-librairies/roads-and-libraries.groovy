class World {
    // Adjacency list
    Map<Integer, List<Integer>> G = [:]
    int nbCities
    int nbRoads
    int buildingLibraryCost
    int repairRoadCost

    void addRoad(int cityA, int cityB) {
        if(!G.containsKey(cityA)) {
            G[cityA] = []
        }
        if(!G.containsKey(cityB)) {
            G[cityB] = []
        }
        G[cityA].add(cityB)
        G[cityB].add(cityA)
    }

    long calculateCost() {
        // If its cheaper to build libraries than reparing roads, then build libraries in every cities
        // Otherwise, for each sub graph (interconnected cities) build one library and repair the roads at reparing roads is cheaper
        if(buildingLibraryCost < repairRoadCost || nbRoads == 0) {
            return ((nbCities as long) * buildingLibraryCost)
        }
        Set<Integer> visitedCities = []
        int nbLibrariesToBuild = 0
        int nbRoadsToRepair = 0

        Closure traverseFrom
        traverseFrom = { int city ->
            visitedCities.add(city)
            G[city].each { adjacentCity ->
                if(!visitedCities.contains(adjacentCity)) {
                    nbRoadsToRepair++
                    traverseFrom(adjacentCity)
                }
            }
        }

        G.each {
            if(!visitedCities.contains(it.key)) {
                nbLibrariesToBuild++
                traverseFrom(it.key)
            }
        }
        int nonVisitedCities = nbCities - visitedCities.size() // Non visited cities are not connected to the network, thus we must build a library in those cities
        nbLibrariesToBuild += nonVisitedCities
        return (nbLibrariesToBuild as long) * buildingLibraryCost + (nbRoadsToRepair as long) * repairRoadCost
    }
}

Scanner scanner = new Scanner(System.in);
int nbQueries = nbQueries = scanner.nextInt();
nbQueries.times {
    def world = new World()
    world.nbCities = scanner.nextInt();
    world.nbRoads = scanner.nextInt();
    world.buildingLibraryCost = scanner.nextLong();
    world.repairRoadCost = scanner.nextLong();
    world.nbRoads.times {
        int cityA = scanner.nextInt();
        int cityB = scanner.nextInt();
        world.addRoad(cityA, cityB)
    }
    println world.calculateCost()
}