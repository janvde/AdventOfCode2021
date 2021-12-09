package day9.part1

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()

    fun getDepthAt(x: Int, y: Int) : Int? = input.getOrNull(y)?.getOrNull(x)

    fun getAdjacentDepths(x: Int, y: Int) : List<Int> {
        return listOfNotNull(
            getDepthAt(x-1, y),
            getDepthAt(x+1,y),
            getDepthAt(x, y-1),
            getDepthAt(x, y+1)
        )
    }

    fun isLowpoint(x: Int, y: Int) : Boolean {
         return getAdjacentDepths(x,y).minOf { it } > getDepthAt(x,y)!!
    }

    val result = mutableListOf<Int>()
    input.forEachIndexed { yindex, ylist ->
        ylist.forEachIndexed { xindex, i ->
            if(isLowpoint(xindex, yindex)) result.add(getDepthAt(xindex,yindex)!!)
        }
    }

    println(result.sumOf { it + 1 })

}

fun readInput(): List<List<Int>> {
    return File("src/main/kotlin/day9/input.txt").readLines().map { line ->
       line.toList().map { it.digitToInt() }
    }
}