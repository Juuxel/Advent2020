package juuxel.advent2020.regular

private sealed class Instruction(val name: String, val arg: Int) {
    var isExecuted = false

    abstract fun swap(): Instruction
    abstract fun copy(): Instruction

    override fun toString(): String {
        val argStr = if (arg < 0) arg.toString() else "+$arg"
        return "$name $argStr (ex: $isExecuted)"
    }

    class Acc(arg: Int) : Instruction("acc", arg) {
        override fun swap(): Instruction = throw UnsupportedOperationException()
        override fun copy(): Instruction = Acc(arg)
    }

    class Nop(arg: Int) : Instruction("nop", arg) {
        override fun swap(): Instruction = Jmp(arg)
        override fun copy(): Instruction = Nop(arg)
    }

    class Jmp(arg: Int) : Instruction("jmp", arg) {
        override fun swap(): Instruction = Nop(arg)
        override fun copy(): Instruction = Jmp(arg)
    }
}

private fun parse(lines: Array<String>): List<Instruction> =
    lines.map {
        val instruction = it.substring(0, 3)
        val arg = it.substring(4).toInt()

        when (instruction) {
            "nop" -> Instruction.Nop(arg)
            "acc" -> Instruction.Acc(arg)
            "jmp" -> Instruction.Jmp(arg)
            else -> throw IllegalArgumentException("Unknown instruction: $instruction in '$it'")
        }
    }

private fun execute(program: List<Instruction>): Int {
    var accumulator = 0
    var i = 0

    while (i < program.size) {
        val instruction = program[i]
        if (instruction.isExecuted) break

        when (instruction) {
            is Instruction.Nop -> i++
            is Instruction.Acc -> {
                accumulator += instruction.arg
                i++
            }
            is Instruction.Jmp -> i += instruction.arg
        }

        instruction.isExecuted = true
    }

    return accumulator
}

private fun executeAndFix(program: List<Instruction>): Int {
    correction@ for (correctionIndex in program.indices) {
        var accumulator = 0
        val corrected = program[correctionIndex]
        if (corrected is Instruction.Acc) continue

        val newProgram = program.map { it.copy() }.toMutableList()
        newProgram[correctionIndex] = program[correctionIndex].swap()

        var i = 0
        while (i < newProgram.size) {
            val instruction = newProgram[i]
            if (instruction.isExecuted) continue@correction

            when (instruction) {
                is Instruction.Nop -> i++
                is Instruction.Acc -> {
                    accumulator += instruction.arg
                    i++
                }
                is Instruction.Jmp -> i += instruction.arg
            }

            instruction.isExecuted = true
        }

        return accumulator
    }

    throw IllegalStateException("Could not fix :(")
}

fun main(args: Array<String>) {
    val program = parse(args)
    val part1 = execute(program)
    val part2 = executeAndFix(program)

    println("Part 1: $part1")
    println("Part 2: $part2")
}
