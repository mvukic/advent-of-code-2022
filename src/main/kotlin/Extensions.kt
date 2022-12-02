import java.io.File

fun String.asFile() = File(this)

fun getFileLines(name: String): List<String> {
    return ClassLoader.getSystemClassLoader().getResource(name)!!.file.asFile().readLines().toList()
}
