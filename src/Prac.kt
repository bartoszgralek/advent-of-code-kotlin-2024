fun main() {
    val options = listOf(1,2,3,4)
    sequences(options).size.println()
    permutations(options).println()
    permutations(options).size.println()
}

fun sequences(options: List<Int>, tmp: List<Int> = emptyList()): List<List<Int>> {
    if (tmp.size == options.size) return listOf(tmp)
    return List(options.size) { index ->
        sequences(options, tmp + options[index])
    }.flatten()
}

fun permutations(options: List<Int>): List<List<Int>> {
    return permutationsInternal(options, options.size)
}

fun permutationsInternal(options: List<Int>, targetSize: Int, tmp: List<Int> = emptyList()): List<List<Int>> {
    if (tmp.size == targetSize) return listOf(tmp)
    return List(options.size) { index ->
        permutationsInternal(options - options[index], targetSize, tmp + options[index])
    }.flatten()
}