fun main() {

    fun List<String>.countNumbers(times: Int): Long {
        val memoization = mutableMapOf<Pair<String, Int>, Long>()

        fun blink(stone: String, times: Int): Long {
            if (times == 0) {
                return 1L
            }

            memoization[Pair(stone, times)]?.also { return it }

            val result =  if (stone == "0") blink("1", times - 1)
            else if (stone.length % 2 == 0) {
                val first = stone.substring(0, stone.length/2)
                val second = stone.substring(stone.length/2, stone.length).toLong().toString()

                blink(first, times - 1) + blink(second, times - 1)
            } else blink((stone.toLong() * 2024L).toString(), times - 1)

            memoization[Pair(stone, times)] = result

            return result
        }

        return this.sumOf { blink(it, times) }
    }

    fun part1(input: List<String>): Long {
        return input.first().split(' ').countNumbers(25).toLong()
    }

    fun part2(input: List<String>): Long {
        return input.first().split(' ').countNumbers(75).toLong()
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}
