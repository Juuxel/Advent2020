package juuxel.advent2020.regular

fun main(args: Array<String>) {
    val grid = args.map { it.map(Square::of) }

    fun trees(dx: Int, dy: Int): Int {
        var current = 0
        var y = 0

        while (y < grid.size) {
            val row = grid[y]
            val x = (dx * y / dy) % row.size

            if (row[x] == Square.TREE) {
                current++
            }

            y += dy
        }

        return current
    }

    // Part 1
    val part1 = trees(3, 1)

    // Part 2
    val slopes = listOf(1 to 1, 5 to 1, 7 to 1, 1 to 2)
    val part2 = part1 * slopes.fold(1) { acc, (dx, dy) -> acc * trees(dx, dy) }

    println(part1)
    println(part2)
}

private enum class Square {
    OPEN,
    TREE;

    companion object {
        fun of(c: Char) =
            when (c) {
                '.' -> OPEN
                '#' -> TREE
                else -> error("Unknown char: $c")
            }
    }
}
