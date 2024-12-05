fun main() {

    fun extractRulesAndUpdates(input: List<String>): Pair<List<String>, MutableMap<String, List<String>>> {
        val split = input.indexOfFirst { it.isEmpty() }
        val updates = input.subList(split + 1, input.size)

        val rulesMap = input.subList(0, split).map {
            val (a, b) = it.split("|")
            Pair(a, b)
        }.fold(mutableMapOf<String, List<String>>()) { acc, el ->
            acc[el.first] = acc.getOrDefault(el.first, mutableListOf()) + el.second
            acc
        }
        return Pair(updates, rulesMap)
    }

    fun Pair<List<String>, MutableMap<String, List<String>>>.separate(): Pair<List<List<String>>, List<List<String>>> {
        val (updates, rulesMap) = this
        return updates.map { it.split(",") }.partition { pages ->
            pages.mapIndexed { index, page ->
                if (index == pages.size - 1) return@mapIndexed true
                rulesMap[page]?.containsAll(pages.subList(index + 1, pages.size))
            }.all { it == true }
        }
    }

    fun part1(input: List<String>): Long {
        return extractRulesAndUpdates(input)
            .separate()
            .first.sumOf { pages -> pages[(pages.size / 2)].toLong() }
    }

    fun part2(input: List<String>): Long {
        val data = extractRulesAndUpdates(input)
        return data.separate()
            .second.sumOf { pages ->
                pages.sortedWith { a, b ->
                    val aShouldBeAfter = data.second[b]?.contains(a) ?: return@sortedWith -1
                    if (aShouldBeAfter) 1 else -1
                }[(pages.size / 2)].toLong()
            }
    }

    val testInput = readInput("Day05_test")
    part1(testInput).println()
    check(part1(testInput) == 143L)
    check(part2(testInput) == 123L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

