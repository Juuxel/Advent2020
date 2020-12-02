package juuxel.advent2020.misc

import juuxel.leafthrough.StringReader

fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    for (line in args) {
        val reader = StringReader(line)
        val lower = reader.readUntil('-').toInt()
        reader.expect('-')
        val upper = reader.readUntil(' ').toInt()
        reader.expect(' ')
        val char = reader.next()
        reader.expect(": ")
        val password = reader.getRemaining()

        // Part 1
        val count = password.count { it == char }
        if (count in lower..upper) {
            part1++
        }

        // Part 2
        val first = password[lower - 1]
        val second = password[upper - 1]
        if (first != second && (first == char || second == char)) {
            part2++
        }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}
