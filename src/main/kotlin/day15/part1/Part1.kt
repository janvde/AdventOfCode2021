package day15.part1

import java.io.File
import java.util.*
import kotlin.Comparator

fun main(args: Array<String>) {

    fun List<List<Int>>.getValue(point: Point): Int? = this.getOrNull(point.y)?.getOrNull(point.x)

    fun List<List<Int>>.getAdjacentPoints(point: Point): List<Point> {
        return listOfNotNull(
            if (point.x > 0) Point(point.x - 1, point.y) else null,
            if (first().size - 1 > point.x) Point(point.x + 1, point.y) else null,
            if (point.y > 0) Point(point.x, point.y - 1) else null,
            if (size - 1 > point.y) Point(point.x, point.y + 1) else null
        )
    }

    fun dijkstra(graph: List<List<Int>>, source: Point): Int {
        val dist = hashMapOf<Point, Int>()
        val q = PriorityQueue<Pair<Point, Int>>(compareBy { it.second })

        dist[source] = 0
        q.add(Pair(source, 0))

        while (q.isNotEmpty()) {
            val (point, _) = q.remove()

            graph.getAdjacentPoints(point).forEach { adj ->
                val alt = dist.getOrDefault(point, Int.MAX_VALUE) + graph.getValue(adj)!!
                if(alt < dist.getOrDefault(adj, Int.MAX_VALUE)){
                    dist[adj] = alt
                    q.add(Pair(adj, alt))
                }
            }
        }

        return dist[Point(graph.first().size-1, graph.size-1)]!!
    }


    val input = readInput()
    val min = dijkstra(input, Point(0,0))
    println(min)
}

data class Point(val x: Int, val y: Int)

fun readInput(): List<List<Int>> = File("src/main/kotlin/day15/input.txt")
    .readLines()
    .map {
        it.map { it.digitToInt() }
    }

