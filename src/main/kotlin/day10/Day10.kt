package day10

import getFileLines

sealed class Command(val cycles: Int) {
    data class Add(val value: Int) : Command(2)
    object Noop : Command(1) {
        override fun toString() = "Noop"
    }
}

fun getCommands(): List<Command> {
    return getFileLines("day10.txt").map {
        val parts = it.split(" ")
        if (parts.size == 2) {
            Command.Add(parts[1].toInt())
        } else {
            Command.Noop
        }
    }
}

fun part1() {
    val commands = getCommands()
    var currentCycle = 0
    var registerX = 1
    var signalStrength = 0
    for (command in commands) {
        when (command) {
            is Command.Noop -> {
                currentCycle += command.cycles
                signalStrength += getSignalStrength(currentCycle, registerX)
            }

            is Command.Add -> {
                for (cycle in 1..command.cycles) {
                    currentCycle += 1
                    signalStrength += getSignalStrength(currentCycle, registerX)
                }
                registerX += command.value
            }
        }
        println(signalStrength)
    }
}

fun getSignalStrength(currentCycle: Int, registerX: Int): Int {
    return when (currentCycle) {
        20 -> currentCycle * registerX
        60 -> currentCycle * registerX
        100 -> currentCycle * registerX
        140 -> currentCycle * registerX
        180 -> currentCycle * registerX
        220 -> currentCycle * registerX
        else -> 0
    }
}

fun part2() {
    val commands = getCommands()
    val crt = Array(6) {
        Array(40) { "." }
    }
    var currentCycle = 0
    var registerX = 1
    for (command in commands) {
        println(command)
        when (command) {
            is Command.Noop -> {
                currentCycle += command.cycles
                val row = getRow(currentCycle)
                val column = getColumn(currentCycle, row)
                val sprite = Triple(registerX - 1, registerX, registerX + 1)
                println("Cycle: $currentCycle, X: $registerX, ($row, $column), $sprite")
                draw(row, column, sprite, crt)
            }

            is Command.Add -> {
                for (cycle in 1..command.cycles) {
                    currentCycle += 1
                    val row = getRow(currentCycle)
                    val column = getColumn(currentCycle, row)
                    val sprite = Triple(registerX - 1, registerX, registerX + 1)
                    println("Cycle: $currentCycle, X: $registerX, ($row, $column), $sprite")
                    draw(row, column, sprite, crt)
                }
                registerX += command.value
            }
        }
    }
    crt.forEach { row ->
        row.forEach { column ->
            print(column)
        }
        println()
    }
}

fun getRow(cycle: Int): Int {
    return when(cycle) {
        in 1 .. 40 -> 0
        in 41 .. 80 -> 1
        in 81 .. 120 -> 2
        in 121 .. 160 -> 3
        in 161 .. 200 -> 4
        in 201 .. 240 -> 5
        else -> error("Oh no")
    }
}

fun getColumn(cycle: Int, row: Int): Int {
    return cycle - 1 - 40 * row
}

fun draw(row: Int, column: Int, sprite: Triple<Int, Int, Int>, crt: Array<Array<String>>) {
    if (column == sprite.first || column == sprite.second || column == sprite.third) {
        crt[row][column] = "#"
    } else {
        crt[row][column] = "."
    }
}

fun main() {
//    part1()
    part2()
}



