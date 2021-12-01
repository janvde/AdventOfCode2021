package day1.part1

import java.io.File

fun main(args: Array<String>) {
    val input = readInput("src/main/kotlin/day1/input.txt")
    val output = input.foldIndexed(0) { index, acc, value ->
        if (index > 0 && value > input[index - 1]) return@foldIndexed acc + 1
        acc
    }

    println(output)
}


fun readInput(fileName: String): List<Int> {
    var result = mutableListOf<Int>()
    File(fileName).bufferedReader().forEachLine {
        result.add(it.toInt())
    }
    return result
}