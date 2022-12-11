package day11

import getFileLines
import split
import java.math.BigInteger

data class Monkey(
    val id: Int,
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val test: (BigInteger) -> Boolean,
    val trueMonkeyId: Int,
    val falseMonkeyId: Int,
    var inspected: BigInteger = BigInteger.ZERO
)

fun getMonkeys(): List<Monkey> {
    return getFileLines("day11.txt")
        .split { it.isBlank() }
        .map { getMonkey(it) }
}

fun getMonkey(lines: List<String>): Monkey {
    return Monkey(
        id = lines[0].split(" ", ":")[1].toInt(),
        items = getItems(lines[1]),
        operation = getOperation(lines[2]),
        test = getTest(lines[3]),
        trueMonkeyId = getTrueOrFalseMonkeyId(lines[4]),
        falseMonkeyId = getTrueOrFalseMonkeyId(lines[5])
    )
}

fun getItems(line: String): MutableList<BigInteger> {
    return line.split(":")[1].split(",").map { it.trim().toBigInteger() }.toMutableList()
}

fun getTrueOrFalseMonkeyId(line: String): Int {
    return line.split(" ").last().toInt()
}

fun getTest(line: String): (BigInteger) -> Boolean {
    return { it.mod(line.split(" ").last().toBigInteger()).equals(BigInteger.ZERO) }
}

fun getMonkeyWithId(monkeys: List<Monkey>, id: Int): Monkey {
    return monkeys.first { it.id == id }
}

fun getOperation(line: String): (BigInteger) -> BigInteger {
    val (left, op, right) = line.split("=")[1].split(" ").filter { it.isNotBlank() }
    return { old ->
        when (op) {
            "+" -> {
                if (right == "old") {
                    old + old
                } else {
                    old + right.toBigInteger()
                }
            }

            "*" -> {
                if (right == "old") {
                    old * old
                } else {
                    old * right.toBigInteger()
                }
            }

            else -> error("Oh no")
        }
    }
}

fun part1() {
    val monkeys = getMonkeys()
    for (roundValue in (1..20)) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                val newItem = monkey.operation(item).divide(BigInteger.valueOf(3))
                if (monkey.test(newItem)) {
                    getMonkeyWithId(monkeys, monkey.trueMonkeyId).items.add(newItem)
                } else {
                    getMonkeyWithId(monkeys, monkey.falseMonkeyId).items.add(newItem)
                }
            }
            monkey.inspected += monkey.items.size.toBigInteger()
            monkey.items.clear()
        }
//        println("=== $roundValue ===")
//        monkeys.forEach { println("Monkey ${it.id}: ${it.items}") }
    }
//    monkeys.forEach { println("Monkey ${it.id}: ${it.inspected}") }
    val (t1, t2) = monkeys.map { it.inspected }.sortedDescending().take(2)
    println("Part 1: ${t1 * t2}")
}

fun part2() {
    val monkeys = getMonkeys()
    for (roundValue in (1..10000)) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                val newItem = monkey.operation(item)
                if (monkey.test(newItem)) {
                    getMonkeyWithId(monkeys, monkey.trueMonkeyId).items.add(newItem)
                } else {
                    getMonkeyWithId(monkeys, monkey.falseMonkeyId).items.add(newItem)
                }
            }
            monkey.inspected += monkey.items.size.toBigInteger()
            monkey.items.clear()
        }

        if (roundValue % 1000 == 0) {
            println("=== $roundValue ===")
            monkeys.forEach { println("Monkey ${it.id}: ${it.inspected}") }
        }
    }

    val (t1, t2) = monkeys.map { it.inspected }.sortedDescending().take(2)
    println(t1 * t2)
}

fun main() {
//    part1()
    part2()
}



