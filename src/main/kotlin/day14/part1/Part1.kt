package day14.part1

import java.io.File

fun main(args: Array<String>) {
    val (template, insertions) = day14.part2.readInput()

    fun List<String>.merge(): String = fold("") { acc, s -> acc.dropLast(1) + s }

    fun insert(pair: String, run: Int) : String {
        if(run == 0 ) return pair
        return "${pair.first()}${insertions[pair]}${pair.last()}"
            .windowed(2,1)
            .map {
                insert(it, run -1) }
            .merge()
    }

    template.windowed(2,1).map { insert(it, 10) }.merge()
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