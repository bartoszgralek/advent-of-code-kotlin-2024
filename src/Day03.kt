fun main() {

    fun part1(input: List<String>): Long {
        val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
        return input.sumOf { line -> regex.findAll(line).sumOf { found ->
            found.groupValues[1].toLong() * found.groupValues[2].toLong()
        } }
    }

    fun part2(input: List<String>): Long {
        return input.map {
            val line = StringBuilder(it)
            val valid = StringBuilder()
            while (line.isNotEmpty()) {
                println("___________________________")
                println(line)
                println(valid)
                val nextDont = line.indexOf("don't()")
                if (nextDont == -1) {
                    valid.append(line)
                    break
                }
                valid.append(line.substring(0, nextDont))
                valid.append("*")
                line.removeRange(0, nextDont)
                line.removePrefix("don't()")
                val nextDo = line.indexOf("do()")
                if (nextDo == -1) {
                    break
                }
                line.removeRange(0, nextDo)
                line.removePrefix("do()")
            }
            valid.toString()
        }.let { part1(it) }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161L)
    check(part2(testInput) == 48L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

