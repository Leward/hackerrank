import groovy.transform.ToString

@ToString
class Config {
    int n
    int nbQueries
}

@ToString
class Query {
    int type
    int x
    int y
}

Closure<Config> extractConfig = { String str ->
    def firstLineItems = str.split("\n")[0].split(" ")
    return new Config(n: Integer.parseInt(firstLineItems[0]),
            nbQueries: Integer.parseInt(firstLineItems[1]))
}

Closure<Query> extractQuery = { String line ->
    def items = line.split(" ");
    return new Query(type: Integer.parseInt(items[0]),
            x: Integer.parseInt(items[1]),
            y: Integer.parseInt(items[2]))
}

Closure<List<Query>> extractQueries = { String str, int nbQueries ->
    def lines = str.split("\n")
    List<Query> queries = []
    for (int i = 1; i < lines.size(); i++) {
        queries.add(extractQuery(lines[i]))
    }
    return queries
}

Config config
List<Integer>[] seqList
int lastAns = 0

System.in.eachLine { line ->
    if(config == null) {
        config = extractConfig(line)
        seqList = new List<Integer>[config.n];
        for (int i = 0; i < seqList.size(); i++) {
            seqList[i] = []
        }
    }
    else {
        def query = extractQuery(line)
        int seqIndex = (query.x ^ lastAns) % config.n
        def seq = seqList[seqIndex]
        if(query.type == 1) {
            seq.add(query.y)
        } else {
            lastAns = seq[query.y % seq.size()]
            println lastAns
        }
    }
}
