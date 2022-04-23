package org.h4j4x.minesweeper

import kotlin.random.Random

class Board(val size: Int) {
    private val cells: List<List<Cell>>
    private var minesCount = 0
    private var minesCleared = 0
    private var safeCellsCleared = 0
    private var unexploredCount = size * size

    init {
        cells = mutableListOf()
        for (rowIndex in 0 until size) {
            val row = mutableListOf<Cell>()
            for (columnIndex in 0 until size) {
                row.add(Cell(rowIndex = rowIndex, columnIndex = columnIndex))
            }
            cells.add(row)
        }
    }

    fun setMines(minesCount: Int) {
        clear()
        this.minesCount = minesCount
        val random = Random.Default
        repeat(minesCount) {
            var cell: Cell?
            do {
                val rowIndex = random.nextInt(from = 0, until = size)
                val columnIndex = random.nextInt(from = 0, until = size)
                cell = cellAt(rowIndex, columnIndex)
            } while (cell == null || cell.mined)
            cell.mined = true
            val cellsAround = cellsAround(cell)
            for (cellAround in cellsAround) {
                cellAround.minesAround++
            }
        }
    }

    fun cellAt(rowIndex: Int, columnIndex: Int): Cell? {
        if (rowIndex in 0 until size && columnIndex in 0 until size) {
            return cells[rowIndex][columnIndex]
        }
        return null
    }

    private fun clear() {
        minesCount = 0
        minesCleared = 0
        safeCellsCleared = 0
        unexploredCount = size * size
        for (row in cells) {
            for (cell in row) {
                cell.clear()
            }
        }
    }

    private fun cellsAround(cell: Cell): List<Cell> {
        val cellsAround = mutableListOf<Cell>()
        var rowIndex = cell.rowIndex - 1
        var columnIndex = cell.columnIndex - 1
        repeat(3) {
            repeat(3) {
                val cellAround = cellAt(rowIndex, columnIndex)
                if (cellAround != null && cellAround != cell) {
                    cellsAround.add(cellAround)
                }
                columnIndex++
            }
            rowIndex++
            columnIndex = cell.columnIndex - 1
        }
        return cellsAround
    }

    fun clear(cell: Cell) {
        val step = if (cell.cleared) -1 else 1
        cell.cleared = !cell.cleared
        if (cell.mined) {
            minesCleared += step
        } else {
            safeCellsCleared += step
        }
        if (hasMinesCleared() && hasNotSafeCellsCleared()) {
            throw GameOverEvent(true, "Congratulations! You found all the mines!")
        }
    }

    private fun hasMinesCleared() = minesCount == minesCleared || unexploredCount <= minesCount

    private fun hasNotSafeCellsCleared() = safeCellsCleared == 0

    fun explore(cell: Cell) = explore(cell, true)

    private fun explore(cell: Cell, checkWin: Boolean) {
        if (!cell.explored) {
            cell.explored = true
            unexploredCount--

            if (cell.mined && !cell.cleared) {
                throw GameOverEvent(false, "You stepped on a mine and failed!")
            }
            if (cell.minesAround == 0) {
                val cellsAround = cellsAround(cell)
                for (cellAround in cellsAround) {
                    explore(cellAround, false)
                }
            }
            if (checkWin && hasMinesCleared() && hasNotSafeCellsCleared()) {
                throw GameOverEvent(true, "Congratulations! You found all the mines!")
            }
        }
    }
}
