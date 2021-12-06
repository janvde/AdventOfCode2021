package day6.part1

import java.io.File

fun main(args: Array<String>) {
    val output = countFish(80, readInput())

    println(output)
}

tailrec fun countFish(runs: Int, fish: List<Int>) : Int {
    return if(runs == 0) fish.count()
    else countFish(runs -1, fish.map { if (it > 0) listOf(it - 1) else listOf(6, 8) }.flatten())
}

fun readInput(): List<Int> {
    return File("src/main/kotlin/day6/input.txt").readLines().map { line ->
        line.split(",").map { it.toInt() }
    }.flatten()
}