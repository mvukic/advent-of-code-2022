package day6

import getFileLines
import java.util.Stack

fun part1() {
    val line = getFileLines("day6.txt").first()
    val windows = line
        .toList()
        .windowed(size = 4, step = 1)
    for ((index, value) in windows.withIndex()) {
        if (value.distinct().size == value.size) {
            println(index + 4)
            println(value)
            break
        }
    }
}

fun part2() {
    val line = getFileLines("day6.txt").first()
    val windows = line
        .toList()
        .windowed(size = 14, step = 1)
    for ((index, value) in windows.withIndex()) {
        if (value.distinct().size == value.size) {
            println(index + 14)
            println(value)
            break
        }
    }
}

fun main() {
    part1()
    part2()
}