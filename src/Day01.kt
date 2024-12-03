import kotlin.math.abs

fun main() {

    fun pair(input: List<String>): Pair<List<Long>, List<Long>> {
    val pairs = input.map { line -> line.split("   ").let { it[0] to it[1] } }
    val left = pairs.map { it.first }.map { it.toLong() }.sorted()
    val right = pairs.map { it.second }.map { it.toLong() }.sorted()
    return Pair(left, right)
}

    fun part1(input: List<String>): Long {
        val (left, right) = pair(input)
        return left.zip(right).sumOf { abs(it.second - it.first) }
    }

    fun part2(input: List<String>): Long {
        val (left, right) = pair(input)
        val result = right.associateWith { key -> right.count { it == key } }
        return left.sumOf { result.getOrDefault(it, 0).times(it) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11L)
    check(part2(testInput) == 31L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
