package org.h4j4x.minesweeper

import kotlin.random.Random

class Board(val size: Int) {
    private val cells: List<List<Cell>>

    init {
        cells = mutableListOf()
        for (rowIndex in 0 until size) {
            val row = mutableListOf<Cell>()
            for (columnIndex in 0 until size) {
                row.add(Cell(row = rowIndex, column = columnIndex))
            }
            cells.add(row)
        }
    }

    fun setMines(count: Int) {
        clearMines()
        val random = Random.Default
        repeat(count) {
            var cell: Cell
            do {
                val rowIndex = random.nextInt(from = 0, until = size)
                val columnIndex = random.nextInt(from = 0, until = size)
                cell = cellAt(rowIndex, columnIndex)
            } while (cell.mined)
            cell.mined = true
        }
    }

    fun cellAt(rowIndex: Int, columnIndex: Int) = cells[rowIndex][columnIndex]

    private fun clearMines() {
        for (row in cells) {
            for (cell in row) {
                cell.mined = false
            }
        }
    }
}
