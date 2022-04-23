@file:JvmName("Main")

package org.h4j4x.minesweeper

const val SHOW_HINTS = false
const val UNEXPLORED_CELL = '.'
const val EXPLORED_CELL = '/'
const val MINE_CELL = 'X'
const val CLEAR_CELL = '*'
const val CLEAR_ACTION = "mine"
const val EXPLORE_ACTION = "free"

fun main() {
    print("How many mines do you want on the field? ")
    val minesCount = readln().toInt()
    val board = Board(size = 9)
    board.setMines(minesCount)
    try {
        do {
            printBoard(board)
            print("Set/unset mines marks or claim a cell as free: ")
            val action = readln().split(" ")
            val actionMessage = processBoardAction(board, action)
            if (actionMessage != null) {
                println(actionMessage)
            }
            println()
        } while (true)
    } catch (e: GameOverEvent) {
        printBoard(board)
        println(e.message)
    }
}

fun processBoardAction(board: Board, action: List<String>): String? {
    if (action.size != 3) {
        return "Invalid command!"
    }
    val columnIndex = action[0].toIntOrNull()
    val rowIndex = action[1].toIntOrNull()
    if (columnIndex == null || rowIndex == null
        || columnIndex - 1 !in 0 until board.size
        || rowIndex - 1 !in 0 until board.size) {
        return "Invalid coordinates!"
    }
    val isClearAction = action[2] == CLEAR_ACTION
    if (action[2] != EXPLORE_ACTION && !isClearAction) {
        return "Invalid action!"
    }

    val cell = board.cellAt(rowIndex = rowIndex - 1, columnIndex = columnIndex - 1)
    if (cell != null) {
        if (isClearAction) {
            board.clear(cell)
        } else {
            board.explore(cell)
        }
        return null
    }
    return "Cell not found!"
}

fun printBoard(board: Board) {
    println(" │123456789│")
    println("—│—————————│")
    for (rowIndex in 0 until board.size) {
        print("${rowIndex + 1}│")
        for (columnIndex in 0 until board.size) {
            val cell = board.cellAt(rowIndex = rowIndex, columnIndex = columnIndex)
            if (cell != null) {
                if (cell.explored) {
                    if (cell.mined) {
                        print(MINE_CELL)
                    } else {
                        print(if (cell.minesAround > 0) cell.minesAround else EXPLORED_CELL)
                    }
                } else if (cell.cleared) {
                    print(CLEAR_CELL)
                } else if (cell.mined) {
                    print(if (SHOW_HINTS) MINE_CELL else UNEXPLORED_CELL)
                } else {
                    print(if (SHOW_HINTS && cell.minesAround > 0) cell.minesAround else UNEXPLORED_CELL)
                }
            }
        }
        println("|")
    }
    println("—│—————————│")
}
