package day1

import getFileLines

fun part1() {
    val calories = getFileLines("day1.txt")
    val maxSum = calories
        .joinToString(";")
        .split(";;")
        .maxOfOrNull { it.split(";").sumOf { c -> c.toInt() } }
    println(maxSum)
}

fun part2() {
    val calories = getFileLines("day1.txt")
    val maxSums = calories
        .joinToString(";")
        .split(";;")
        .map { it.split(";").sumOf { c -> c.toInt() } }
        .sortedDescending()
        .take(3)
        .sum()
    println(maxSums)
}

fun main() {
    part1()
    part2()
}



