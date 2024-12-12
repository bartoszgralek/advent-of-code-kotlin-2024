typealias Area = List<Triple<Int, Int, Char>>

fun main() {

    val offsets = listOf(
        -1 to 0,
        0 to -1,
        1 to 0,
        0 to 1
    )

    val outerCorners = listOf(
        listOf(
            -1 to 0,
            0 to -1
        ),
        listOf(
            0 to -1,
            1 to 0
        ),
        listOf(
            1 to 0,
            0 to 1
        ),
        listOf(
            0 to 1,
            -1 to 0
        )
    )

    val innerCorners = outerCorners.map {
        it + Pair(it.first { coords -> coords.first != 0 }.first, it.first { coords -> coords.second != 0 }.second)
    }

    fun Matrix.inside(x: Int, y: Int, sign: Char): Boolean {
        val plant = runCatching { array[x][y] }.getOrNull() ?: return false
        return sign == plant
    }

    fun Matrix.flood(x: Int, y: Int): List<Pair<Int, Int>> {
        val sign = array[x][y]
        val visited = mutableListOf<Pair<Int, Int>>()

        fun floodFill(x: Int, y: Int): List<Pair<Int, Int>> {
            if (Pair(x, y) in visited) return emptyList()

            if (inside(x, y, sign)) {
                visited.add(Pair(x, y))
                return listOf(Pair(x, y)) + offsets.map { floodFill(x + it.first, y + it.second) }.flatten()
            }

            return emptyList()
        }

        return floodFill(x, y)
    }

    fun Matrix.toAreas(): MutableList<Area> {
        return array.mapIndexed { x, row -> row.mapIndexed { y, char -> Triple(x, y, char) } }
            .flatten()
            .fold(mutableListOf()) { acc, triple ->
                if (acc.none { it.contains(triple) }) {
                    val others = flood(triple.first, triple.second)
                    acc.add(others.map { Triple(it.first, it.second, triple.third) })
                }
                acc
            }
    }

    fun Area.perimeter(): Long {
        val visited = mutableListOf<Pair<Int, Int>>()
        val start = first()

        fun findPerimeter(x: Int, y: Int): Long {
            if (Pair(x, y) in visited) return 0L

            visited.add(Pair(x, y))

            val (neighbours, missing) = offsets.map { (a, b) -> Pair(x + a, y + b) }
                .partition { neighbour -> neighbour in map { Pair(it.first, it.second) } }

            return neighbours.filter { it !in visited }.sumOf { findPerimeter(it.first, it.second) } + missing.count()
        }

        return findPerimeter(start.first, start.second)
    }

    fun Area.numberOfSides(): Long {
        val proxy = this.map { Pair(it.first, it.second) }
        return sumOf { (x, y, _) ->
            val outer = outerCorners.count { two -> two.map { (a, b) -> Pair(x + a, y + b) }.all { it !in proxy } }
            val inner = innerCorners.count { three ->
                three.map { (a, b) -> Pair(x + a, y + b) }.let { (c1, c2, d) ->
                    listOf(c1, c2).all { it in proxy } && d !in proxy
                }
            }

            outer + inner
        }.toLong()
    }

    fun MutableList<Area>.multiplyAreaBy(factor: Area.() -> Long): Long {
        return sumOf { area ->
            area.size * area.factor()
        }
    }

    fun part1(input: List<String>): Long {
        return Matrix.of(input)
            .toAreas()
            .multiplyAreaBy(Area::perimeter)
    }

    fun part2(input: List<String>): Long {
        return Matrix.of(input)
            .toAreas()
            .multiplyAreaBy(Area::numberOfSides)
    }

    val testInput = readInput("Day12_test_2")
//    check(part1(testInput) == 1930L)
//    check(part2(testInput) == 0L)


    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
