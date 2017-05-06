import groovy.transform.ToString

/*
 * Use type long for values as they may not fit into an integer
 * Complexity of this implementation is O(nm)
 * TODO: Optimize the same way as the Go example
 */

@ToString
class Config {
    int listSize
    int nbQueries
}

@ToString
class Query {
    int first
    int last
    long value
}

Closure<Config> readConfig = { String line ->
    def values = line.split(" ");
    return new Config(
            listSize: Integer.parseInt(values[0]),
            nbQueries: Integer.parseInt(values[1])
    )
}

Closure<Query> parseQuery = { String line ->
    def values = line.split(" ");
    return new Query(
            first: Integer.parseInt(values[0]),
            last: Integer.parseInt(values[1]),
            value: Long.parseLong(values[2])
    )
}

Config config
Long[] list
System.in.eachLine { line ->
    if (config == null) {
        config = readConfig(line)
        list = new int[config.listSize]
    } else {
        def query = parseQuery(line)
        IntRange indicesToUpdate = ((query.first - 1)..(query.last - 1))
        indicesToUpdate.each {
            list[it] += query.value
        }
    }
}

println list.max()
