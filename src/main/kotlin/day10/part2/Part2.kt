package day10.part2


import java.io.File

fun main(args: Array<String>) {
    val input = day10.part1.readInput()

    val characterMap = mapOf<Char, Char>(
        ')' to '(',
        '>' to '<',
        '}' to '{',
        ']' to '[',
    )

    fun getScoreFor(char: Char) : Int = when(char){
        '(' -> 1
        '[' -> 2
        '{' -> 3
        '<' -> 4
        else -> 1 // do nothing
    }

    fun findCompletingChars(line: String) : List<Char>? {
        val stack = mutableListOf<Char>()
        line.toList().forEach { char ->
            if(characterMap.containsKey(char)){ // is closing char
                if(stack.last() == characterMap[char]){ //stack top equals opposite of closing char
                    stack.removeLast() //pop from stack
                } else {
                    return null //corrupted
                }
            } else { //is opening char
                stack.add(char)
            }
        }

        if(stack.isNotEmpty()){ // incomplete
            return stack
        }

        return emptyList()
    }

    val output = input
        .mapNotNull { findCompletingChars(it) }
        .map { chars -> chars.foldRight(0L) { c, acc ->  (acc *5)+getScoreFor(c)} }
        .sorted()
    println(output[output.size/2])

}

fun readInput(): List<String> {
    return File("src/main/kotlin/day10/input.txt").readLines()
}
