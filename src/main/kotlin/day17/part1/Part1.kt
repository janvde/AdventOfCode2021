package day17.part1

fun main(args: Array<String>) {
    val input = readInput()

    //maximize velocity without shooting through target
    //x is not needed for calculating max y
    var yVelocity = -input.yRange.first - 1

    val maxY = (yVelocity downTo 0).fold(0) { acc, i -> acc + i }

    println(maxY)
}

data class TargetArea(val xRange: IntRange, val yRange: IntRange)

//not wasting time on parsing
fun readInput(): TargetArea {
    //target area: x=20..30, y=-10..-5
    //return TargetArea(20.. 30, -10..-5)

    //target area: x=265..287, y=-103..-58
    return TargetArea(265..287, -103..-58)
}