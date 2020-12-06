package juuxel.advent2020.regular

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
