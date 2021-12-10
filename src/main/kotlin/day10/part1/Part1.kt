package day10.part1

import java.io.File

fun main(args: Array<String>) {
    val input = readInput()

    val characterMap = mapOf<Char, Char>(
        ')' to '(',
        '>' to '<',
        '}' to '{',
        ']' to '[',
    )

    fun getScoreFor(char: Char) : Int = when(char){
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }

    fun findCorruptionChar(line: String) : Char? {
        val stack = mutableListOf<Char>()
        line.toList().forEach { char ->
            if(characterMap.containsKey(char)){ // is closing char
                if(stack.last() == characterMap[char]){ //stack top equals opposite of closing char
                    stack.removeLast() //pop from stack
                } else {
                    return char
                }
            } else { //is opening char
                stack.add(char)
            }
        }

        //incomplete if stack is not empty

        return null
    }

    val output = input.mapNotNull { findCorruptionChar(it) }.map { getScoreFor(it)}.sum()
    println(output)

}

fun readInput(): List<String> {
    return File("src/main/kotlin/day10/input.txt").readLines()
}