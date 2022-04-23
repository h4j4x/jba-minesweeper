@file:JvmName("Main")

package org.h4j4x.minesweeper

const val SHOW_MINED = false
const val SAFE_CELL = '.'
const val MINE_CELL = 'X'
const val CLEAR_CELL = '*'

fun main() {
    print("How many mines do you want on the field? ")
    val minesCount = readln().toInt()
    val board = Board(size = 9)
    board.setMines(minesCount)
    do {
        printBoard(board)
        print("Set/delete mine marks (x and y coordinates): ")
        val (columnIndex, rowIndex) = readln().split(" ").map { it.toInt() }
        val cell = board.cellAt(rowIndex = rowIndex - 1, columnIndex = columnIndex - 1)
        if (cell != null) {
            if (!cell.mined && cell.minesAround > 0) {
                println("There is a number here!")
            } else {
                board.toggleCellCleared(cell)
            }
            println()
        } else {
            println("There is no cell here!")
        }
    } while(board.hasMinesUncleared() || board.hasSafeCellsCleared())
    printBoard(board)
    println("Congratulations! You found all the mines!")
}

fun printBoard(board: Board) {
    println(" │123456789│")
    println("—│—————————│")
    for (rowIndex in 0 until board.size) {
        print("${rowIndex + 1}│")
        for (columnIndex in 0 until board.size) {
            val cell = board.cellAt(rowIndex = rowIndex, columnIndex = columnIndex)
            if (cell != null) {
                if (cell.cleared) {
                    print(CLEAR_CELL)
                } else if (cell.mined) {
                    print(if (SHOW_MINED) MINE_CELL else SAFE_CELL)
                } else {
                    print(if (cell.minesAround > 0) cell.minesAround else SAFE_CELL)
                }
            }
        }
        println("|")
    }
    println("—│—————————│")
}
