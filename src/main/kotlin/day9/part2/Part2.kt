package day9.part2

import java.io.File


data class Point(val x: Int, val y: Int)

fun main(args: Array<String>) {
    val input = readInput()

    fun getDepthAt(point: Point): Int? = input.getOrNull(point.y)?.getOrNull(point.x)

    fun getAdjacentPoints(point: Point): List<Point> {
        return listOfNotNull(
            if (point.x > 0) Point(point.x - 1, point.y) else null,
            if (input.first().size - 1 > point.x) Point(point.x + 1, point.y) else null,
            if (point.y > 0) Point(point.x, point.y - 1) else null,
            if (input.size - 1 > point.y) Point(point.x, point.y + 1) else null
        )
    }

    fun getAdjacentDepths(point: Point): List<Int> = getAdjacentPoints(point).mapNotNull { getDepthAt(it) }

    fun isLowpoint(point: Point): Boolean {
        return getAdjacentDepths(point).minOf { it } > getDepthAt(point)!!
    }

    fun findBasinPoints(point: Point): Set<Point> {
        val adjacent = getAdjacentPoints(point).filter { getDepthAt(it)!! > getDepthAt(point)!! && getDepthAt(it)!! < 9 }.toSet()
        return adjacent + adjacent.map { findBasinPoints(it) }.flatten()
    }


    val basins = mutableListOf<Point>()
    input.forEachIndexed { yindex, ylist ->
        ylist.forEachIndexed { xindex, i ->
            if (isLowpoint(Point(xindex, yindex))) basins.add(Point(xindex, yindex))
        }
    }

    val output = basins.map { 1 +  findBasinPoints(it).toSet().size}.sorted().takeLast(3).reduce { a, i ->  a*i}

    println(output)

}

fun readInput(): List<List<Int>> {
    return File("src/main/kotlin/day9/input.txt").readLines().map { line ->
        line.toList().map { it.digitToInt() }
    }
}
