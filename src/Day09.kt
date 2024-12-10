fun main() {


    fun IntArray.toBlocks(): MutableList<Int?> {
        return this.map { it - 48 }.foldIndexed(mutableListOf()) { i, acc, num ->
            when (i % 2) {
                0 -> repeat(num) { acc.add(i / 2) }
                1 -> repeat(num) { acc.add(null) }
                else -> throw RuntimeException("That should not be possible")
            }
            acc
        }
    }

    fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
    }

    fun MutableList<Int?>.defragment(): MutableList<Int?> {
        return foldRightIndexed(this) { i, value, acc ->
            if (acc.indexOfFirst { it == null } > i) return acc
            if (value != null)
                swap(i, acc.indexOfFirst { it == null })
            acc
        }
    }

    fun MutableList<Int?>.findGaps(size: Int): List<Int> {
        return this.indices.windowed(size, 1).firstOrNull { window -> window.all { this[it] == null } } ?: emptyList()
    }

    fun MutableList<Int?>.defragmentWhole(fileId: Int): MutableList<Int?> {
        if (fileId == -1) return this
        val indexes = indices.filter { get(it) == fileId }
        val gaps = findGaps(indexes.size)

        if (gaps.isNotEmpty() && gaps.last() < indexes.first()) {
            gaps.forEachIndexed { index, gapId ->
                swap(gapId, indexes[index])
            }
        }
        return this.defragmentWhole(fileId - 1)
    }

    fun MutableList<Int?>.defragmentWhole(): MutableList<Int?> = defragmentWhole(last { it != null } ?: -1)

    fun MutableList<Int?>.checksum(): Long {
        return foldIndexed(0L) { i, acc, value ->
            acc + i * (value ?: 0)
        }
    }

    fun part1(input: List<String>): Long {
        return input.first().chars().toArray()
            .toBlocks()
            .defragment()
            .checksum()
    }

    fun part2(input: List<String>): Long {
        return input.first().chars().toArray()
            .toBlocks()
            .defragmentWhole()
            .checksum()
    }

    val testInput = readInput("Day09_test")
//    check(part1(testInput) == 1928L)
//    check(part2(testInput) == 0L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}