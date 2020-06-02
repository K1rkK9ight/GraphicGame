package com.example.findwordgame

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayout
import android.view.*
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_eightx_eight.fullscreen_content_controls
import kotlinx.android.synthetic.main.activity_eightx_eight.WinView
import kotlinx.android.synthetic.main.activity_eightx_eight.WordsLeft
import kotlinx.android.synthetic.main.activity_eightx_eight.gridLayouttextView

class EightxEight : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    private val mHideHandler = Handler()
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    lateinit var gridLayout: GridLayout
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    private var statusClick = 0
    private var matrix1 = listOf<String>()
    private var matrix2 = listOf<String>()
    private var row1 = 0
    private var row2 = 0
    private var column1 = 0
    private var column2 = 0
    private var left = 9
    private var count = 0
    private val textViews = mutableListOf<TextView>()
    private val countList = mutableListOf<String>()
    private val checkList = BaseOfPossibleWords().createRandomBoard(boardSize)
    private val wordList = BaseOfPossibleWords().createSupList(checkList)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean = false
    override fun onClick(view: View?) { }

    val color = Color.alpha(100)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_eightx_eight)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mVisible = true
        if (!hardMode) BoardView().boardTextView(this, wordList, gridLayouttextView)
        else gridLayouttextView.setBackgroundColor(color)
        gridLayout = findViewById(R.id.gridLayout)
        charBoard = List(boardSize) { CharArray(boardSize) }
        CreateBoard().createEmptyBoard(boardSize)
        val dataGridReturn = CreateBoard().boardFilling(wordList, boardSize)
        CreateBoard().characterPadding(dataGridReturn, boardSize)
        boardOutput(dataGridReturn, boardSize, checkList)
    }

    @SuppressLint("SetTextI18n")
    fun boardOutput(dataGridReturn: List<CharArray>, boardSize: Int, checkList: List<String>) {
        val listChar = mutableListOf<Char>()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val textView = TextView(this)
                textView.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f))
                listChar.add(dataGridReturn[i][j])
                textView.apply {
                    text = dataGridReturn[i][j].toString()
                    id = i
                    setBackgroundResource(R.drawable.border)
                    gravity = Gravity.CENTER
                    tag = "$i+$j"
                }
                textView.setOnClickListener {
                    if (statusClick == 0) statusClickZero(textView)
                    else statusClickNotZero(textView, dataGridReturn)
                }
                textView.textSize = 15f
                textView.setPadding(20, 10, 20, 10)
                textViews.add(textView)
                gridLayout.apply {
                    setPadding(10, 10, 10, 10)
                    gridLayout.addView(textView)
                    gridLayout.columnCount = boardSize
                    gridLayout.rowCount = boardSize
                }
            }
        }
    }

    private fun statusClickZero(textView: TextView) {
        if (statusClick == 0) {
            val tag = textView.tag.toString()
            matrix1 = tag.split("+")
            row1 = matrix1[0].toInt()
            column1 = matrix1[1].toInt()
            statusClick++
        }
    }

    @SuppressLint("SetTextI18n")
    fun statusClickNotZero(textView: TextView, dataGridReturn: List<CharArray> ) {
        var string = ""
        val tag = textView.tag.toString()
        matrix2 = tag.split("+")
        row2 = matrix2[0].toInt()
        column2 = matrix2[1].toInt()
        if (matrix1[0] == matrix2[0]) {
            for (col in column1..column2) {
                for (text in textViews) {
                    if (text.tag == "$row1+$col") string += dataGridReturn[row1][col]
                }
            }
            if (checkList.contains(string)) {
                countList.add(string)
                for (col in column1..column2) {
                    for (text in textViews) {
                        if (text.tag == "$row1+$col") text.setBackgroundColor(Color.rgb(240,139,235))
                    }
                }
                BoardView().toastMakeGoodText(this)
                left--
                wordsLeft(left)
                count++
            } else BoardView().toastMakeBadText(this)
        }
        else if (matrix1[1] == matrix2[1]) {
            for (row in row1..row2) {
                for (text in textViews) {
                    if (text.tag == "$row+$column2") string += dataGridReturn[row][column2]
                }
            }
            if (checkList.contains(string)) {
                countList.add(string)
                for (row in row1..row2) {
                    for (text in textViews) {
                        if (text.tag == ("$row+$column2")) text.setBackgroundColor(Color.rgb( 233,151,200))
                    }
                }
                BoardView().toastMakeGoodText(this)
                left--
                wordsLeft(left)
                count++
            } else BoardView().toastMakeBadText(this)
        }
        else {
            if (row1 < row2) {
                var col = column1
                for(row in row1..row2) {
                    for(text in textViews) {
                        if (text.tag == ("$row+$col")) string += dataGridReturn[row][col]
                    }
                    col++
                }
                if (checkList.contains(string)) {
                    countList.add(string)
                    var colum = column1
                    for (row in row1..row2) {
                        for (text in textViews) {
                            if (text.tag == "$row+$colum") text.setBackgroundColor(Color.rgb(208,128,217))
                        }
                        colum++
                    }
                    BoardView().toastMakeGoodText(this)
                    left--
                    wordsLeft(left)
                    count++
                } else BoardView().toastMakeBadText(this)
            }
            else {
                var column = column1
                for (row in row1.downTo(row2)) {
                    for(text in textViews){
                        if(text.tag == "$row+$column") string += dataGridReturn[row][column]
                    }
                    column++
                }
                if (checkList.contains(string)) {
                    countList.add(string)
                    var column3 = column1
                    for (row in row1.downTo(row2)) {
                        for (text in textViews) {
                            if (text.tag == "$row+$column3") text.setBackgroundColor(
                                Color.rgb(228,164,214))
                        }
                        column3++
                    }
                    BoardView().toastMakeGoodText(this)
                    left--
                    wordsLeft(left)
                    count++
                } else BoardView().toastMakeBadText(this)
            }
        }
        winControl(count, this)
        gridLayouttextView.removeAllViewsInLayout()
        BoardView().textViewElement(checkList, this, countList, gridLayouttextView)
        statusClick = 0
    }

    @SuppressLint("SetTextI18n")
    fun winControl(count: Int, context: Context) {
        if (count == 9) {
            WinView.text = "YOU WIN!"
            Toast.makeText(context, "Для начала новой игры нажмите кнопку рестарт!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun wordsLeft(left: Int) {
        WordsLeft.text = "Слов осталось: $left"
    }

    fun backToLevels(view: View) {
        startActivity(Intent(this, Levels::class.java))
        overridePendingTransition(R.anim.alpha,R.anim.beta)
        finish()
    }
    fun restart(view: View) {
        startActivity(Intent(this, EightxEight::class.java))
        overridePendingTransition(R.anim.alpha,R.anim.beta)
        finish()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }

    private fun hide() {
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false
        mHideHandler.removeCallbacks(mShowPart2Runnable)
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

}