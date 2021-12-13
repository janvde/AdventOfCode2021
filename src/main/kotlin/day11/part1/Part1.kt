package day11.part1

import java.io.File

fun main(args: Array<String>) {

    fun getNeighboursMatrix(): List<Pair<Int, Int>> = listOf(
        Pair(-1, -1), Pair(0, -1), Pair(1, -1),
        Pair(-1, 0),  Pair(1, 0),
        Pair(-1, 1), Pair(0, 1), Pair(1, 1),
    )

    fun Map<Point, Int>.getNeighbours(point: Point) : List<Point> {
        return getNeighboursMatrix().mapNotNull {
            val point = Point(point.x + it.first, point.y + it.second)
            val neighbourEnergy = getOrDefault(point, -1)
            getOrDefault(point, -1)
            if(neighbourEnergy != -1 ) point else null
        }
    }

    fun Map<Point, Int>.increment(): Map<Point, Int> = mapValues { it.value.inc() }

    fun flash(octopus: Map<Point, Int>) : Map<Point, Int> {
        val result = octopus.toMutableMap()

        while (result.values.count { it > 9 } > 0) {
            val flashing = result.filterValues { it > 9 }.keys
            flashing.forEach {
                result[it] = 0
            }

            flashing // get all points that should flash
                .flatMap { octopus.getNeighbours(it) }
                .filterNot { result[it] == 0 } //get all neighbours that should increment
                .forEach { neighbour ->
                    result[neighbour] = result[neighbour]!! + 1
                }
        }

        return result
    }




    fun step(octopus: Map<Point, Int>) : Map<Point, Int> {
        //increment energy level
        val incremented = octopus.increment()

        //flash until no energy level above 9
        val flashed = flash(incremented)

        //return flashes
        return flashed
    }

    val input = readInput()
    var result = input
    var total = 0
    (0 until 100).forEach {
        result = step(result)
        total += result.count { it.value == 0 }
    }

    println(total)

}

data class Point(val x: Int, val y: Int)

fun readInput(): Map<Point, Int> {
    return File("src/main/kotlin/day11/input.txt").readLines().mapIndexed { yindex, yvalue ->
        yvalue.toList().mapIndexed { xindex, xvalue ->
            Pair(Point(xindex, yindex), xvalue.digitToInt())
        }
    }.flatten().toMap()
}