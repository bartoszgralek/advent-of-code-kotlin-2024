class Matrix {

    companion object {
        fun of(input: List<String>): Matrix {
            return Matrix().also { matrix -> matrix.setArray(input.map { it.toCharArray() }.toTypedArray()) }
        }

        fun empty(n: Int, m: Int, char: Char = '.'): Matrix {
            return of(List(n) {
                val builder = StringBuilder()
                repeat(m) { builder.append(char) }
                builder.toString()
            }.toMutableList())
        }
    }

    internal var array: Array<CharArray> = emptyArray()

    private fun setArray(array: Array<CharArray>) {
        this.array = array.copyOf()
    }

    internal fun fieldOf(position: Pair<Int, Int>): Char {
        return this.array[position.first][position.second]
    }

    fun copy(): Matrix = Matrix().also { matrix -> matrix.setArray(this.array.map { it.copyOf() }.toTypedArray()) }

    fun replaceElement(position: Pair<Int, Int>, element: Char) {
        this.array[position.first][position.second] = element
    }

    fun count(c: Char): Int {
        return array.sumOf { row ->
            row.count { it == c }
        }
    }

    override fun toString(): String {
        return this.array.joinToString("\n") { row -> row.joinToString(", ", "[", "]") }
    }
}