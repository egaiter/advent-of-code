@file:JvmName("MainKt")

package com.aoc

import java.io.InputStream
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val reports = getReportsFromStream(getResourceAsStream("/input.txt"))
    var safeCount = 0;
    reports.forEach {
        if (processReport(it)) safeCount += 1
    }
    println(safeCount)
}

fun processReport(report: List<Int>): Boolean {
    val direction = (report[1] - report[0]).sign
    for (i  in 0 until report.size-1) {
        val diff = (report[i+1] - report[i])
        if (diff.sign != direction) return false
        if (abs(diff) > 3 || abs(diff) < 1) return false
    }
    return true
}

fun getReportsFromStream(stream: InputStream): List<List<Int>> {
    return stream.bufferedReader().lines().map {
        it.split(" ").map(String::toInt)
    }.toList()
}

fun getResourceAsStream(path: String): InputStream =
    object {}.javaClass.getResource(path)?.openStream()!!