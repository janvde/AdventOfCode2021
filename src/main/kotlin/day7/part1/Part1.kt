package day7.part1

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    //93006334
    val input = readInput()
    val median = median(input)

    val output = input.sumOf { abs(median - it) }

    println(output)
}

fun median(numbers: List<Int>) = numbers.sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }

fun readInput(): List<Int> {
    return File("src/main/kotlin/day7/input.txt").readLines().map { line ->
        line.split(",").map { it.toInt() }
    }.flatten()
}