package day1.part2

import java.io.File

fun main(args: Array<String>) {
    val input = readInput("src/main/kotlin/day1/input.txt")

    val result = input
        .windowed(3,1)
        .map { it.sum() }
        .windowed(2,1)
        .count { it.first() < it.last() }

    println(result)
}


fun readInput(fileName: String): List<Int> {
    var result = mutableListOf<Int>()
    File(fileName).bufferedReader().forEachLine {
        result.add(it.toInt())
    }
    return result
}