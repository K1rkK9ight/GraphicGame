package com.example.findwordgame

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {

    @Test
    fun test1() {
        val boardSize = 8
        val resultSize = 9
        val testList = BaseOfPossibleWords().createRandomBoard(boardSize)
        assertEquals(resultSize, testList.size)
    }

    @Test
    fun test2() {
        val boardSize = 8
        val resultLength = (0..38)
        var count = 0
        BaseOfPossibleWords().createRandomBoard(boardSize).forEach { count += it.length }
        assert(count in resultLength)
    }

    @Test
    fun test3() {
        val boardSize = 10
        val resultSize = 9
        val testList = BaseOfPossibleWords().createRandomBoard(boardSize)
        assertEquals(resultSize, testList.size)
    }
    @Test
    fun test4() {
        val boardSize = 10
        val resultLength = (0..55)
        var count = 0
        BaseOfPossibleWords().createRandomBoard(boardSize).forEach { count += it.length }
        assert(count in resultLength)
    }

    @Test
    fun test5() {
        val list = listOf("МЕДАЛЬ", "ЯМА", "ОБОЙМА", "ЛЕВ", "КАПОТ", "СТРУЯ",
            "ЛОЖЕ", "МИНА", "ЯД")
        val resultSize = 9
        val testList = BaseOfPossibleWords().createSupList(list)
        assertEquals(resultSize, testList.size)
    }

    @Test
    fun test6() {
        val list = listOf("МЕДАЛЬ")
        val resultSize = 1
        val testList = BaseOfPossibleWords().createSupList(list)
        assertEquals(resultSize, testList.size)
    }

    @Test
    fun test7() {
        val list = listOf("ЁЖ", "КОТ", "БОЛТ", "МИНУС", "АРМЯН", "НАПИТОК",
            "АД", "ГОЛУБЧИК", "ЧАС", "МАМА", "МУЗЫКА", "РЁВ", "КАНЦЛЕР", "ЛЕВ", "ВОЛЕЙБОЛ")
        val resultSize = 15
        val testList = BaseOfPossibleWords().createSupList(list)
        assertEquals(resultSize, testList.size)
    }

    @Test
    fun test8() {
        val list = listOf("ЁЖ")
        val resultSize = 1
        val testList = BaseOfPossibleWords().createSupList(list)
        assertEquals(resultSize, testList.size)
    }

}
