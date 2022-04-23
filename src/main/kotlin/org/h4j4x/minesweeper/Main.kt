@file:JvmName("Main")

package org.h4j4x.minesweeper

import kotlin.random.Random

const val SAFE_CELL = '.'
const val MINE_CELL = 'X'

fun randomPoint(maxValue: Int): Pair<Int, Int> {
    return Random.nextInt(from = 0, until = maxValue) to Random.nextInt(from = 0, until = maxValue)
}

fun main() {
    val boardSize = 9
    val board = MutableList(boardSize) {
        MutableList(boardSize) { SAFE_CELL }
    }
    print("How many mines do you want on the field? ")
    repeat(readln().toInt()) {
        var x: Int
        var y: Int
        do {
            val pair = randomPoint(boardSize)
            x = pair.first
            y = pair.second
        } while (board[x][y] == MINE_CELL)
        board[x][y] = MINE_CELL
    }
    for (row in board) {
        println(row.joinToString(""))
    }
}
