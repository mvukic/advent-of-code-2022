package day9

import getFileLines
import kotlin.math.pow
import kotlin.math.sqrt

typealias Point = Pair<Int, Int>

fun part1() {
    val movements = getFileLines("day9.txt")
    val head = mutableListOf(Pair(0, 0))
    val tail = mutableListOf(Pair(0, 0))
    for (movement in movements) {
        println(movement)
        val (direction, steps) = movement.split(" ").let {
            Pair(it[0], it[1].toInt())
        }
        for (step in 1..steps) {
            // Move head
            val currentHead = head.last()
            val newHead = when (direction) {
                "R" -> Pair(currentHead.first + 1, currentHead.second)
                "L" -> Pair(currentHead.first - 1, currentHead.second)
                "U" -> Pair(currentHead.first, currentHead.second + 1)
                "D" -> Pair(currentHead.first, currentHead.second - 1)
                else -> error("Oh no")
            }
            head.add(newHead)

            // Move tail
            val currentTail = tail.last()
            val distance = newHead distanceTo currentTail
            println(distance)
            if (distance == 0.0 || distance == 1.0 || distance == sqrt(2.0)) {
                println("Not moving")
            } else if (distance == 2.0) {
                // Move in column/row
                println("Moving in column/row")
                if (newHead.first - currentTail.first == 2) {
                    tail.add(Pair(currentTail.first + 1, currentTail.second))
                } else if (newHead.first - currentTail.first == -2) {
                    tail.add(Pair(currentTail.first - 1, currentTail.second))
                } else if (newHead.second - currentTail.second == 2) {
                    tail.add(Pair(currentTail.first, currentTail.second + 1))
                } else if (newHead.second - currentTail.second == -2) {
                    tail.add(Pair(currentTail.first, currentTail.second - 1))
                }
            } else {
                // Move diagonal
                println("Moving diagonally")
                if (newHead.first > currentTail.first && newHead.second > currentTail.second) {
                    tail.add(Pair(currentTail.first + 1, currentTail.second + 1))
                } else if (newHead.first < currentTail.first && newHead.second < currentTail.second) {
                    tail.add(Pair(currentTail.first - 1, currentTail.second - 1))
                } else if (newHead.first > currentTail.first && newHead.second < currentTail.second) {
                    tail.add(Pair(currentTail.first + 1, currentTail.second - 1))
                } else if (newHead.first < currentTail.first && newHead.second > currentTail.second) {
                    tail.add(Pair(currentTail.first - 1, currentTail.second + 1))
                }
            }

            println("New head: ${head.last()}")
            println("New tail: ${tail.last()}")
            println()
        }
    }

    val count = tail.map { "${it.first}-${it.second}" }.distinct().size
    println(count)

}

fun part2() {
    val movements = getFileLines("day9.txt")
    val head = mutableListOf(Pair(0, 0))
    val tail1 = mutableListOf(Pair(0, 0))
    val tail2 = mutableListOf(Pair(0, 0))
    val tail3 = mutableListOf(Pair(0, 0))
    val tail4 = mutableListOf(Pair(0, 0))
    val tail5 = mutableListOf(Pair(0, 0))
    val tail6 = mutableListOf(Pair(0, 0))
    val tail7 = mutableListOf(Pair(0, 0))
    val tail8 = mutableListOf(Pair(0, 0))
    val tail9 = mutableListOf(Pair(0, 0))

    for (movement in movements) {
        val (direction, steps) = movement.split(" ").let {
            Pair(it[0], it[1].toInt())
        }
        for (step in 1..steps) {
            println(movement)
            // Move head
            val currentHead = head.last()
            val newHead = when (direction) {
                "R" -> Pair(currentHead.first + 1, currentHead.second)
                "L" -> Pair(currentHead.first - 1, currentHead.second)
                "U" -> Pair(currentHead.first, currentHead.second + 1)
                "D" -> Pair(currentHead.first, currentHead.second - 1)
                else -> error("Oh no")
            }
            head.add(newHead)

            getNewTailFromHead(newHead, tail1.last())?.let {
                tail1.add(it)
            }

            getNewTailFromHead(tail1.last(), tail2.last())?.let {
                tail2.add(it)
            }

            getNewTailFromHead(tail2.last(), tail3.last())?.let {
                tail3.add(it)
            }

            getNewTailFromHead(tail3.last(), tail4.last())?.let {
                tail4.add(it)
            }

            getNewTailFromHead(tail4.last(), tail5.last())?.let {
                tail5.add(it)
            }

            getNewTailFromHead(tail5.last(), tail6.last())?.let {
                tail6.add(it)
            }

            getNewTailFromHead(tail6.last(), tail7.last())?.let {
                tail7.add(it)
            }

            getNewTailFromHead(tail7.last(), tail8.last())?.let {
                tail8.add(it)
            }

            getNewTailFromHead(tail8.last(), tail9.last())?.let {
                tail9.add(it)
            }
        }
    }
    val count = tail9.map { "${it.first}-${it.second}" }.distinct().size
    println(count)
}

fun getNewTailFromHead(head: Point, tail: Point): Point? {
    val distance = head distanceTo tail
    if (distance == 0.0 || distance == 1.0 || distance == sqrt(2.0)) {
        return null
    } else if (distance == 2.0) {
        if (head.first - tail.first == 2) {
            return Pair(tail.first + 1, tail.second)
        } else if (head.first - tail.first == -2) {
            return Pair(tail.first - 1, tail.second)
        } else if (head.second - tail.second == 2) {
            return Pair(tail.first, tail.second + 1)
        } else if (head.second - tail.second == -2) {
            return Pair(tail.first, tail.second - 1)
        }
    } else {
        if (head.first > tail.first && head.second > tail.second) {
            return Pair(tail.first + 1, tail.second + 1)
        } else if (head.first < tail.first && head.second < tail.second) {
            return Pair(tail.first - 1, tail.second - 1)
        } else if (head.first > tail.first && head.second < tail.second) {
            return Pair(tail.first + 1, tail.second - 1)
        } else if (head.first < tail.first && head.second > tail.second) {
            return Pair(tail.first - 1, tail.second + 1)
        }
    }
    error("Oh no")
}


infix fun Point.distanceTo(other: Point): Double {
    return sqrt(
        (first.toDouble() - other.first.toDouble()).pow(2) + (second.toDouble() - other.second.toDouble()).pow(
            2
        )
    )
}

fun main() {
//    part1()
    part2()
}



