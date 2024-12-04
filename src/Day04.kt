fun main() {

    val xmas = charArrayOf('X', 'M', 'A', 'S')

    val offsets = listOf(
        0 to 1,
        1 to 0,
        1 to 1,
        0 to -1,
        -1 to 0,
        -1 to -1,
        -1 to 1,
        1 to -1
    )

    fun List<String>.toMatrix(): Array<CharArray> {
        return this.map { it.toCharArray() }.toTypedArray()
    }

    fun Array<CharArray>.move(x: Int, y: Int, offset: Pair<Int, Int>): Char {
        return this[x - offset.first][y - offset.second]
    }

    infix fun Pair<Int, Int>.multiply(times: Int): Pair<Int, Int> = Pair(this.first * times, this.second * times)

    fun Array<CharArray>.checkLine(startX: Int, startY: Int, offset: Pair<Int, Int>): Boolean {
        return xmas.foldIndexed(true) { index, acc, letter ->
            acc && runCatching { this.move(startX, startY, offset multiply index) == letter }.getOrElse { false }
        }
    }

    fun part1(input: List<String>): Int {
        val matrix = input.toMatrix()
        return matrix.sumOfIndexed { x, line ->
            line.sumOfIndexed { y, _ ->
                offsets.count { matrix.checkLine(x, y, it) }
            }
        }
    }

    val opposites = listOf(
        (-1 to -1) to (1 to 1),
        (-1 to 1) to (1 to -1)
    )

    fun Array<CharArray>.checkX(startX: Int, startY: Int): Boolean {
        val centerA = this[startX][startY] == 'A'
        val diagonals = opposites.all { pair ->
            val firstOpponent = pair.first
            val secondOpponent = pair.second

            runCatching {
                (this.move(startX, startY, firstOpponent) == 'M' && this.move(startX, startY, secondOpponent) == 'S') ||
                (this.move(startX, startY, firstOpponent) == 'S' && this.move(startX, startY, secondOpponent) == 'M')
            }.getOrElse { false }
        }

        return centerA && diagonals
    }

    fun part2(input: List<String>): Int {
        val matrix = input.toMatrix()
        return matrix.sumOfIndexed { x, line ->
            line.countIndexed { y, _ ->
                matrix.checkX(x, y)
            }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

inline fun CharArray.sumOfIndexed(selector: (index: Int, Char) -> Int): Int {
    var sum = 0
    var index = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}

inline fun CharArray.countIndexed(selector: (index: Int, Char) -> Boolean): Int {
    var sum = 0
    for ((index, element) in this.withIndex()) {
        if (selector(index, element)) sum += 1
    }
    return sum
}

inline fun <T> Array<T>.sumOfIndexed(selector: (index: Int, T) -> Int): Int {
    var sum = 0
    var index = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}


