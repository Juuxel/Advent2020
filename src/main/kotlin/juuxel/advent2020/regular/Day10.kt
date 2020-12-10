package juuxel.advent2020.regular

fun main(args: Array<String>) {
    val joltages = args.map(String::toInt)

    val part1 = part1(joltages)

    println("Part 1: $part1")
}

private fun part1(joltages: List<Int>): Int {
    var one = 0
    var three = 0

    val sorted = joltages.toMutableList()
    sorted.sort()
    sorted.add(0, 0) // The seat joltage
    sorted.add(sorted.size, sorted.last() + 3) // The phone joltage

    for (i in 1..sorted.lastIndex) {
        val current = sorted[i]
        val previous = sorted[i - 1]

        when (current - previous) {
            1 -> one++
            3 -> three++
        }
    }

    return one * three
}
