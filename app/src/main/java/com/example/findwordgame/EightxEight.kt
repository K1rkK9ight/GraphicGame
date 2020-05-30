package com.example.findwordgame

import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_tenx_ten.WinView
import kotlinx.android.synthetic.main.activity_tenx_ten.WordsLeft
import kotlinx.android.synthetic.main.activity_tenx_ten.gridLayouttextView

class EightxEight : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    private val mHideHandler = Handler()
    private val mShowPart2Runnable = Runnable {
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    lateinit var gridLayout: GridLayout
    private var statusClick = 0
    private var matrix1 = listOf<String>()
    private var matrix2 = listOf<String>()
    private var row1 = 0
    private var row2 = 0
    private var column1 = 0
    private var column2 = 0
    private val textViews = mutableListOf<TextView>()
    private val countList = mutableListOf<String>()
    private val boardSize = 8

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean = false
    override fun onClick(v: View?) { }

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
        val checkList = BaseOfPossibleWords().createRandomBoard(boardSize)
        val wordList = BaseOfPossibleWords().createSupList(checkList)
        for (element in wordList) {
            val textView = TextView(this)
            val word = element.wordChar
            textView.text = word
            textView.textSize = 15f
            textView.setPadding(20, 10, 20, 10)
            gridLayouttextView.setPadding(10, 10, 10, 10)
            gridLayouttextView.addView(textView)
        }
        gridLayout = findViewById<View>(R.id.gridLayout) as GridLayout
        charBoard = List(boardSize) { CharArray(boardSize) }
        for (rowChar in 0 until boardSize) {
            for (colChar in 0 until boardSize) {
                charBoard[rowChar][colChar] = ' '
            }
        }
        lateinit var dataGridReturn: List<CharArray>
        for (words in wordList) {
            val word = words.wordChar
            val char = words.wordChar.toList()
            dataGridReturn = BoardView().settingWord(word, char, boardSize)
        }
        BoardView().characterPadding(dataGridReturn, boardSize)
        boardOutput(dataGridReturn, boardSize, checkList)
    }

    @SuppressLint("SetTextI18n")
    private fun boardOutput(dataGridReturn: List<CharArray>, boardSize: Int, checkList: List<String>) {
        val listChar = mutableListOf<Char>()
        var count = 0
        var left = 9
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                val textView = TextView(this)
                textView.layoutParams = GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, 1f))
                listChar.add(dataGridReturn[i][j])
                textView.text = dataGridReturn[i][j].toString()
                textView.id = i
                textView.setBackgroundResource(R.drawable.border)
                textView.gravity = Gravity.CENTER
                textView.tag = "$i+$j"
                textView.setOnClickListener {
                    if (statusClick == 0) {
                        val tag = textView.tag.toString()
                        matrix1 = tag.split("+")
                        row1 = matrix1[0].toInt()
                        column1 = matrix1[1].toInt()
                        statusClick++
                    } else {
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
                                        if (text.tag == "$row1+$col") text.setBackgroundColor(
                                            Color.rgb(240,139,235))
                                    }
                                }
                                Toast.makeText(this,
                                    "Слово отгадано <3", Toast.LENGTH_SHORT).show()
                                left--
                                WordsLeft.text = "Слов осталось: $left"
                                count++
                            } else Toast.makeText(this,
                                "Неправильно или не заданное слово =(", Toast.LENGTH_SHORT).show()
                        } else if (matrix1[1] == matrix2[1]) {
                            for (row in row1..row2) {
                                for (text in textViews) {
                                    if (text.tag == "$row+$column2") string += dataGridReturn[row][column2]
                                }
                            }
                            if (checkList.contains(string)) {
                                countList.add(string)
                                for (row in row1..row2) {
                                    for (text in textViews) {
                                        if (text.tag == ("$row+$column2")) text.setBackgroundColor(
                                            Color.rgb( 233,151,200))
                                    }
                                }
                                Toast.makeText(this,
                                    "Так держать!", Toast.LENGTH_SHORT).show()
                                left--
                                WordsLeft.text = "Слов осталось: $left"
                                count++
                            } else Toast.makeText(this,
                                "Попробуй еще раз!", Toast.LENGTH_SHORT).show()
                        } else {
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
                                            if (text.tag == "$row+$colum") text.setBackgroundColor(
                                                Color.rgb(208,128,217))
                                        }
                                        colum++
                                    }
                                    Toast.makeText(this,
                                        "Мое почтение ^.^", Toast.LENGTH_SHORT).show()
                                    left--
                                    WordsLeft.text = "Слов осталось: $left"
                                    count++
                                } else Toast.makeText(this,
                                    "Такого нет!", Toast.LENGTH_SHORT).show()
                            } else {
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
                                    Toast.makeText(this,
                                        "Это, просто, нечто!", Toast.LENGTH_SHORT).show()
                                    left--
                                    WordsLeft.text = "Слов осталось: $left"
                                    count++
                                } else Toast.makeText(this,
                                    "Не-а!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        if (count == 9) {
                            WinView.text = "YOU WIN!"
                            Toast.makeText(this,
                                "Для начала новой игры нажмите кнопку рестарт!",
                                Toast.LENGTH_SHORT).show()
                        }
                        gridLayouttextView.removeAllViewsInLayout()
                        for (element in checkList) {
                            val textView1  = TextView(this)
                            if (countList.contains(element)) textView1.setTextColor(
                                Color.rgb(144,116,175))
                            textView1.text = element
                            textView1.textSize = 15f
                            textView1.setPadding(20, 10, 20, 10)
                            gridLayouttextView.setPadding(10,10,10,10)
                            gridLayouttextView.addView(textView1)
                        }
                        statusClick = 0
                    }
                }
                textView.textSize = 15f
                textView.setPadding(20, 10, 20, 10)
                textViews.add(textView)
                gridLayout.setPadding(10, 10, 10, 10)
                gridLayout.addView(textView)
                gridLayout.columnCount = boardSize
                gridLayout.rowCount = boardSize
            }
        }
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