package juuxel.advent2020.regular

fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    for (line in args) {
        val dash = line.indexOf('-')
        val firstSpace = line.indexOf(' ')
        val secondSpace = firstSpace + 3

        val lower = line.substring(0, dash).toInt()
        val upper = line.substring(dash + 1, firstSpace).toInt()
        val char = line[firstSpace + 1]
        val password = line.substring(secondSpace + 1)
        val count = password.count { it == char }

        if (count in lower..upper) {
            part1++
        }

        val first = password[lower - 1]
        val second = password[upper - 1]
        if (first != second && (first == char || second == char)) {
            part2++
        }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}
