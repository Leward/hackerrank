import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['id'])
@Sortable(includes = ['cost'])
class Flavor {
    int id
    int cost

    @Override
    String toString() {
        return "Flavor[id=${id}, cost=${cost}]"
    }
}

@ToString
class Trip {
    int money
    List<Flavor> availableFlavors // sorted by cost, TODO: use sorted datastructure

    Trip(int money, List<Flavor> availableFlavors) {
        this.money = money
        this.availableFlavors = availableFlavors.findAll {it.cost < money }.sort()
    }

    Tuple2<Flavor, Flavor> pickTwoFlavors() {
        for (int i = 0; i < availableFlavors.size(); i++) {
            def maybeCombination = findAnotherFlavorMatchingBudget(availableFlavors[i])
            if(maybeCombination.isPresent()) {
                return new Tuple2<Flavor, Flavor>(availableFlavors[i], maybeCombination.get())
            }
        }
        throw new IllegalStateException("No flavor found")
    }

    private Optional<Flavor> findAnotherFlavorMatchingBudget(Flavor flavor1) {
        return binarySearch(availableFlavors.findAll { it.id != flavor1.id }, money - flavor1.cost)
    }

    private Optional<Flavor> binarySearch(List<Flavor> flavors, int target) {
        if(flavors.size() == 0) {
            return Optional.empty()
        }
        int middle = flavors.size().intdiv(2)
        if(flavors[middle].cost == target) {
            return Optional.of(flavors[middle])
        }
        if(flavors.size() == 1) {
            return Optional.empty()
        }
        if(flavors[middle].cost > target) {
            return binarySearch(flavors.subList(0, middle), target)
        } else {
            return binarySearch(flavors.subList(middle + 1, flavors.size()), target)
        }
    }
}

Closure<Trip> oneTrip = { List<String> lines ->
    def money = Integer.parseInt(lines[0])
    List<Flavor> flavors = []
    lines[2].split(' ').eachWithIndex { String cost, int i ->
        flavors.add(new Flavor(id: i + 1, cost: Integer.parseInt(cost)))
    }
    return new Trip(money, flavors)
}

// Read problem
def readLines = System.in.readLines()
def nTrips = Integer.parseInt(readLines[0])
List<Trip> trips = []
nTrips.times {
    def start = 1 + (it * 3)
    def end = start + 2
    def trip = oneTrip(readLines.subList(start, end + 1))
    trips.add(trip)
}

// Solve trips
trips.each { trip ->
    def flavors = trip.pickTwoFlavors()
    if(flavors.first.id > flavors.second.id) {
        flavors = new Tuple2<Flavor, Flavor>(flavors.second, flavors.first)
    }
    println flavors.collect { it.id }.join(" ")
}


