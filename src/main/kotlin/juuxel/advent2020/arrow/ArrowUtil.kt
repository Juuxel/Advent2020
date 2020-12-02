package juuxel.advent2020.arrow

object ExitException : RuntimeException()

inline fun exiting(block: () -> Unit) =
    try {
        block()
    } catch (e: ExitException) {
    }
