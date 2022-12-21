package day21

import getFileLines

sealed class Calculation {
    data class Value(val value: Long) : Calculation()
    data class DependsOnMonkeys(
        val firstMonkeyId: String,
        val secondMonkeyId: String,
        var operator: String
    ) : Calculation() {
        fun calculate(firstMonkeyValue: Long, secondMonkeyValue: Long) = when (operator) {
            "+" -> firstMonkeyValue + secondMonkeyValue
            "-" -> firstMonkeyValue - secondMonkeyValue
            "*" -> firstMonkeyValue * secondMonkeyValue
            "/" -> firstMonkeyValue / secondMonkeyValue
            else -> error("Oh no")
        }
    }
}

typealias Monkeys = MutableMap<String, Calculation>

fun Monkeys.getIfValueOrNull(monkeyId: String): Calculation.Value? {
    val calculation = this[monkeyId]!!
    return if (calculation is Calculation.Value) {
        calculation
    } else {
        null
    }
}

fun Monkeys.areAllValue() = this.values.all { it is Calculation.Value }

private fun getMonkeys(): Monkeys {
    return getFileLines("day21.txt").associate {
        val (left, right) = it.split(": ")
        val rightAsInt = right.toLongOrNull()
        if (rightAsInt != null) {
            left to Calculation.Value(rightAsInt)
        } else {
            val (firstMonkeyId, operator, secondMonkeyId) = right.split(" ")
            left to Calculation.DependsOnMonkeys(firstMonkeyId, secondMonkeyId, operator)
        }
    }.toMutableMap()
}

fun part1() {
    val monkeys = getMonkeys()
    while (true) {
        val root = monkeys["root"]
        if (root is Calculation.Value) {
            println("Root is ${root.value}")
            break
        }
        for ((monkeyId, calculation) in monkeys) {
            if (calculation is Calculation.Value) {
                continue
            }
            calculation as Calculation.DependsOnMonkeys
            val firstMonkey = monkeys.getIfValueOrNull(calculation.firstMonkeyId) ?: continue
            val secondMonkey = monkeys.getIfValueOrNull(calculation.secondMonkeyId) ?: continue

            val result = calculation.calculate(firstMonkey.value, secondMonkey.value)
            monkeys[monkeyId] = Calculation.Value(result)
        }
    }
    val root = monkeys["root"]
}

fun part2() {
    val monkeys = getMonkeys()
    while (true) {
        var changes = false
        for ((monkeyId, calculation) in monkeys) {
            if (monkeyId == "root") {
                continue
            }
            if (calculation is Calculation.Value) {
                continue
            }

            calculation as Calculation.DependsOnMonkeys
            if (calculation.firstMonkeyId == "humn") {
                continue
            }
            if (calculation.secondMonkeyId == "humn") {
                continue
            }
            val firstMonkey = monkeys.getIfValueOrNull(calculation.firstMonkeyId) ?: continue
            val secondMonkey = monkeys.getIfValueOrNull(calculation.secondMonkeyId) ?: continue

            val result = calculation.calculate(firstMonkey.value, secondMonkey.value)
            changes = true
            monkeys[monkeyId] = Calculation.Value(result)
        }
        if (!changes) {
            break
        }
    }
    for ((monkeyId, c) in monkeys) {
        println("$monkeyId: $c")
    }
    val rootCalculation = (monkeys["root"] as Calculation.DependsOnMonkeys)
    var equation = "${rootCalculation.firstMonkeyId} = ${rootCalculation.secondMonkeyId}"
    while (true) {
        var changes = false
        for ((monkeyId, calculation) in monkeys) {
            if (monkeyId == "root") {
                continue
            }
            if (monkeyId == "humn") {
                if (equation.contains(monkeyId)) {
                    if (calculation is Calculation.Value) {
                        equation = equation.replace(monkeyId, "X")
                        changes = true
                    }
                }
                continue
            }
            if (equation.contains(monkeyId)) {
                if (calculation is Calculation.Value) {
                    equation = equation.replace(monkeyId, "${calculation.value}")
                } else if (calculation is Calculation.DependsOnMonkeys) {
                    equation = equation.replace(monkeyId, "(${calculation.firstMonkeyId} ${calculation.operator} ${calculation.secondMonkeyId})")
                }
                changes = true
            }
        }
        if (!changes) {
            break
        }
    }
    println(equation)
}

fun main() {
//    part1()
    part2()
}