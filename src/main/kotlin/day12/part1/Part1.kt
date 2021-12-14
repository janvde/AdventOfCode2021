package day12.part1

import java.io.File

fun main(args: Array<String>) {
    val adjMap = readInput()

    fun isAllowed(cave : String, visited: Set<String>): Boolean = cave.all { it.isUpperCase() } || cave !in visited

    fun findPaths(cave: String, visited: MutableSet<String>) : List<String> {
        if(cave == "end") return listOf(cave)
        visited.add(cave)
        val paths = adjMap[cave]!!
            .filter { isAllowed(it, visited) }
            .flatMap { findPaths(it, visited) }
            .map { "$cave,$it"}

        visited.remove(cave)

        return paths
    }

    val paths = findPaths("start", mutableSetOf())
    paths.map {
        println(it)
    }
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