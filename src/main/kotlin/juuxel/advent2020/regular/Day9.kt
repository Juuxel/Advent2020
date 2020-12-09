package juuxel.advent2020.regular

private const val PREAMBLE_SIZE = 25

fun main(args: Array<String>) {
    val numbers = args.map(String::toLong)

    val part1 = numbers.firstIndexed { index, num ->
        if (index < PREAMBLE_SIZE) return@firstIndexed false

        val range = (index - PREAMBLE_SIZE) until index
        for (i in range) {
            for (j in range) {
                if (i == j) continue

                if (num == numbers[i] + numbers[j]) return@firstIndexed false
            }
        }

        true
    }

    val part2 = run {
        root@ for (i in numbers.indices) {
            var sum = numbers[i]

            for (j in (i + 1) until numbers.size) {
                sum += numbers[j]

                if (sum > part1) {
                    continue@root
                } else if (sum == part1) {
                    val parts = numbers.slice(i..j)
                    return@run parts.minOrNull()!! + parts.maxOrNull()!!
                }
            }
        }

        throw NoSuchElementException("wat")
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}

private inline fun <T> List<T>.firstIndexed(filter: (index: Int, T) -> Boolean): T {
    for ((index, t) in this.withIndex()) {
        if (filter(index, t)) {
            return t
        }
    }

    throw NoSuchElementException("No element matching the filter")
}
