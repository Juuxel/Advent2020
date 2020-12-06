package juuxel.advent2020.regular

private fun splitByBlanks(args: Array<String>): List<List<String>> {
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

fun main(args: Array<String>) {
    val groups = splitByBlanks(args)

    val part1 = groups.sumBy { group ->
        group.joinToString(separator = "").toSet().size
    }

    val part2 = groups.sumBy { group ->
        ('a'..'z').count { char ->
            group.all { char in it }
        }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}
