import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun List<String>.toMatrix(): Array<CharArray> {
    return this.map { it.toCharArray() }.toTypedArray()
}

fun <T : Number> Pair<T, T>.add(other: Pair<T, T>): Pair<T, T> {
    val firstSum = addNumbers(this.first, other.first)
    val secondSum = addNumbers(this.second, other.second)
    return Pair(firstSum, secondSum)
}

// Helper function to add two numbers of the same type
private fun <T : Number> addNumbers(a: T, b: T): T {
    return when (a) {
        is Int -> (a + b.toInt()) as T
        is Long -> (a + b.toLong()) as T
        is Float -> (a + b.toFloat()) as T
        is Double -> (a + b.toDouble()) as T
        else -> throw IllegalArgumentException("Unsupported type")
    }
}