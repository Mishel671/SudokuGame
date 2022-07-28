package ru.dzyubamichael.sudokugameapp.presentation.game.customview

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import ru.dzyubamichael.sudokugameapp.R

class SudokuBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var boardColor = 0
    private var cellFillColor = 0
    private var cellsHighlightColor = 0
    private var letterColor = 0
    private var letterColorSolve = 0
    private var backgroundColor = 0
    private val boardColorPaint = Paint()
    private val cellFillColorPaint = Paint()
    private val cellsHighlightColorPaint = Paint()
    private val letterPaint = Paint()
    private val letterPaintBounds = Rect()
    val solver = Solver()

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val dimension = Math.min(this.measuredWidth, this.measuredHeight)
        val cellSize = dimension / 9
        val isValid: Boolean
        val x = event.x
        val y = event.y
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            solver.selectedRow = Math.ceil((y / cellSize).toDouble()).toInt()
            solver.selectedColumn = Math.ceil((x / cellSize).toDouble()).toInt()
            isValid = true
        } else {
            isValid = false
        }
        return isValid
        //return super.onTouchEvent(event);
    }

    private fun drawNumbers(canvas: Canvas) {
        val dimension = Math.min(this.measuredWidth, this.measuredHeight)
        val cellSize = dimension / 9
        letterPaint.textSize = cellSize.toFloat()
        for (r in 0..8) { // rows
            for (c in 0..8) { // columns
                if (solver.board[r][c] != 0) {
                    val text = Integer.toString(solver.board[r][c])
                    var width: Float
                    var height: Float
                    letterPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
                    width = letterPaint.measureText(text)
                    height = letterPaintBounds.height().toFloat()
                    canvas.drawText(
                        text, c * cellSize + (cellSize - width) / 2,
                        r * cellSize + cellSize - (cellSize - height) / 2,
                        letterPaint
                    )
                }
            }
        }
        letterPaint.color = letterColorSolve
        for (letter in solver.emptyBoxIndex) {
            val r = letter[0] as Int
            val c = letter[1] as Int
            val text = Integer.toString(solver.board[r][c])
            var width: Float
            var height: Float
            letterPaint.getTextBounds(text, 0, text.length, letterPaintBounds)
            width = letterPaint.measureText(text)
            height = letterPaintBounds.height().toFloat()
            canvas.drawText(
                text, c * cellSize + (cellSize - width) / 2,
                r * cellSize + cellSize - (cellSize - height) / 2,
                letterPaint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val dimension = Math.min(this.measuredWidth, this.measuredHeight)
        setMeasuredDimension(dimension, dimension)
    }

    override fun onDraw(canvas: Canvas) {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 16f
        boardColorPaint.color = boardColor
        boardColorPaint.isAntiAlias = true
        cellFillColorPaint.style = Paint.Style.FILL
        cellFillColorPaint.isAntiAlias = true
        cellFillColorPaint.color = cellFillColor
        cellsHighlightColorPaint.style = Paint.Style.FILL
        cellsHighlightColorPaint.isAntiAlias = true
        cellsHighlightColorPaint.color = cellsHighlightColor
        letterPaint.style = Paint.Style.FILL
        letterPaint.isAntiAlias = true
        letterPaint.color = letterColor
        canvas.drawColor(backgroundColor)
        colorCell(canvas, solver.selectedRow, solver.selectedColumn)
        canvas.drawRect(0f, 0f, this.width.toFloat(), this.height.toFloat(), boardColorPaint)
        drawBoard(canvas)
        drawNumbers(canvas)
    }

    private fun colorCell(canvas: Canvas, r: Int, c: Int) {
        val dimension = Math.min(this.measuredWidth, this.measuredHeight)
        val cellSize = dimension / 9
        if (solver.selectedColumn != -1 && solver.selectedRow != -1) {
            // invalidate(); // Refresh
            canvas.drawRect(
                ((c - 1) * cellSize).toFloat(), 0f, (c * cellSize).toFloat(), (
                        cellSize * 9).toFloat(), cellFillColorPaint
            )
            canvas.drawRect(
                0f, ((r - 1) * cellSize).toFloat(), (cellSize * 9).toFloat(), (
                        r * cellSize).toFloat(), cellFillColorPaint
            )
            canvas.drawRect(
                ((c - 1) * cellSize).toFloat(),
                ((r - 1) * cellSize).toFloat(),
                (c * cellSize).toFloat(),
                (
                        r * cellSize).toFloat(),
                cellsHighlightColorPaint
            )
        }
        invalidate() // Refresh
    }

    private fun drawThickLine() {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 10f
        boardColorPaint.color = boardColor
    }

    private fun drawThinLine() {
        boardColorPaint.style = Paint.Style.STROKE
        boardColorPaint.strokeWidth = 4f
        boardColorPaint.color = boardColor
    }

    private fun drawBoard(canvas: Canvas) {
        val dimension = Math.min(this.measuredWidth, this.measuredHeight)
        val cellSize = dimension / 9
        // columns
        for (c in 0..9) {
            if (c % 3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas.drawLine(
                (cellSize * c).toFloat(), 0f, (
                        cellSize * c).toFloat(), this.width.toFloat(), boardColorPaint
            )
        }
        // rows
        for (r in 0..9) {
            if (r % 3 == 0) {
                drawThickLine()
            } else {
                drawThinLine()
            }
            canvas.drawLine(
                0f, (cellSize * r).toFloat(),
                this.height.toFloat(), (cellSize * r).toFloat(), boardColorPaint
            )
        }
    }

    fun getGameData(): Array<IntArray> = solver.board

    fun setGameData(gameData: Array<IntArray>){
        solver.board = gameData
    }


    init {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SudokuBoardView, 0, 0
        )
        try {
            boardColor = a.getInteger(R.styleable.SudokuBoardView_boardColor, 0)
            cellFillColor = a.getInteger(R.styleable.SudokuBoardView_cellFillColor, 0)
            cellsHighlightColor = a.getInteger(R.styleable.SudokuBoardView_cellsHighLightColor, 0)
            letterColor = a.getInteger(R.styleable.SudokuBoardView_letterColor, 0)
            letterColorSolve = a.getInteger(R.styleable.SudokuBoardView_letterColorSolve, 0)
            backgroundColor = a.getInteger(R.styleable.SudokuBoardView_backgroundColor, 0)
        } finally {
            a.recycle()
        }
    }
}