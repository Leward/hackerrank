class Config {
    int nbValues
    int nbLeftRotations
}

def leftRotate = { List<Integer> values ->
    values[1..(values.size() - 1)] + values[0]
}

def multiLeftRotate = {List<Integer> values, int nbLeftRotations ->
    values[nbLeftRotations..(values.size() - 1)] + values[0..(nbLeftRotations-1)]
}

Config config
List<Integer> values
System.in.eachLine {
    def parts = it.split(' ')
    if(config == null) {
        config = new Config(
                nbValues: parts[0].toInteger(),
                nbLeftRotations: parts[1].toInteger()
        )
    } else {
        values = parts.collect { it.toInteger() }
    }
}

// Do N left rotations
//config.nbLeftRotations.times {
//    values = leftRotate(values)
//}

println multiLeftRotate(values, config.nbLeftRotations).join(' ')