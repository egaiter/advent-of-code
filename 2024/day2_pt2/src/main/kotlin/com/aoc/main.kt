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

fun processReport(report: List<Int>, recurse: Boolean = true): Boolean {
    var prospectiveBad = false
    for (i in 0 ..< report.size-1) {
        val direction = (report[1] - report[0]).sign
        val diff = report[i+1] - report[i]
        var bad = false

        if (diff.sign != direction) bad = true
        if (abs(diff) > 3 || abs(diff) < 1) bad = true
        if (bad) {
            prospectiveBad = true
            if (recurse) {
                report.forEachIndexed { idx, _ ->
                    if (processReport(report.removeIndex(idx), false)) {
                         return true
                    }
                }
            }
        }
    }
    return !prospectiveBad
}

inline fun <reified T> List<T>.removeIndex(idx: Int): List<T> {
    return listOf(*this.slice(0..< idx).toTypedArray(),*this.slice(idx+1..< this.size).toTypedArray())
}

fun getReportsFromStream(stream: InputStream): List<List<Int>> {
    return stream.bufferedReader().lines().map {
        it.split(" ").map(String::toInt)
    }.toList()
}

fun getResourceAsStream(path: String): InputStream =
    object {}.javaClass.getResource(path)?.openStream()!!