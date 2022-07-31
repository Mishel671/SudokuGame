package ru.dzyubamichael.sudokugameapp.presentation.game.customview

class Solver internal constructor() {
    var board: Array<IntArray>
    var emptyBoxIndex: ArrayList<ArrayList<Any>>

    var selectedRow: Int
    var selectedColumn: Int

    init {
        selectedRow = -1
        selectedColumn = -1
        board = Array(9) { IntArray(9) }
        for (r in 0..8) { // rows
            for (c in 0..8) { // columns
                board[r][c] = 0
            }
        }
        emptyBoxIndex = ArrayList()
    }

    fun getEmptyBoxIndexes() {
        for (r in 0..8) { // rows
            for (c in 0..8) { // columns
                if (board[r][c] == 0) {
                    emptyBoxIndex.add(ArrayList())
                    emptyBoxIndex[emptyBoxIndex.size - 1].add(r)
                    emptyBoxIndex[emptyBoxIndex.size - 1].add(c)
                }
            }
        }
    }

    private fun check(row: Int, col: Int): Boolean {
        if (board[row][col] > 0) {
            for (i in 0..8) {
                // row check
                if (board[i][col] == board[row][col] && row != i) {
                    return false
                }
                // col check
                if (board[row][i] == board[row][col] && col != i) {
                    return false
                }
            }
            // box check
            val boxRow = row / 3
            val boxCol = col / 3
            for (r in boxRow * 3 until boxRow * 3 + 3) {
                for (c in boxCol * 3 until boxCol * 3 + 3) {
                    if (board[r][c] == board[row][col] && row != r && col != c) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun solve(display: SudokuBoardView?): Boolean {
        var row = -1
        var col = -1
        for (r in 0..8) {
            for (c in 0..8) {
                if (board[r][c] == 0) {
                    row = r
                    col = c
                    break
                }
            }
        }
        if (row == -1 || col == -1) {
            return true
        }
        for (i in 1..9) {
            board[row][col] = i
            // display.invalidate();
            if (check(row, col)) {
                if (solve(display)) {
                    return true
                }
            }
            board[row][col] = 0
        }
        return false
    }

    fun resetBoard() {
        for (r in 0..8) { // rows
            for (c in 0..8) { // columns
                board[r][c] = 0
            }
        }
        emptyBoxIndex = ArrayList()
    }

    fun setNumberPos(num: Int) {
        if (selectedRow != -1 && selectedColumn != -1) {
            if (board[selectedRow - 1][selectedColumn - 1] == num) {
                board[selectedRow - 1][selectedColumn - 1] = 0
            } else {
                board[selectedRow - 1][selectedColumn - 1] = num
            }
        }
    }
}