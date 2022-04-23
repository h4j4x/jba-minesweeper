package org.h4j4x.minesweeper

import kotlin.random.Random

class Board(val size: Int) {
    private val cells: List<List<Cell>>

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

    fun setMines(count: Int) {
        clearMines()
        val random = Random.Default
        repeat(count) {
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

    private fun clearMines() {
        for (row in cells) {
            for (cell in row) {
                cell.mined = false
                cell.minesAround = 0
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
}
