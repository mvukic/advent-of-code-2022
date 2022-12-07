package day7

import getFileLines


data class Folder(
    val label: String,
    val files: MutableList<File> = mutableListOf(),
    val folders: MutableList<Folder> = mutableListOf(),
    var parent: Folder? = null
) {
    fun getOrCreteFolder(label: String): Folder {
        return folders.find { it.label == label } ?: Folder(label).also {
            it.parent = this
            folders.add(it)
        }
    }

    fun getOrCreteFile(label: String, size: String): File {
        return files.find { it.label == label } ?: File(label, size).also {
            files.add(it)
        }
    }

    fun size(): Long {
        return files.sumOf { it.size.toLong() } + folders.sumOf { it.size() }
    }

    fun sizeBelowLimit(limit: Long): Long {
        return if (size() <= limit) {
            size() + folders.sumOf { it.sizeBelowLimit(limit) }
        } else {
            folders.sumOf { it.sizeBelowLimit(limit) }
        }
    }

    fun getAsList(): List<Folder> {
        return listOf(this) + folders.flatMap { it.getAsList() }
    }

    fun print(indentSize: Int = 0) {
        repeat(indentSize) {
            print(" ")
        }
        print("- $label (dir)")
        println()
        folders.forEach { it.print(indentSize + 1) }
        files.forEach { it.print(indentSize + 1) }
    }
}

data class File(
    val label: String,
    val size: String
) {
    fun print(indentSize: Int = 0) {
        repeat(indentSize) {
            print(" ")
        }
        print("- $label (file, size = $size)")
        println()
    }
}

fun getTree(): Folder {
    var tree: Folder? = null
    var current: Folder? = null

    val outputs = getFileLines("day7.txt")
    var index = 0
    while (true) {
        if (index == outputs.size) {
            break
        }
        val line = outputs[index]
        val command = line.split(" ")
        if (command[0] == "$") {
            if (command[1] == "cd") {
                val targetDirName = command[2]
                if (tree == null) {
                    tree = Folder(targetDirName)
                    current = tree
                } else if (targetDirName == "..") {
                    current = current!!.parent
                } else {
                    current = current!!.getOrCreteFolder(targetDirName)
                }
                index++
            } else if (command[1] == "ls") {
                val files = outputs.drop(index + 1).takeWhile { !it.startsWith("$") }
                files.forEach {
                    val parts = it.split(" ")
                    if (parts[0] == "dir") {
                        current?.getOrCreteFolder(parts[1])
                    } else {
                        current?.getOrCreteFile(parts[1], parts[0])
                    }
                }
                index += files.size + 1
            }
        } else {
            index++
        }
    }
    return tree!!
}

fun part1() {
//    val tree = getTree()
//    tree.print()
//    println(tree.sizeBelowLimit(100000))

}

fun part2() {
    val tree = getTree()
    val expected = 30000000 - (70000000 - tree.size())
    println(expected)
    val toDelete = tree
        .getAsList()
        .sortedBy { it.size() }
        .first { it.size() >= expected }
    println("${toDelete.label} - ${toDelete.size()}")
}

fun main() {
    part1()
    part2()
}