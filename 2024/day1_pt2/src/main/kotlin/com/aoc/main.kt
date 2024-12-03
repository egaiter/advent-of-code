@file:JvmName("MainKt")

package com.aoc

import java.io.BufferedReader
import java.io.InputStream
import kotlin.math.abs

fun main() {
    val reader = getResourceAsText("/input.txt")!!.bufferedReader()
    val data = splitLineToData(reader)
    val reader2 = getResourceAsText("/input.txt")!!.bufferedReader()
    val lists = splitLineToLists(reader2)

    println(processPart2(data.first, data.second))
    println(processPart2Alt(lists.first, lists.second))
}


fun processPart2(left: List<Int>, right: Map<Int,Int>): Int {
    var similarity = 0

    left.forEach {
        similarity += it * (right[it] ?: 0)
    }

    return similarity
}

fun processPart2Alt(left: List<Int>, right: List<Int>): Int {
    var similarity = 0
    var j = 0
    var currentCount = 1

    while(right[j] < left[0]) {
        j++
    }
    var current = right[j]

    while(right[j+1] == current) {
        j++
        currentCount++
    }

    for(i in left.indices) {
        while (j < right.size-1 && right[j] < left[i]) {
            j++
            current = right[j]
            currentCount = 1
            while(j < right.size-1 && right[j+1] == current) {
                j++
                currentCount++
            }
        }
        if (right[j] == left[i]) {
            similarity += left[i] * currentCount
        }
    }

    return similarity
}

fun splitLineToData(reader: BufferedReader): Pair<List<Int>,Map<Int,Int>> {
    lateinit var line: String
    val spacesRegex = Regex("\\s+")
    val leftNumbers = mutableListOf<Int>()
    val rightDict = mutableMapOf<Int, Int>()
    val rightNumbers = mutableListOf<Int>()

    reader.lines().forEach { line ->
        val textNumbers = line.replace(spacesRegex,",").split(',')
        leftNumbers.add(textNumbers[0].toInt())
        rightNumbers.add(textNumbers[1].toInt())
    }

    leftNumbers.sort()
    rightNumbers.groupBy { it }.forEach { rightDict[it.key]= it.value.size }

    return Pair(leftNumbers, rightDict)
}

fun splitLineToLists(reader: BufferedReader): Pair<List<Int>,List<Int>> {
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
    return Pair(leftNumbers, rightNumbers)
}

fun getResourceAsStream(path: String): InputStream? =
    object {}.javaClass.getResource(path)?.openStream()