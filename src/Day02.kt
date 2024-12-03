import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        return input.map { line -> line.split(" ").map { it.toLong() } }.count { levels ->
            val isIoD = levels.sorted() == levels || levels.sortedDescending() == levels
            val isStable = levels.zipWithNext { a, b -> abs(b-a) in 1..3 }.all { it }

            isIoD && isStable
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> line.split(" ") }.map { report ->
            report.foldIndexed(mutableListOf<String>()) { i, acc, level ->
                acc.add(report.filterIndexed { index, el -> index != i }.joinToString(" "))
                acc
            }
        }.count { part1(it) > 0 }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
