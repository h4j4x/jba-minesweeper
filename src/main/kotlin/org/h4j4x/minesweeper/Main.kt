@file:JvmName("Main")

package org.h4j4x.minesweeper

const val SAFE_CELL = '.'
const val MINE_CELL = 'X'

fun main() {
    print("How many mines do you want on the field? ")
    val minesCount = readln().toInt()
    val board = Board(size = 9)
    board.setMines(minesCount)
    for (rowIndex in 0 until board.size) {
        for (columnIndex in 0 until board.size) {
            val cell = board.cellAt(rowIndex = rowIndex, columnIndex = columnIndex)
            if (cell != null) {
                if (cell.mined) {
                    print(MINE_CELL)
                } else {
                    print(if (cell.minesAround > 0) cell.minesAround else SAFE_CELL)
                }
            }
        }
        println()
    }
}
