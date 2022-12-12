package day12

import getFileLines
import java.io.File

typealias Nodes = MutableList<MutableList<String>>

fun readNodes(): Nodes {
    return getFileLines("day12.txt").mapIndexed { rowIndex, row ->
        row.toList().mapIndexed { columnIndex, column ->
            "$column-$rowIndex-$columnIndex"
        }.toMutableList()
    }.toMutableList()
}

fun getPoint(nodes: Nodes, label: String): Pair<Int, Int> {
    nodes.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, column ->
            if (column.startsWith(label)) {
                return rowIndex to columnIndex
            }
        }
    }
    error("Oh no")
}

val result = mutableListOf<String>()

fun part1() {
    val nodes = readNodes()
    val start = getPoint(nodes, "S")
    val end = getPoint(nodes, "E")

    nodes[start.first][start.second] = "a-${start.first}-${start.second}"
    nodes[end.first][end.second] = "z-${end.first}-${end.second}"

    result.add("MATCH (n) DETACH DELETE n;")
    // Create nodes
    nodes.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, column ->
            result.add(cypherCreateNode(column))
        }
    }

    // Connect nodes
    nodes.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, column ->
            nodes.getOrNull(rowIndex - 1)?.getOrNull(columnIndex)?.let {
                tryToConnect(column, it)
            }
            nodes.getOrNull(rowIndex + 1)?.getOrNull(columnIndex)?.let {
                tryToConnect(column, it)
            }
            nodes.getOrNull(rowIndex)?.getOrNull(columnIndex - 1)?.let {
                tryToConnect(column, it)
            }
            nodes.getOrNull(rowIndex)?.getOrNull(columnIndex + 1)?.let {
                tryToConnect(column, it)
            }
        }
    }

    result.add(cypherFindShortestPath(nodes[start.first][start.second], nodes[end.first][end.second]))

    File("C:\\Users\\Matija\\Downloads\\cypher.cypher").writeText(result.joinToString("\n"))
}

fun tryToConnect(node1: String, node2: String) {
    val label1 = node1.split("-").first().toCharArray().first()
    val label2 = node2.split("-").first().toCharArray().first()
     if (label1 > label2) {
         result.add(cypherCreateRelationship(node1, node2))
     } else if (label1 == label2) {
         result.add(cypherCreateRelationship(node1, node2))
     } else if (label1 == label2 - 1) {
         result.add(cypherCreateRelationship(node1, node2))
     }
}

fun cypherCreateNode(label: String): String {
    return "CREATE (n: Node { label: \"$label\"});"
}

fun cypherCreateRelationship(startLabel: String, endLabel: String): String {
    return "MATCH (start: Node { label: \"$startLabel\" }), (end: Node { label: \"$endLabel\" }) CREATE (start)-[:CONNECTED { cost: 1 }]->(end);"
}

fun cypherFindShortestPath(startLabel: String, endLabel: String): String {
    return "MATCH (start: Node { label: \"${startLabel}\" }), (end: Node { label: \"$endLabel\" }), p = shortestPath((start)-[:CONNECTED*]->(end)) RETURN p"
}

fun part2() {
//    val calories = getFileLines("day12.txt")
}

fun main() {
    part1()
    part2()
}



