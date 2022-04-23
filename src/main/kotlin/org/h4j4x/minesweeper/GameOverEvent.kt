package org.h4j4x.minesweeper

class GameOverEvent(val winner: Boolean, message: String) : Exception(message)
