def isOrderedSequence = { List<Integer> it ->
    for (int i = 0; i < it.size() - 1; i++) {
        if(it[i] >= it[i + 1]) {
            return false
        }
    }
    return true
}

def s = []
System.in.eachLine { s.add(it.toInteger()) }
println s.subsequences() // Groovy magic is cool but not optimized
        .findAll(isOrderedSequence)
        .max { it.size() }
        .size()

