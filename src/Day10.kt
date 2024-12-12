import com.github.shiguruikai.combinatoricskt.cartesianProduct

fun main() {

    data class Node(val x: Int, val y: Int, val value: Int)

    data class Edge(val from: Node, val to: Node)

    class Graph {

        val edges = mutableSetOf<Edge>()
        val nodes = mutableSetOf<Node>()

        fun addEdge(from: Node, to: Node) {
            nodes.add(from)
            nodes.add(to)
            edges.add(Edge(from, to))
        }

        fun Node.neighbours(): List<Node> {
            return edges.filter { it.from == this }.map { it.to }
        }

        fun pathExists(from: Node, to: Node): Boolean {
            val visited: MutableSet<Node> = mutableSetOf()

            fun dfs(node: Node): Boolean {
                if (node == to) return true

                visited.add(node)

                for (neighbour in node.neighbours()) {
                    if (neighbour !in visited) {
                        if (dfs(neighbour)) return true
                    }
                }

                return false
            }

            return dfs(from)
        }

        fun countPaths(from: Node, to: Node): Int {
            fun bfs(node: Node): Int {
                if (node == to) return 1

                return node.neighbours().sumOf {
                    bfs(it)
                }
            }

            return bfs(from)
        }
    }


    val offsets = listOf(
        -1 to 0,
        0 to -1,
        1 to 0,
        0 to 1
    )

    fun Matrix.toGraph(): Graph {
        val graph = Graph()

        this.array.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                offsets.map { (key, value) -> x + key to y + value }.forEach { (oX, oY) ->
                    val value = this.array.getOrNull(oX)?.getOrNull(oY)?.digitToInt() ?: -1
                    if (value == char.digitToInt() + 1)
                        graph.addEdge(
                            Node(x, y, char.digitToInt()),
                            Node(oX, oY, value)
                        )
                }
            }
        }

        return graph
    }

    fun part1(input: List<String>): Long {
        val graph = Matrix.of(input).toGraph()
        val starts = graph.nodes.filter { it.value == 0 }
        val ends = graph.nodes.filter { it.value == 9 }

        return starts.cartesianProduct(ends).count { (from, to) -> graph.pathExists(from, to) }.toLong()
    }

    fun part2(input: List<String>): Long {
        val graph = Matrix.of(input).toGraph()
        val starts = graph.nodes.filter { it.value == 0 }
        val ends = graph.nodes.filter { it.value == 9 }

        return starts.cartesianProduct(ends).sumOf { (from, to) -> graph.countPaths(from, to) }.toLong()
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36L)
    check(part2(testInput) == 81L)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
