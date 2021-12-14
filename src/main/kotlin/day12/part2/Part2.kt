package day12.part2

import java.io.File

fun main(args: Array<String>) {
    val adjMap = readInput()

    fun String.isUpperCase(): Boolean = all { it.isUpperCase() }

    fun List<String>.hasVisitedSmallTwice(): Boolean =
        filterNot { it.isUpperCase() }.groupBy { it }.none { it.value.size == 2 }

    fun isAllowed(cave: String, path: List<String>): Boolean = cave.isUpperCase()
            || cave !in path
            || path.hasVisitedSmallTwice()

    fun findPaths(path: List<String>): List<List<String>> {
        val current = path.last()
        if (current == "end") return listOf(path)

        return adjMap[current]!!
            .filterNot { it == "start" }
            .filter { isAllowed(it, path) }
            .flatMap {
                findPaths(path + it)
            }
    }

    val paths = findPaths(listOf("start"))
    println(paths.count())
}

fun readInput(): Map<String, List<String>> {
    val adjMap = mutableMapOf<String, MutableList<String>>()
    File("src/main/kotlin/day12/input.txt").readLines()
        .forEach {
            val pair = it.split("-")
            adjMap.getOrPut(pair.first()) { mutableListOf() }.add(pair.last())
            adjMap.getOrPut(pair.last()) { mutableListOf() }.add(pair.first())
        }

    return adjMap

}