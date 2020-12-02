package juuxel.advent2020.regular

private const val TARGET_SUM = 2020

fun main(args: Array<String>) {
    part1(args)
    part2(args)
}

private fun part1(args: Array<String>) {
    val input = args.map(String::toInt)

    for (i in input.indices) {
        for (j in i..input.lastIndex) {
            val a = input[i]
            val b = input[j]
            if (a + b == TARGET_SUM) {
                println(a * b)
                return
            }
        }
    }

    println("No match found!")
}

private fun part2(args: Array<String>) {
    val input = args.map(String::toInt)

    for (i in input.indices) {
        for (j in i..input.lastIndex) {
            for (k in j..input.lastIndex) {
                val a = input[i]
                val b = input[j]
                val c = input[k]
                if (a + b + c == TARGET_SUM) {
                    println(a * b * c)
                    return
                }
            }
        }
    }

    println("No match found!")
}

