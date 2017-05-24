/*
 To allow going thgough big lists, values are sorted so that they are easier to navigate
 A greedy approach is used to find the matching pair (divide search by two at each iteration as list is sorted)
 Use List.subList instead of Groovy list[a..b] notation as sublist only keeps pointers to backing list; whereas
  Groovy construct creates a new array in memoery and it takes more time too (causing timeouts)
 */

//long startTime = System.nanoTime();

int countPairsWithMatchingDifference(List<Integer> values, int difference) {
    values = values.sort()
    int pairs = 0
    for (int i = 0; i < values.size(); i++) {
        if(searchValueMatchingDiff(values, values[i], difference) != -1) {
            pairs++
        }
    }
    return pairs
}

int searchValueMatchingDiff(List<Integer> sortedValues, int a, int difference) {
    // Find so that a - b = difference
    int middleIndex = Math.floorDiv(sortedValues.size(), 2)
    int b = sortedValues[middleIndex]
    if (a - b == difference) {
        return middleIndex
    } else if (sortedValues.size() == 1) {
        return -1
    } else if (a - b < difference) {
        List<Integer> arr = sortedValues.subList(0, middleIndex)
        return searchValueMatchingDiff(arr, a, difference)
    } else {
        List<Integer> arr = sortedValues.subList(middleIndex, sortedValues.size())
        return searchValueMatchingDiff(arr, a, difference)
    }
}

class Config {
    int nbItems
    int difference
}

Config config
List<Integer> values = []
System.in.eachLine {
    def line = it.split(' ')
    if (config == null) {
        config = new Config(nbItems: line[0].toInteger(), difference: line[1].toInteger())
    } else {
        values = line.collect { it.toInteger() }
    }
}

println countPairsWithMatchingDifference(values, config.difference)

//long endTime = System.nanoTime();
//long duration = (endTime - startTime) / 1000000;
//println "Duration: ${duration}ms"
