package day8

import getFileLines

data class Tree(
    val height: Int,
    var visible: Boolean,
    var scenicScore: Int = 0
)

fun part1() {
    val lines = getFileLines("day8.txt")
    val forest = lines.map {
        it.toCharArray().map { tree -> Tree(tree.toString().toInt(), false) }
    }
    val width = forest.first().size
    val height = forest.size

    for (rowIndex in 0 until height) {
        for (columnIndex in 0 until width) {
            if (rowIndex == 0 || rowIndex == height - 1) {
                forest[rowIndex][columnIndex].visible = true
            } else if (columnIndex == 0 || columnIndex == width - 1) {
                forest[rowIndex][columnIndex].visible = true
            } else {
                forest[rowIndex][columnIndex].visible = isVisible(rowIndex, columnIndex, forest)
            }
        }
    }

    forest.forEach { row ->
        row.forEach {
            print(if (it.visible) "T" else "F")
        }
        println()
    }

    val visible = forest.sumOf { row -> row.count { column -> column.visible } }
    println(visible)
}

fun isVisible(rowIndex: Int, columnIndex: Int, forest: List<List<Tree>>): Boolean {
    val tree = forest[rowIndex][columnIndex]

    val column = getColumn(forest, columnIndex)
    val topColumn = column.take(rowIndex)
    val bottomColumn = column.drop(rowIndex + 1)

    val row = getRow(forest, rowIndex)
    val leftRow = row.take(columnIndex)
    val rightRow = row.drop(columnIndex + 1)

    return topColumn.all { it.height < tree.height } ||
            bottomColumn.all { it.height < tree.height } ||
            leftRow.all { it.height < tree.height } ||
            rightRow.all { it.height < tree.height }
}

fun getScenicScore(rowIndex: Int, columnIndex: Int, forest: List<List<Tree>>): Int {
    val tree = forest[rowIndex][columnIndex]
    println("$tree <$rowIndex, $columnIndex>")

    val column = getColumn(forest, columnIndex)
    val topColumn = column.take(rowIndex)
    val bottomColumn = column.drop(rowIndex + 1)

    val row = getRow(forest, rowIndex)
    val leftRow = row.take(columnIndex)
    val rightRow = row.drop(columnIndex + 1)

    var topColumnScenicScore = topColumn.reversed().indexOfFirst { it.height >= tree.height }
    topColumnScenicScore = if ( topColumnScenicScore == -1) {
        topColumn.size
    } else {
        topColumnScenicScore + 1
    }
    println("Top: $topColumnScenicScore")

    var bottomColumnScenicScore = bottomColumn.indexOfFirst { it.height >= tree.height }
    bottomColumnScenicScore = if ( bottomColumnScenicScore == -1) {
        bottomColumn.size
    } else {
        bottomColumnScenicScore + 1
    }
    println("Bottom: $bottomColumnScenicScore")

    var leftRowScenicScore = leftRow.reversed().indexOfFirst { it.height >= tree.height }
    leftRowScenicScore = if ( leftRowScenicScore == -1) {
        leftRow.size
    } else {
        leftRowScenicScore + 1
    }
    println("Left: $leftRowScenicScore")

    var rightRowScenicScore = rightRow.indexOfFirst { it.height >= tree.height }
    rightRowScenicScore = if ( rightRowScenicScore == -1) {
        rightRow.size
    } else {
        rightRowScenicScore + 1
    }
    println("Right: $rightRowScenicScore")
    println()

    return topColumnScenicScore * bottomColumnScenicScore * leftRowScenicScore * rightRowScenicScore
}

fun getColumn(forest: List<List<Tree>>, columnIndex: Int): List<Tree> {
    return forest.map { row -> row[columnIndex] }
}

fun getRow(forest: List<List<Tree>>, rowIndex: Int): List<Tree> {
    return forest[rowIndex]
}

fun part2() {
    val lines = getFileLines("day8.txt")
    val forest = lines.map {
        it.toCharArray().map { tree -> Tree(tree.toString().toInt(), false) }
    }
    val width = forest.first().size
    val height = forest.size

    for (rowIndex in 0 until height) {
        for (columnIndex in 0 until width) {
            if (rowIndex == 0 || rowIndex == height - 1) {
                forest[rowIndex][columnIndex].visible = true
            } else if (columnIndex == 0 || columnIndex == width - 1) {
                forest[rowIndex][columnIndex].visible = true
            } else {
                forest[rowIndex][columnIndex].visible = isVisible(rowIndex, columnIndex, forest)
                forest[rowIndex][columnIndex].scenicScore = getScenicScore(rowIndex, columnIndex, forest)
            }
        }
    }

    forest.forEach { row ->
        row.forEach {
            print(" ${it.scenicScore} ")
        }
        println()
    }

    val highestScenicScore = forest.maxOf { row -> row.maxOf { column -> column.scenicScore } }
    println(highestScenicScore)
}

fun main() {
//    part1()
    part2()
}