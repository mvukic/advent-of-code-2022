import java.io.File

fun String.asFile() = File(this)

fun getFileLines(name: String): List<String> {
    return ClassLoader.getSystemClassLoader().getResource(name)!!.file.asFile().readLines().toList()
}

fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val idx = this.indexOfFirst(predicate)
    return if (idx == -1) {
        listOf(this)
    } else {
        return listOf(this.take(idx)) + this.drop(idx + 1).split(predicate)
    }
}