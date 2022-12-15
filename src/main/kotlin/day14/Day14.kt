package day14

import getFileLines

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromString(str: String): Point {
            return str.split(",").let {
                Point(it[0].toInt(), it[1].toInt())
            }
        }
    }
}

data class Line(val points: List<Point>)

typealias Canvas = Array<Array<String>>

fun part1() {
    var lines = getLines()
    val minX = lines.minOf { line -> line.points.minOf { it.x } }
    val maxX = lines.maxOf { line -> line.points.maxOf { it.x } }
    val maxY = lines.maxOf { line -> line.points.maxOf { it.y } }
    println("Min X: $minX, Max X: $maxX, Max Y: $maxY")
    lines = normalizePoints(lines, minX)

    val canvas: Canvas = Array(maxY + 1) {
        Array(maxX - minX + 1) { "." }
    }

    drawLines(canvas, lines)
    printCanvas(canvas)

    var sandCount = 1
    while (true) {
        try {
            val sandPoint = Point(500 - minX, 0)
            val nextAvailablePoint = getNextAvailablePoint(canvas, sandPoint)
            drawPoint(canvas, nextAvailablePoint)
            printCanvas(canvas)
            println()
            sandCount++
        } catch (e: Exception) {
            println("Sand count: ${sandCount - 1}")
            break
        }
    }

}

fun getNextAvailablePoint(canvas: Canvas, point: Point): Point {
//    println(point)
    val lowestPoint = getLowestPoint(canvas, point)

    // Try to move left
    val bottomLeft = lowestPoint.copy(x = lowestPoint.x - 1, y = lowestPoint.y + 1)
    val canOccupyBottomLeft = canOccupy(canvas, bottomLeft)
    if (canOccupyBottomLeft) {
//        println("Going left to $bottomLeft")
        return getNextAvailablePoint(canvas, bottomLeft)
    }

    // Try to move right
    val bottomRight = lowestPoint.copy(x = lowestPoint.x + 1, y = lowestPoint.y + 1)
    val canOccupyBottomRight = canOccupy(canvas, bottomRight)
    if (canOccupyBottomRight) {
//        println("Going right to $bottomRight")
        return getNextAvailablePoint(canvas, bottomRight)
    }

//    println("Can't move so returning $lowestPoint")
    return lowestPoint
}

fun getLowestPoint(canvas: Canvas, point: Point): Point {
//    println("Getting lowest for $point")
    val bottom = point.copy(y = point.y + 1)
    return if (canOccupy(canvas, bottom)) {
//        println("Occupying lower point $bottom")
        getLowestPoint(canvas, bottom)
    } else {
        point
    }
}

fun canOccupy(canvas: Array<Array<String>>, point: Point): Boolean {
    if (point.y >= canvas.size) {
        error("Out of rang on y")
    }
    if (point.x < 0 || point.x >= canvas.first().size) {
        error("Out of rang on x")
    }
    return canvas[point.y][point.x] == "."
}


fun part2() {
    val lines = getLines()
}

fun drawLines(canvas: Canvas, lines: List<Line>) {
    for (line in lines) {
        for (pairs in line.points.windowed(2)) {
            val allPoints = getPointsBetween(pairs[0], pairs[1])
            for (point in allPoints) {
                canvas[point.y][point.x] = "#"
            }
        }
    }
}

fun getPointsBetween(a: Point, b: Point): List<Point> {
    return if (a.x == b.x) {
        if (a.y < b.y) {
            (a.y..b.y).map { Point(a.x, it) }
        } else {
            (b.y..a.y).map { Point(a.x, it) }
        }
    } else {
        if (a.x < b.x) {
            (a.x..b.x).map { Point(it, a.y) }
        } else {
            (b.x..a.x).map { Point(it, a.y) }
        }
    }
}

fun drawPoint(canvas: Canvas, point: Point) {
    canvas[point.y][point.x] = "o"
}

fun normalizePoints(lines: List<Line>, minX: Int): List<Line> {
    return lines.map { line -> Line(line.points.map { it.copy(x = it.x - minX) }) }
}

fun printCanvas(canvas: Canvas) {
    for (line in canvas) {
        println(line.joinToString(""))
    }
}

fun getLines(): List<Line> {
    return getFileLines("day14.txt")
        .map { line -> line.split(" -> ") }
        .map { line -> line.map { Point.fromString(it) } }
        .map { line -> Line(line) }
}

fun main() {
    part1()
    part2()
}



