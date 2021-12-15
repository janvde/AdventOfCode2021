package day13.part2

import java.io.File

fun main(args: Array<String>) {
    fun Int.foldValue(value: Int): Int = if (this <= value) this else value - (this - value)

    fun Set<Dot>.fold(instruction: Instruction): Set<Dot> =
        if (instruction.axis == 'x') map { Dot(it.x.foldValue(instruction.value), it.y) }.toSet()
        else map { Dot(it.x, it.y.foldValue(instruction.value)) }.toSet()

    fun Set<Dot>.convertString(): String = (0..maxOf { it.y }).map { yValue ->
        (0..maxOf { it.x }).joinToString("") { xValue ->
            if (contains(Dot(xValue, yValue))) "█" else " "
        }
    }.joinToString("\n")


    val (dots, instructions) = readInput()

    val output = instructions.fold(dots) { acc, instruction ->
        acc.fold(instruction)
    }

    println(output.convertString())
}

data class Dot(val x: Int, val y: Int)
data class Instruction(val axis: Char, val value: Int)

fun readInput(): Pair<Set<Dot>, List<Instruction>> {
    return File("src/main/kotlin/day13/input.txt").readLines()
        .filterNot { it.isEmpty() }
        .partition { !it.startsWith("fold along") }
        .let { (dots, instructions) ->
            Pair(dots.map {
                val splitted = it.split(",")
                Dot(splitted.first().toInt(), splitted.last().toInt())
            }.toSet(),
                instructions.map {
                    val splitted = it.split("=")
                    Instruction(splitted.first().last(), splitted.last().toInt())
                })
        }
}