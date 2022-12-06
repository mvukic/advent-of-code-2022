package day5

import getFileLines
import java.util.Stack

fun part1() {
    val stacks: MutableList<Stack<String>> = mutableListOf(
        Stack(),
        Stack(),
        Stack()
    )
    val lines = getFileLines("day5.txt")
    val emptyLineIndex = lines.indexOfFirst { it.isBlank() }
    lines.subList(0, emptyLineIndex)
        .map { it.replace("[", " ").replace("]", " ") }
        .map { it.toList() }
        .map { it.windowed(size = 4, step = 4, partialWindows = true).map { c -> c.joinToString("").replace(" ", "") } }
        .dropLast(1)
        .forEach {
            it.forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    stacks[index].push(s)
                }
            }
        }

//    stacks.forEach { println(it) }
    println()

    lines.subList(emptyLineIndex, lines.size).filter { it.isNotBlank() }
        .forEach {
            val parts = it.split(" ")
            val count = parts[1].toInt()
            val startColumn = parts[3].toInt()
            val endColumn = parts[5].toInt()
            for (c in (1..count)) {
                stacks[endColumn - 1].insertElementAt(stacks[startColumn - 1].first(), 0)
                stacks[startColumn - 1].removeFirst()
            }
//            stacks.forEach { s -> println(s) }
//            println()
        }

//    stacks.forEach { println(it) }

    println(stacks.joinToString("") { it.first() })

}

fun part2() {
    val stacks: MutableList<Stack<String>> = mutableListOf(
        Stack(),
        Stack(),
        Stack(),
        Stack(),
        Stack(),
        Stack(),
        Stack(),
        Stack(),
        Stack(),
    )
    val lines = getFileLines("day5.txt")
    val emptyLineIndex = lines.indexOfFirst { it.isBlank() }
    lines.subList(0, emptyLineIndex)
        .map { it.replace("[", " ").replace("]", " ") }
        .map { it.toList() }
        .map { it.windowed(size = 4, step = 4, partialWindows = true).map { c -> c.joinToString("").replace(" ", "") } }
        .dropLast(1)
        .forEach {
            it.forEachIndexed { index, s ->
                if (s.isNotBlank()) {
                    stacks[index].push(s)
                }
            }
        }

    stacks.forEach { println(it) }
    println()

    lines.subList(emptyLineIndex, lines.size).filter { it.isNotBlank() }
        .forEach {
            println(it)
            val parts = it.split(" ")
            val count = parts[1].toInt()
            val startColumn = parts[3].toInt()
            val endColumn = parts[5].toInt()
            val subList = stacks[startColumn - 1].subList(0, count)
            println("Sub list: $subList")
            subList.reversed().forEach { slItem -> stacks[endColumn - 1].insertElementAt(slItem, 0) }
            for (c in (1..count)) {
                stacks[startColumn - 1].removeFirst()
            }

            stacks.forEach { s -> println(s) }
            println()
        }

    stacks.forEach { println(it) }

    println(stacks.joinToString("") { it.first() })
}

fun main() {
//    part1()
    part2()
}