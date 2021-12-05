package day4.part1

import java.io.File

fun main(args: Array<String>) {
    val lines = File("src/main/kotlin/day4/input.txt").readLines()
    val draws = lines.first().split(",").map { it.toInt() }
    val cards = lines.drop(2)
        .windowed(5, 6)
        .map {
            BingoCard(it.map { line ->
                line.split(" ")
                    .filterNot { it.isBlank() }
                    .map { Number(it.toInt(), false) }
            })
        }

    println(solve(draws, cards))
}

tailrec fun solve(draws: List<Int>, cards: List<BingoCard>): Int {
    val markedCards = markCards(draws.first(), cards)
    val winner = markedCards.findWinner()

    return if (winner != null) {
        getUnmarkedSum(winner) * draws.first()
    } else {
        solve(draws.drop(1), markedCards)
    }
}

fun getUnmarkedSum(card: BingoCard): Int = card.numbers.flatten().filter { !it.marked }.sumOf { it.value }

fun markCards(number: Int, cards: List<BingoCard>): List<BingoCard> {
    return cards.map { card ->
        BingoCard(
            card.numbers.map { line ->
                line.map {
                    if (it.value == number) it.marked = true
                    it
                }
            })
    }
}

fun List<BingoCard>.findWinner(): BingoCard? = find { it.hasWon() }

fun BingoCard.hasWon(): Boolean = hasColumnMatch() || hasRowMatch()

fun BingoCard.hasColumnMatch() : Boolean = (0 until numbers.first().size).any { index -> numbers.all { it[index].marked }}

fun BingoCard.hasRowMatch() : Boolean = numbers.any { lines -> lines.all { it.marked } }

data class Number(val value: Int, var marked: Boolean)

data class BingoCard(val numbers: List<List<Number>>)

