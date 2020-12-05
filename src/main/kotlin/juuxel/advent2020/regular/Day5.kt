package juuxel.advent2020.regular

fun main(args: Array<String>) {
    val seatIds = args.map { seat ->
        var row = 0

        for (i in 0..6) {
            val bit = (if (seat[i] == 'F') 0 else 1) shl (6 - i)
            row = row or bit
        }

        var column = 0

        for (i in 7..9) {
            val bit = (if (seat[i] == 'L') 0 else 1) shl (2 - (i - 7))
            column = column or bit
        }

        //println("Seat $seat:\n\tRow: $row\n\tColumn: $column\n\tID: ${row * 8 + column}")

        row * 8 + column
    }

    val min = seatIds.minOrNull()!!
    val max = seatIds.maxOrNull()!!

    val part1 = max
    val part2 = (min..max).find { it !in seatIds }

    println("Part 1: $part1")
    println("Part 2: $part2")
}
