package day7.part2

import java.io.File
import kotlin.math.abs
import kotlin.math.floor

fun main(args: Array<String>) {
    val input = readInput()
    val mean = input.average()

    val output = input.map {
        calculateFuelUsage(it, floor(mean).toInt()) //would be better to check both floor and ceil
    }.sum()

    println(output)
}

fun calculateFuelUsage(i : Int, mean: Int) : Int {
    return (1 .. abs(i-mean)).sum()
}

fun readInput(): List<Int> {
    return File("src/main/kotlin/day7/input.txt").readLines().map { line ->
        line.split(",").map { it.toInt() }
    }.flatten()
}