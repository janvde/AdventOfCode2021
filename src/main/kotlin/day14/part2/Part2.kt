package day14.part2

import java.io.File

fun main(args: Array<String>) {
    val (template, insertions) = readInput()

    var polymerMap = template
        .windowed(2,1)
        .groupBy { it }.toMap()
        .mapValues { it.value.size.toLong() }
        .toMutableMap()

    val charCount = template
        .toSet()
        .associateBy({ it.toString() }, { template.count { char -> char == it }.toLong() })
        .toMutableMap()

    repeat(40) {
        val pairsCounter = mutableMapOf<String, Long>()
        polymerMap.keys.forEach { pair ->
            val insertion = insertions[pair]
            val currentCount = polymerMap.getOrDefault(pair, 0)
            val first = "${pair.first()}$insertion"
            val second = "$insertion${pair.last()}"
            pairsCounter.apply {
                this[first] = getOrDefault(first, 0) + currentCount
                this[second] = getOrDefault(second, 0) + currentCount
            }

            charCount[insertion!!] = charCount.getOrDefault(insertion, 0L).plus(currentCount)
        }
        polymerMap = pairsCounter
    }

    println(charCount.maxOf { it.value } - charCount.minOf { it.value })
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