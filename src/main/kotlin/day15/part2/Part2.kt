package day15.part2

import java.io.File
import java.util.*

fun main(args: Array<String>) {

    fun List<List<Int>>.scaleMap(times: Int) : List<List<Int>> {
        val scaledX = map { row ->
                (1 until  times).fold(row) { acc, i ->
                    acc + acc.takeLast(row.size).map { if(it == 9) 1 else it+1 }
                }
            }
        return (1 until times).fold(scaledX) { acc, i ->
            acc + acc.takeLast(scaledX.size).map { it.map { if(it == 9) 1 else it+1 } }
        }
    }
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
                if (alt < dist.getOrDefault(adj, Int.MAX_VALUE)) {
                    dist[adj] = alt
                    q.add(Pair(adj, alt))
                }
            }
        }

        return dist[Point(graph.first().size - 1, graph.size - 1)]!!
    }

    val min = dijkstra(readInput().scaleMap(5), Point(0, 0))
    println(min)
}

data class Point(val x: Int, val y: Int)

fun readInput(): List<List<Int>> = File("src/main/kotlin/day15/input.txt")
    .readLines()
    .map {
        it.map { it.digitToInt() }
    }