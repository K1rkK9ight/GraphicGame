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

class EightxEight : AppCompatActivity() {

    private val mHideHandler = Handler()
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private lateinit var gridLayout: GridLayout
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    private var statusClick = 0
    private var matrix1 = listOf<String>()
    private var matrix2 = listOf<String>()
    private var row1 = 0
    private var row2 = 0
    private var column1 = 0
    private var column2 = 0
    private val textViews = mutableListOf<TextView>()
    private val textViewsMap = mutableMapOf<String, String>()
    private val countList = mutableListOf<String>()
    private val checkList = BaseOfPossibleWords().createRandomBoard(boardSize)
    private val wordList = BaseOfPossibleWords().createSupList(checkList)
    private val color = Color.alpha(100)
    private var colorList = mutableListOf<String>()
    private var toastGood = false
    private var toastBad = false

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
        boardOutput(dataGridReturn, boardSize)
    }

    @SuppressLint("SetTextI18n")
    fun boardOutput(dataGridReturn: List<CharArray>, boardSize: Int) {
        val listChar = mutableListOf<Char>()
        val winList = mutableListOf<Int>()
        val leftList = mutableListOf(1, 1, 1, 1, 1, 1, 1, 1, 1)
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val textView = TextView(this)
                textView.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f))
                listChar.add(dataGridReturn[i][j])
                val string = "$i+$j"
                textView.apply {
                    text = dataGridReturn[i][j].toString()
                    id = i
                    setBackgroundResource(R.drawable.border)
                    gravity = Gravity.CENTER
                    tag = string
                }
                textView.setOnClickListener {
                    val textViewTag = textView.tag.toString()
                    if (statusClick == 0) statusClickZero(textViewTag)
                    else {
                        statusClickNotZero(textViewTag, dataGridReturn, winList, leftList)
                        wordsLeft(leftList)
                        BoardView().textViewElement(checkList, this, countList, gridLayouttextView)
                        if (colorList.isNotEmpty()) {
                            for (text in textViews) {
                                for (element in colorList) {
                                    if (text.tag == element) text.setBackgroundColor(Color.rgb( 233,151,200))
                                }
                            }
                        }
                        colorList.clear()
                        if (toastGood) {
                            BoardView().toastMakeGoodText(this)
                            toastGood = false
                        }
                        if (toastBad) {
                            BoardView().toastMakeBadText(this)
                            toastBad = false
                        }
                    }
                    winControl(winList, this)
                }
                textView.textSize = 15f
                textView.setPadding(20, 10, 20, 10)
                textViews.add(textView)
                textViewsMap.put(textView.toString(), string)
                gridLayout.apply {
                    setPadding(10, 10, 10, 10)
                    gridLayout.addView(textView)
                    gridLayout.columnCount = boardSize
                    gridLayout.rowCount = boardSize
                }
            }
        }
    }

    private fun statusClickZero(tag: String) {
        matrix1 = tag.split("+")
        row1 = matrix1[0].toInt()
        column1 = matrix1[1].toInt()
        statusClick++
    }

    @SuppressLint("SetTextI18n")
    fun statusClickNotZero(tag: String, dataGridReturn: List<CharArray>,
                           winList: MutableList<Int>, leftList: MutableList<Int>) {
        matrix2 = tag.split("+")
        row2 = matrix2[0].toInt()
        column2 = matrix2[1].toInt()
        when {
            matrix1[0] == matrix2[0] -> matrixZero(dataGridReturn, winList, leftList)
            matrix1[1] == matrix2[1] -> matrixOne(dataGridReturn, leftList, winList)
            else -> notZeroNotOne(dataGridReturn, leftList, winList)
        }
        statusClick = 0
    }

    private fun matrixZero(dataGridReturn: List<CharArray>, winList: MutableList<Int>,
                           leftList: MutableList<Int>) {
        var string = ""
        for (col in column1..column2) {
            for ((text, values) in textViewsMap) {
                if (values == "$row1+$col") string += dataGridReturn[row1][col]
            }
        }
        if (checkList.contains(string)) {
            countList.add(string)
            for (col in column1..column2) {
                for ((text, values) in textViewsMap) {
                    if (values == "$row1+$col") colorList.add("$row1+$col")
                }
            }
            toastGood = true
            leftList.remove(leftList.last())
            winList.add(1)
        } else toastBad = true
    }

    private fun matrixOne(dataGridReturn: List<CharArray>, leftList: MutableList<Int>,
                          winList: MutableList<Int>) {
        var string = ""
        for (row in row1..row2) {
            for ((text, values) in textViewsMap) {
                if (values == "$row+$column2") string += dataGridReturn[row][column2]
            }
        }
        if (checkList.contains(string)) {
            countList.add(string)
            for (row in row1..row2) {
                for ((text, values) in textViewsMap) {
                    if (values == ("$row+$column2")) colorList.add("$row+$column2")
                }
            }
            toastGood = true
            leftList.remove(leftList.last())
            winList.add(1)
        } else toastBad = true
    }

    private fun notZeroNotOne(dataGridReturn: List<CharArray>, leftList: MutableList<Int>,
                              winList: MutableList<Int>) {
        var string = ""
        if (row1 < row2) {
            var col = column1
            for (row in row1..row2) {
                for ((text, values) in textViewsMap) {
                    if (values == ("$row+$col")) string += dataGridReturn[row][col]
                }
                col++
            }
            if (checkList.contains(string)) {
                countList.add(string)
                var colum = column1
                for (row in row1..row2) {
                    for ((text, values) in textViewsMap) {
                        if (values == "$row+$colum") colorList.add("$row+$colum")
                    }
                    colum++
                }
                toastGood = true
                leftList.remove(leftList.last())
                winList.add(1)
            } else toastBad = true
        } else {
            var column = column1
            for (row in row1.downTo(row2)) {
                for ((text, values) in textViewsMap) {
                    if (values == "$row+$column") string += dataGridReturn[row][column]
                }
                column++
            }
            if (checkList.contains(string)) {
                countList.add(string)
                var column3 = column1
                for (row in row1.downTo(row2)) {
                    for ((text, values) in textViewsMap) {
                        if (values == "$row+$column3") colorList.add("$row+$column3")
                    }
                    column3++
                }
                toastBad = true
                leftList.remove(leftList.last())
                winList.add(1)
            } else toastBad = true
        }
    }

    @SuppressLint("SetTextI18n")
    fun winControl(winList: List<Int>, context: Context) {
        if (winList.size == 9) {
            WinView.text = "YOU WIN!"
            Toast.makeText(context, "Для начала новой игры нажмите кнопку рестарт!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun wordsLeft(leftList: List<Int>) {
        WordsLeft.text = "Слов осталось: ${leftList.size}"
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