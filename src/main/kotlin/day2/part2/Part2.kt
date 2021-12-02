package day2.part2

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()

    val output = input.fold(Submarine(0,0,0)) { acc, movement ->
        when (movement.direction) {
            "forward" -> Submarine(acc.horizontal + movement.value, acc.depth + (acc.aim * movement.value), acc.aim)
            "up" -> Submarine(acc.horizontal, acc.depth, acc.aim - movement.value)
            "down" -> Submarine(acc.horizontal, acc.depth, acc.aim + movement.value)
            else -> acc
        }
    }.let { it.horizontal * it.depth }


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

data class Submarine(val horizontal: Int, val depth: Int, val aim: Int)