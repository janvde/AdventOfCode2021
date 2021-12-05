package day5.part1

import java.io.File
import java.lang.Integer.max
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val input = readInput().filter {
        it.start.x == it.end.x || it.start.y == it.end.y
    }

    println(countOverlaps(input))
}

fun readInput() : List<Line> {
    val regex = Regex("""\d+""")
    return File("src/main/kotlin/day5/input.txt").readLines().map {
        val (x1, y1, x2, y2) = regex.findAll(it).map { it.value.toInt()}.toList()
        Line(Point(x1, y1), Point(x2,y2))
    }
}

fun Line.toPoints() : List<Point> {
    val horizontalRange = if(start.x <= end.x) start.x .. end.x else end.x .. start.x
    val verticalRange = if(start.y <= end.y) start.y .. end.y else end.y .. start.y

    return if(horizontalRange.toList().size > verticalRange.toList().size){
        horizontalRange.toList().map {
            Point(it, end.y)
        }
    } else {
        verticalRange.toList().map {
            Point(start.x, it)
        }
    }
}

fun countOverlaps(lines: List<Line>) : Int {
    return lines.map { it.toPoints() }
        .flatten()
        .groupingBy { it }
        .eachCount()
        .count { it.value > 1 }
}

data class Point(val x : Int, val y: Int)

data class Line(val start: Point, val end: Point)
