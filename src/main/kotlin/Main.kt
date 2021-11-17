import matrix.CellState
import matrix.Matrix

fun main(args: Array<String>) {

    val matrix = Matrix()
    println(matrix.matrix[0])

    matrix.matrix[0].state = CellState.OccupyFrog()
    matrix.matrix.find {
        (it.x == 0 && it.y ==1)
    }?.state = CellState.OccupyFrog()
    println(matrix.checkNeighborsForFrog(matrix.matrix[0]))
    println((matrix.matrix[0].state as CellState.OccupyFrog).findAPair)



}

