class Matrix {

    companion object {
        fun of(input: List<String>): Matrix {
            return Matrix().also { matrix -> matrix.setArray(input.map { it.toCharArray() }.toTypedArray()) }
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

    override fun toString(): String {
        return this.array.joinToString("\n") { row -> row.joinToString(", ", "[", "]") }
    }
}