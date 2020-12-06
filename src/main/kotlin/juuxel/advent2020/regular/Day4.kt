package juuxel.advent2020.regular

private val HCL_PATTERN = Regex("^#[0-9a-f]{6}$")
private val ECL_VALUES = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
private val PID_PATTERN = Regex("^[0-9]{9}$")

private val ALL_FIELDS = setOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
private val REQUIRED_FIELDS: Map<String, (String) -> Boolean> = (ALL_FIELDS - "cid").associateWith { field ->
    fun case(validator: (String) -> Boolean) = validator

    when (field) {
        "byr" -> case { it.toInt() in 1920..2002 }
        "iyr" -> case { it.toInt() in 2010..2020 }
        "eyr" -> case { it.toInt() in 2020..2030 }
        "hgt" -> case {
            when {
                it.endsWith("cm") -> it.removeSuffix("cm").toInt() in 150..193
                it.endsWith("in") -> it.removeSuffix("in").toInt() in 59..76
                else -> false
            }
        }
        "hcl" -> case { HCL_PATTERN.matches(it) }
        "ecl" -> case { it in ECL_VALUES }
        "pid" -> case { PID_PATTERN.matches(it) }
        else -> case { false }
    }
}

fun main(args: Array<String>) {
    var part1 = 0
    var part2 = 0

    for (passport in splitByBlanks(args)) {
        val fields = passport.joinToString(separator = " ").splitToSequence(' ')
            .map { it.split(':') }
            .associate { (key, value) -> key to value }

        if (REQUIRED_FIELDS.keys.all { it in fields }) {
            part1++

            if (REQUIRED_FIELDS.all { (key, validator) -> validator(fields[key]!!) }) {
                part2++
            }
        }
    }

    println("Part 1: $part1")
    println("Part 2: $part2")
}
