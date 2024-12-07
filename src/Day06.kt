data class Guard(val startPosition: Pair<Int, Int>, val startIcon: Char) {
    private val possiblePositions = mapOf(
        '^' to 0,
        '>' to 90,
        'v' to 180,
        '<' to 270
    )

    private val forwardMap = mapOf(
        0 to Pair(-1, 0),
        90 to Pair(0, 1),
        180 to Pair(1, 0),
        270 to Pair(0, -1)
    )

    private var currentPosition = startPosition
    private var currentAngle = possiblePositions[startIcon] ?: throw WrongStartIconException()

    var visited = mutableListOf(startPosition)
    private var visitedWithOrientation = mutableSetOf(startPosition to currentAngle)

    fun turnRight() {
        currentAngle = (currentAngle + 90).mod(360)
    }

    fun goForward() {
        val offset = forwardMap[currentAngle] ?: throw WrongAngleException()
        currentPosition = currentPosition.add(offset)

        visited.add(currentPosition)
        if (!visitedWithOrientation.add(currentPosition to currentAngle)) throw AlreadyVisitedException(currentPosition to currentAngle)
    }

    fun nextStep(): Pair<Int, Int> {
        val offset = forwardMap[currentAngle] ?: throw WrongAngleException()
        return currentPosition.add(offset)
    }

    fun makeRound(
        matrix: Matrix
    ): Guard {
        var inFront: Char
        while (true) {

            inFront = runCatching { matrix.fieldOf(nextStep()) }.getOrNull() ?: break
            when (inFront) {
                '.', in listOf('>', '<', 'v', '^') -> goForward()
                '#' -> turnRight()
            }
        }

        return this
    }

    fun distinctVisited(): Long = visited.distinct().count().toLong()

    inner class AlreadyVisitedException(private val pair: Pair<Pair<Int, Int>, Int>) : Throwable() {
        override val message: String
            get() = "The position is repeated: $pair"
    }

    inner class WrongAngleException : Throwable()

    inner class WrongStartIconException : Throwable()
}

fun Matrix.findGuard(): Guard {
    var guard: Guard? = null
    this.array.forEachIndexed{ x, array ->
        array.forEachIndexed { y, char ->
            if (char in listOf('>', '<', 'v', '^')) {
                guard = Guard(Pair(x, y), char)
            }
        }
    }

    return guard ?: throw NoGuardFound()
}

class NoGuardFound : Throwable()


fun main() {

    fun part1(input: List<String>): Long {
        val matrix = Matrix.of(input)
        val guard = matrix.findGuard()

        return guard.makeRound(matrix)
            .distinctVisited()
    }

    fun part2(input: List<String>): Long {
        val matrix = Matrix.of(input)
        val guard = matrix.findGuard()

        guard.makeRound(matrix)

        return guard.visited.filter { it != guard.startPosition }.distinct().count { position ->
            val _guard = guard.copy()
            val _matrix = matrix.copy()
            _matrix.replaceElement(position, '#')

            runCatching { _guard.makeRound(_matrix) }.isFailure
        }.toLong()
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41L)
    check(part2(testInput) == 6L)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}