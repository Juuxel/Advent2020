package juuxel.advent2020.regular

private data class BagColour(val name: String, val children: Map<String, Int>) {
    inline fun contains(colour: String, childChecker: (name: String) -> Boolean): Boolean =
        colour in children || children.any { (name, _) -> childChecker(name) }

    fun getChildCount(recurser: (name: String) -> Int): Int =
        children.values.sum() + children.map { (name, count) -> count * recurser(name) }.sum()
}

private class CheckerContainer {
    lateinit var containsShinyGold: (String) -> Boolean
    lateinit var getChildCount: (String) -> Int
}

fun main(args: Array<String>) {
    val colours: MutableMap<String, BagColour> = HashMap()
    val containsShinyGold: MutableMap<BagColour, Boolean> = HashMap()
    val childCounts: MutableMap<BagColour, Int> = HashMap()

    for (line in args) {
        val data = line.split(' ')
        var i = 0
        val name = "${data[i++]} ${data[i++]}"

        // 'bags contain'
        i += 2

        val children = HashMap<String, Int>()
        while (i < data.size) {
            val count = data[i++]
            if (count == "no") {
                break
            }

            val child = "${data[i++]} ${data[i++]}"
            children[child] = count.toInt()
            i++ // 'bag' or 'bags' + punctuations
        }

        colours[name] = BagColour(name, children)
    }

    val container = CheckerContainer()
    container.containsShinyGold = { name: String ->
        val colour = colours[name]!!
        containsShinyGold.getOrPut(colour) {
            colour.contains("shiny gold", container.containsShinyGold)
        }
    }
    container.getChildCount = { name: String ->
        val colour = colours[name]!!
        childCounts.getOrPut(colour) {
            colour.getChildCount(container.getChildCount)
        }
    }

    val part1 = colours.keys.sumBy {
        if (container.containsShinyGold(it)) 1 else 0
    }

    val part2 = container.getChildCount("shiny gold")

    println("Part 1: $part1")
    println("Part 2: $part2")
}
