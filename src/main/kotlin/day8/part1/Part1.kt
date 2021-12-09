package day8.part1

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()
    val output = input.flatMap { it.output}.count { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
    println(output)

}

fun readInput(): List<Note> {
    return File("src/main/kotlin/day8/input.txt").readLines().map { line ->
        val parts = line.split(" | ")
        Note(parts.first().split(" "), parts.last().split(" "))
    }
}

data class Note(val signalPatterns: List<String>, val output: List<String>)