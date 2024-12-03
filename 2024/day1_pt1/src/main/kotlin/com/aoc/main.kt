@file:JvmName("MainKt")

package com.aoc

import java.io.BufferedReader
import java.io.InputStream
import kotlin.math.abs

fun main() {
    val reader = getResourceAsText("/input.txt")!!.bufferedReader()
    val pairs = splitLineToPairs(reader)

    println(processPart1(pairs))
}


fun processPart1(pairs: List<Pair<Int,Int>>): Int {
    var diff: Int = 0

    pairs.forEach { (first, second) ->
        diff += abs(first - second)
    }

    return diff
}

fun splitLineToPairs(reader: BufferedReader): List<Pair<Int,Int>> {
    lateinit var line: String
    val spacesRegex = Regex("\\s+")
    val leftNumbers = mutableListOf<Int>()
    val rightNumbers = mutableListOf<Int>()

    reader.lines().forEach { line ->
        val textNumbers = line.replace(spacesRegex,",").split(',')
        leftNumbers.add(textNumbers[0].toInt())
        rightNumbers.add(textNumbers[1].toInt())
    }

    leftNumbers.sort()
    rightNumbers.sort()

    return leftNumbers.mapIndexed { idx, it ->
        Pair(leftNumbers[idx], rightNumbers[idx])
    }
}

fun getResourceAsText(path: String): InputStream? =
    object {}.javaClass.getResource(path)?.openStream()