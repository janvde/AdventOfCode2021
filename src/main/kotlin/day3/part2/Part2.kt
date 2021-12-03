package day3.part2

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()

    val mostCommon = calculateMostCommonValue(input, 0).first().toInt(2)
    val leastCommon = calculateLeastCommonValue(input, 0).first().toInt(2)
    println(mostCommon * leastCommon)
}

fun calculateMostCommonValue(input: List<String>, position: Int): List<String> {
    return if (input.size == 1) input
    else {
        calculateMostCommonValue(findMostCommonValue(input, position), position + 1)
    }
}

fun calculateLeastCommonValue(input: List<String>, position: Int): List<String> {
    return if (input.size == 1) input
    else {
        calculateLeastCommonValue(findLeastCommonValue(input, position), position + 1)
    }
}

fun findMostCommonValue(input: List<String>, position: Int): List<String> {
    return input.groupBy { it[position] == '1' }
        .let {
            if ((it[true]?.size ?: 0) >= (it[false]?.size ?: 0)) {
                it[true]!!
            } else {
                it[false]!!
            }
        }
}

fun findLeastCommonValue(input: List<String>, position: Int): List<String> {
    return input.groupBy { it[position] == '0' }
        .let {
            if ((it[true]?.size ?: 0) > (it[false]?.size ?: 0)) {
                it[false]!!
            } else {
                it[true]!!
            }
        }
}

fun readInput(): List<String> {
    var result = mutableListOf<String>()
    File("src/main/kotlin/day3/input.txt").bufferedReader().forEachLine {
        result.add(it)
    }
    return result
}