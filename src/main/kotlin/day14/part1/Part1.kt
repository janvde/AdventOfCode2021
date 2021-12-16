package day14.part1

import java.io.File

fun main(args: Array<String>) {
    val (template, insertions) = readInput()

    fun List<String>.merge(): String = fold("") { acc, s -> acc.dropLast(1) + s }

    (1..10)
        .fold(template) { acc, i ->
            acc.windowed(2, 1)
                .map { "${it.first()}${insertions[it]}${it.last()}" }
                .merge()
        }
        .toList()
        .groupingBy { it }
        .eachCount().values
        .let { println(it.maxOf { it } - it.minOf { it }) }

}

fun readInput(): Pair<String, Map<String, String>> {
    val lines = File("src/main/kotlin/day14/input.txt").readLines()
    return Pair(
        lines.first(),
        lines
            .drop(2)
            .map { it.split(" -> ") }
            .associateBy({ it.first() }, { it.last() })
    )
}