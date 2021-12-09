package day8.part2

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()
    val output = input.map {
        val sorted = it.signalPatterns.map { sortLetters(it) }.sortedBy { it.length }

        val numbers = mutableMapOf<Int, String>().apply {
            put(1, sorted[0])
            put(4, sorted[2])
            put(7, sorted[1])
            put(8, sorted[9])
        }

        sorted.filter { it.count() == 6 }.forEach {
            if ((it difference numbers[7]!!) == 4) {
                numbers[6] = it
            } else if ((it union numbers[4]!!) == it.length) {
                numbers[9] = it
            } else {
                numbers[0] = it
            }
        }
        sorted.filter { it.count() == 5 }.forEach {
            if (it union numbers[7]!! == it.length){
                numbers[3] = it
            } else if((it union  numbers[6]!!) == 6){
                numbers[5] = it
            } else {
                numbers[2] = it
            }
        }

        val values = numbers.entries.associateBy({it.value} ) {it.key}

        it.output.map { sortLetters(it) }.map {
            values[it]
        }.joinToString("").toInt()
    }

    println(output.sum())

}

fun sortLetters(input: String) : String  =  input.toList().sorted().joinToString("")

infix fun String.difference(other: String): Int = (this.toSet() - other.toSet()).size

infix fun String.union(other: String): Int = (this.toSet() + other.toSet()).size

fun readInput(): List<Note> {
    return File("src/main/kotlin/day8/input.txt").readLines().map { line ->
        val parts = line.split(" | ")
        Note(parts.first().split(" "), parts.last().split(" "))
    }
}

data class Note(val signalPatterns: List<String>, val output: List<String>)