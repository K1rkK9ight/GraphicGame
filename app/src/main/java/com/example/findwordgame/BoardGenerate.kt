package com.example.findwordgame

import kotlin.random.Random


class BoardGenerate {
    private var seed: Long = 0
    private var random: Random? = null
    fun boardGenerate(seed: Long) {
        this.seed = seed
        reinit()
    }

    fun reinit() {
        random = Random(seed)
    }

    fun generate(max: Int): Int {
        return random!!.nextInt(max)
    }
}