@file:JvmName("MainKt")

package com.aoc

import java.io.InputStream

fun main() {
    val stream = getResourceAsStream("/input.txt")
    val multiples = extractFunctions(stream!!.bufferedReader().readText())
    val result = multiples.map { it.first * it.second }.sum()
    println(result)

}

fun extractFunctions(input: String): List<Pair<Int,Int>> {
    val argsregex = Regex("\\d{1,3},\\d{1,3}")
    var endposition = 0
    val result = mutableListOf<Pair<Int,Int>>()
    var dontPosition = input.indexOf("don't()")
    var doPosition: Int = 0
    var position = input.indexOf("mul(")
    while(position >= 0) {
        while (position > dontPosition && dontPosition > 0) {
            doPosition = input.indexOf("do()", dontPosition)
            if (doPosition < 0) return result
            dontPosition = input.indexOf("don't()", doPosition)
            position = input.indexOf("mul(", doPosition)
            if (position < 0) return result
            println("do:" +doPosition.toString())
            println(dontPosition)
            println(position)
        }
        endposition = input.indexOf(")",position)
        if (endposition > 0) {
            val argSlice = input.slice(position+4..< endposition)
            if (argSlice.matches(argsregex)) {
                val nums = argSlice.split(",").map(String::toInt)
                result.add(Pair(nums[0],nums[1]))
            }
        }


        position = input.indexOf("mul(",position+1)
        println("pos:" +position.toString())
    }

    return result
}

fun getResourceAsStream(path: String): InputStream? =
    object {}.javaClass.getResource(path)?.openStream()