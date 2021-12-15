package day13.part1

import java.io.File

fun main(args: Array<String>) {

    fun Set<Dot>.foldX(value: Int): Set<Dot> = map {
        if (it.x <= value) it else Dot(value - (it.x - value), it.y)
    }.toSet()

    fun Set<Dot>.foldY(value: Int): Set<Dot> = map {
        if (it.y <= value) it else Dot(it.x, value - (it.y - value))
    }.toSet()

    fun Set<Dot>.fold(instruction: Instruction): Set<Dot> =
        if (instruction.axis == 'x') foldX(instruction.value)
        else foldY(instruction.value)


    val input = readInput()
    val output = input.first.fold(input.second.first())

    println(output)
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