package com.example.findwordgame

import kotlin.math.floor

lateinit var charBoard: List<CharArray>

class BoardView {

    fun settingWord(word: String, char: List<Char>, characters: Int) : List<CharArray> {
        val grid = charBoard
        val randomRow = (0..7).random()
        val randomCol = (0..7).random()
        var typeOfFilling = (1..3).random()
        var indRow = 0
        var indCol = 0
        var recursive = 0
        val lengthRange: IntProgression
        val board= List(characters) { CharArray(characters) }
        if (typeOfFilling == 1) {
            if (characters - randomRow >= word.length) {
                for (row in randomRow until characters) {
                    if (indRow < word.length) {
                        board[row][randomCol] = grid[row][randomCol]
                        if (grid[row][randomCol] == ' ') grid[row][randomCol] = char[indRow]
                        else if (char[indRow] != grid[row][randomCol]) recursive = 1
                        }
                    indRow++
                }
                if (recursive == 1) {
                    lengthRange = (randomRow + word.length - 1).downTo(randomRow)
                    for (row in lengthRange) grid[row][randomCol] = board[row][randomCol]
                    settingWord(word, char, characters)
                }
            } else settingWord(word, char, characters)
        } else if (typeOfFilling == 2) {
            if (characters - randomCol >= word.length) {
                for (col in randomCol until characters) {
                    if (indCol < word.length) {
                        board[randomRow][col] = grid[randomRow][col]
                            if (grid[randomRow][col] == ' ') grid[randomRow][col] = char[indCol]
                            else if (char[indCol] != grid[randomRow][indCol]) recursive = 1
                        indCol++
                    }
                }
                if (recursive == 1) {
                    lengthRange = (randomCol + word.length - 1).downTo(randomCol)
                    for (col in lengthRange) grid[randomRow][col] = board[randomRow][col]
                    settingWord(word, char, characters)
                }
            } else settingWord(word, char, characters)
        } else if (typeOfFilling == 3) {
            var index  = randomRow
            typeOfFilling = (1..2).random()
            if (typeOfFilling == 1) {
                indCol = 0
                if (characters - randomCol >= word.length && characters - randomRow >= word.length) {
                    for (col in randomCol until characters) {
                        if (indCol < word.length) {
                            board[index][col] = grid[index][col]
                            if (grid[index][col] == ' ') grid[index][col] = char[indCol]
                            else if (char[indCol] != grid[index][indCol]) recursive = 1
                            indCol++
                        }
                        index++
                    }
                    if (recursive == 1) {
                        indRow = randomRow
                        indCol = randomCol
                        for (col in randomCol until word.length) {
                            grid[indRow][indCol] = board[indRow][indCol]
                            indCol++
                            indRow++
                        }
                        settingWord(word, char, characters)
                    }
                } else settingWord(word, char, characters)
            } else {
                indCol = 0
                if (characters - randomCol >= word.length && randomRow >= word.length) {
                    for (col in randomCol until characters) {
                        if (index >= 0) {
                            if (indCol < word.length) {
                                board[index][col] = grid[index][col]
                                if (grid[index][col] == ' ') grid[index][col] = char[indCol]
                                else if (char[indCol] != grid[index][indCol]) recursive = 1
                                indCol++
                            }
                            index--
                        }
                    }
                    if (recursive == 1) {
                        lengthRange = (randomCol + word.length - 1).downTo(randomCol)
                        var indexRow = randomRow - word.length + 1
                        for (col in lengthRange) {
                            if (indexRow < randomRow + 1) {
                                grid[indexRow][col] = board[indexRow][col]
                                indexRow++
                            }
                        }
                        settingWord(word, char, characters)
                    }
                } else settingWord(word, char, characters)
            }
        }
        return grid
    }

    fun characterPadding(dataGridReturn: List<CharArray>, boardSize: Int) {
        val chars = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
        for (rowChar in 0 until boardSize) {
            for (colChar in 0 until boardSize) {
                if(dataGridReturn[rowChar][colChar] == ' ') {
                    dataGridReturn[rowChar][colChar] =
                        chars[floor(Math.random() * chars.length).toInt()]
                }
            }
        }
    }

}