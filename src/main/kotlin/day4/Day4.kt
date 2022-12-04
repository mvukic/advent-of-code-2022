package day4

import getFileLines

fun part1() {
    val pairs = getFileLines("day4.txt")
        .asSequence()
        .map { it.split(",") }
        .map { (sections1, sections2) ->
            val r1 = (sections1.split("-")[0].toInt()..sections1.split("-")[1].toInt()).toList()
            val r2 = (sections2.split("-")[0].toInt()..sections2.split("-")[1].toInt()).toList()
            r1.containsAll(r2) || r2.containsAll(r1)
        }
        .count { it }
    println(pairs)
}

fun part2() {
    val pairs = getFileLines("day4.txt")
        .asSequence()
        .map { it.split(",") }
        .map { (sections1, sections2) ->
            val r1 = (sections1.split("-")[0].toInt()..sections1.split("-")[1].toInt()).toList()
            val r2 = (sections2.split("-")[0].toInt()..sections2.split("-")[1].toInt()).toList()
            (r1 intersect r2.toSet()).isNotEmpty()
        }
        .count { it }
    println(pairs)

}

fun main() {
    part1()
    part2()
}



