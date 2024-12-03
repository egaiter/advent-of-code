@file:JvmName("MainKt")

package com.aoc

import java.io.InputStream

fun main() {
    println("Hello, Kotlin/Native!")
}

fun getResourceAsStream(path: String): InputStream? =
    object {}.javaClass.getResource(path)?.openStream()