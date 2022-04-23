package org.h4j4x.minesweeper

data class Cell(val rowIndex: Int, val columnIndex: Int) {
    var mined = false
    var minesAround = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (rowIndex != other.rowIndex) return false
        if (columnIndex != other.columnIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = rowIndex
        result = 31 * result + columnIndex
        return result
    }
}
