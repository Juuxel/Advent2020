package juuxel.advent2020.regular

fun splitByBlanks(args: Array<String>): List<List<String>> {
    val result = ArrayList<List<String>>()
    var current = ArrayList<String>()

    fun next() {
        result.add(current)
        current = ArrayList()
    }

    for (line in args) {
        if (line != "") {
            current.add(line)
        } else {
            next()
        }
    }

    next()

    return result
}
