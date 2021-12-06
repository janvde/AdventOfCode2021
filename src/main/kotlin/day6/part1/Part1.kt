package day6.part1

import java.io.File

fun main(args: Array<String>) {
    val output = (0 until 80).fold(readInput()) { acc, _ ->
        acc.map { if (it > 0) listOf(it - 1) else listOf(6, 8) }.flatten()
    }.count()

    println(output)
}

fun readInput(): List<Int> {
    return File("src/main/kotlin/day6/input.txt").readLines().map { line ->
        line.split(",").map { it.toInt() }
    }.flatten()
}