import java.util.*;
import java.util.function.Function;

public class Solution {

    static class World {

        final int nbCities;
        final int nbRoads;
        final long libraryCost;
        final long roadCost;
        final List<List<Integer>> G;

        World(int nbCities, int nbRoads, long libraryCost, long roadCost) {
            this.nbCities = nbCities;
            this.nbRoads = nbRoads;
            this.libraryCost = libraryCost;
            this.roadCost = roadCost;
            G = new ArrayList<>(nbCities);
            for (int i = 0; i < nbCities; i++) {
                G.add(i, new ArrayList<>());
            }
        }

        void addRoad(int cityA, int cityB) {
            G.get(cityA - 1).add(cityB - 1);
            G.get(cityB - 1).add(cityA - 1);
        }

        long computeCost() {
            if (roadCost > libraryCost) {
                return libraryCost * nbCities;
            }
            int librariesToBuild = 0;
            int roadsToBuild = 0;
            Set<Integer> visited = new HashSet<>();

            Function<Integer, Integer> traverseFrom = startCity -> {
                int tmpRoadsToBuild = 0;
                Stack<Integer> stack = new Stack<>();
                stack.push(startCity);
                visited.add(startCity);
                while (!stack.isEmpty()) {
                    int city = stack.pop();
                    for (int adjacentCity : G.get(city)) {
                        if (!visited.contains(adjacentCity)) {
                            tmpRoadsToBuild++;
                            visited.add(adjacentCity);
                            stack.push(adjacentCity);
                        }
                    }
                }
                return tmpRoadsToBuild;
            };

            for (int i = 0; i < nbCities; i++) {
                if (!visited.contains(i)) {
                    librariesToBuild++;
                    roadsToBuild += traverseFrom.apply(i);
                }
            }
            return librariesToBuild * libraryCost + roadsToBuild * roadCost;
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for (int a0 = 0; a0 < q; a0++) {
            int nbCities = in.nextInt();
            int nbRoads = in.nextInt();
            long libraryCoast = in.nextLong();
            long roadCost = in.nextLong();
            World world = new World(nbCities, nbRoads, libraryCoast, roadCost);
            List<List<Integer>> lists = new ArrayList<>(nbCities);
            for (int i = 0; i < nbCities; i++) {
                lists.add(i, new LinkedList<>());
            }

            for (int a1 = 0; a1 < nbRoads; a1++) {
                int city_1 = in.nextInt();
                int city_2 = in.nextInt();
                world.addRoad(city_1, city_2);
            }

            System.out.println(world.computeCost());
        }
    }
}
