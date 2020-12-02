package juuxel.advent2020.arrow

import arrow.core.ListK
import arrow.core.extensions.fx
import arrow.core.k
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe

fun nonSuspend(args: Array<String>) = unsafe { runBlocking { IO { main(args) } } }

suspend fun main(args: Array<String>) = exiting {
    val input = args.map(String::toInt).k()
    ListK.fx {
        val a = input.bind()
        val b = input.bind()
        val c = input.bind()

        if (a + b + c == 2020) {
            println("Found solution! ${a * b * c}")
            throw ExitException
        }
    }
}
