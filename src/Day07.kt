fun main() {

    val functions = listOf(
        { a: Long, b: Long -> a + b },
        { a: Long, b: Long -> a * b },
        { a: Long, b: Long -> (a.toString() + b.toString()).toLong() }
    )

    fun calculate(input: List<String>, radix: Int, functions: List<(Long, Long) -> Long>): Long {
        return input.map { it.split(":") }.filter { (sum, rest) ->
            val numbers = rest.split(' ').drop(1).map { it.toLong() }

            List(radix `**` (numbers.size - 1)) { index ->
                index.toString(radix)
                    .toLong()
                    .let { String.format("%0${numbers.size - 1}d", it) }
                    .map { functions[it.digitToInt()] }
            }.any { operations ->
                val result = numbers.drop(1).foldIndexed(numbers.first()) { index, acc, next ->
                    operations[index](acc, next)
                }
                result == sum.toLong()
            }
        }.sumOf { it.first().toLong() }
    }

    fun part1(input: List<String>): Long {
        val radix = 2
        return calculate(input, radix, functions.subList(0, radix))
    }

    fun part2(input: List<String>): Long {
        val radix = 3
        return calculate(input, radix, functions.subList(0, radix))
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
