package day3

import getFileLines

val priorities: Map<Char, Int> = (('a'..'z').zip(1..26) + ('A'..'Z').zip(27..52)).toMap()

fun part1() {
    val rucksacks = getFileLines("day3.txt")
        .map { it.windowed(it.length / 2, it.length / 2) }
        .map { (c1, c2) -> c1.toList() intersect c2.toList().toSet() }
        .mapNotNull { priorities[it.first()] }
        .sum()

    println(rucksacks)
}

fun part2() {
    val rucksacks = getFileLines("day3.txt")
        .asSequence()
        .windowed(3, 3)
        .map { (r1, r2, r3) -> r1.toList() intersect r2.toList().toSet() intersect r3.toList().toSet() }
        .mapNotNull { priorities[it.first()] }
        .sum()

    println(rucksacks)
}

fun main() {
    part1()
    part2()
}



