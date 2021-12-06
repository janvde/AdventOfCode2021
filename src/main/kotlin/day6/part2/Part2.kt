package day6.part2

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val output = readInput()
        .groupingBy { it }
        .eachCount()
        .let { counts -> List(9) { counts[it]?.toLong() ?: 0L } }
        .let { countFish(256, it.toMutableList()) }

    println(output)

}

tailrec fun countFish(runs: Int, fish: MutableList<Long>): Long {
    return if (runs == 0) fish.sum()
    else {
        val dayZero = fish.first()
        Collections.rotate(fish, -1)
        fish[6] += dayZero
        countFish(runs - 1, fish)
    }
}

fun readInput(): List<Int> {
    return File("src/main/kotlin/day6/input.txt").readLines().map { line ->
        line.split(",").map { it.toInt() }
    }.flatten()
}
