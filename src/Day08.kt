import com.github.shiguruikai.combinatoricskt.combinations

fun main() {

    fun Matrix.findAntennas(): Collection<List<Pair<Int, Int>>> {
        return buildList {
            array.forEachIndexed { x, row ->
                row.forEachIndexed { y, char ->
                    if (char.isLetterOrDigit()) {
                        add(Pair(char, Pair(x ,y)))
                    }
                }
            }
        }.groupBy { it.first }.mapValues { (_, v) -> v.map { it.second } }.values
    }

    fun Pair<Int, Int>.antinode(other: Pair<Int, Int>): Pair<Int, Int> {
        val vector = Pair(other.first - this.first, other.second - this.second)
        return Pair(other.first + vector.first, other.second + vector.second)
    }

    fun Pair<Int, Int>.antinodeSequence(other: Pair<Int, Int>): Sequence<Pair<Int, Int>> {
        val vector = Pair(other.first - this.first, other.second - this.second)
        return generateSequence(other) { it.add(vector) }
    }

    fun part1(input: List<String>): Long {
        val matrix = Matrix.of(input)
        val antinodes = Matrix.empty(matrix.array.size, matrix.array.first().size)

        matrix.findAntennas().forEach { locationGroup ->
            locationGroup.combinations(2).forEach { (pointA, pointB) ->
                val antinodeA = pointA.antinode(pointB)
                val antinodeB = pointB.antinode(pointA)

                runCatching { antinodes.replaceElement(antinodeA, '#') }
                runCatching { antinodes.replaceElement(antinodeB, '#') }
            }
        }

        return antinodes.count('#').toLong()
    }

    fun part2(input: List<String>): Long {
        val matrix = Matrix.of(input)
        val antinodes = Matrix.empty(matrix.array.size, matrix.array.first().size)

        matrix.findAntennas().forEach { locationGroup ->
            locationGroup.combinations(2).forEach { (pointA, pointB) ->
                runCatching {
                    pointA.antinodeSequence(pointB).forEach {
                        antinodes.replaceElement(it, '#')
                    }
                }
                runCatching {
                    pointB.antinodeSequence(pointA).forEach {
                        antinodes.replaceElement(it, '#')
                    }
                }
            }
        }

        return antinodes.count('#').toLong()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14L)
    check(part2(testInput) == 34L)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
