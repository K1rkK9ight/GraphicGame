package com.example.findwordgame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_eightx_eight.WordsLeft
import kotlinx.android.synthetic.main.activity_eightx_eight.WinView


class BoardView {



    fun boardTextView(context: Context, wordList: List<Word>, gridLayouttextView: GridLayout) {
        for (element in wordList) {
            val textView = TextView(context)
            val word = element.wordChar
            textView.text = word
            textView.textSize = 15f
            textView.setPadding(20, 10, 20, 10)
            gridLayouttextView.setPadding(10, 10, 10, 10)
            gridLayouttextView.addView(textView)
        }
    }

    fun textViewElement(checkList: List<String>, context: Context,
                        countList: List<String>, gridLayoutTextView: GridLayout) {
        for (element in checkList) {
            val textView1  = TextView(context)
            if (countList.contains(element)) textView1.setTextColor(
                Color.rgb(144,116,175))
            textView1.apply {
                text = element
                textSize = 15f
                setPadding(20, 10, 20, 10) }
            gridLayoutTextView.setPadding(10,10,10,10)
            gridLayoutTextView.addView(textView1)
        }
    }


    fun toastMakeGoodText(context: Context) {
        val list = listOf("Это, просто, нечто!", "Мое почтение ^.^", "Попробуй еще раз!",
            "Слово отгадано <3", "Молодец!", "Продолжай!", "Ого~(o)~")
        val massage = list.random()
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    fun toastMakeBadText(context: Context) {
        val list = listOf("Не-а!", "Такого нет!", "Неправильно!",
            "Не заданное слово =(", "Эх ты", "Не это!", "Не, не, не, другое")
        val massage = list.random()
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    fun winControl(count: Int, context: Context) {
        if (count == 9) {
            EightxEight().WinView.text = "YOU WIN!"
            Toast.makeText(context, "Для начала новой игры нажмите кнопку рестарт!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    fun wordsLeft(left: Int) {
        EightxEight().WordsLeft.text = "Слов осталось: $left"
    }

}