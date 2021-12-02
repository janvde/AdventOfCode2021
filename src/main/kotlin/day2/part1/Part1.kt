package day2.part1

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()

    val output = input.fold(Pair(0,0)) { acc, movement ->
        when (movement.direction) {
            "forward" -> Pair(acc.first + movement.value, acc.second)
            "up" -> Pair(acc.first, acc.second - movement.value)
            "down" -> Pair(acc.first, acc.second + movement.value)
            else -> acc
        }
    }.let { it.first * it.second }


    println(output)
}


fun readInput(): List<Movement> {
    val result = mutableListOf<Movement>()
    File("src/main/kotlin/day2/input.txt").bufferedReader().forEachLine {
        val elements = it.split(" ")
        result.add(Movement(elements.first(), elements.last().toInt()))
    }
    return result
}

data class Movement(val direction: String, val value: Int)