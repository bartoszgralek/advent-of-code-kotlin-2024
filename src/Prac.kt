fun main() {
    val options = listOf(1,2,3,4)
    sequences(options).println()
    permutations(options).println()
}

fun sequences(options: List<Int>, tmp: List<Int> = emptyList()): List<List<Int>> {
    if (tmp.size == options.size) return listOf(tmp)
    return List(options.size) { index ->
        sequences(options, tmp + options[index])
    }.flatten()
}

fun permutations(options: List<Int>, targetSize: Int = options.size, tmp: List<Int> = emptyList()): List<List<Int>> {
    if (tmp.size == targetSize) return listOf(tmp)
    return List(options.size) { index ->
        permutations(options - options[index], targetSize, tmp + options[index])
    }.flatten()
}