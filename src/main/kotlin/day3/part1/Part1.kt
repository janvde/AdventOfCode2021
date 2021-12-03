package day3.part1

import java.io.File
import kotlin.math.pow

fun main(args: Array<String>) {
    val input = readInput()

    (0 until input.first().length).map { position ->
        input.count { it[position] == '1' } * 2 > input.size
    }
        .map { if (it) 1 else 0 }
        .joinToString("")
        .let {
            //inverse binary and multiply
            println((it.toInt(2) xor (2.0.pow(it.length)-1).toInt()) * it.toInt(2))
        }
}


fun readInput(): List<String> {
    var result = mutableListOf<String>()
    File("src/main/kotlin/day3/input.txt").bufferedReader().forEachLine {
        result.add(it)
    }
    return result
}