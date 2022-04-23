package org.h4j4x.minesweeper

data class Cell(val row: Int, val column: Int, var mined: Boolean = false)
