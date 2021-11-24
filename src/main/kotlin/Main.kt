import matrix.CellState
import matrix.Matrix

fun main(args: Array<String>) {

    val matrix = Matrix()
//    println(matrix.matrix[0])
//
//    matrix.matrix[0].state = CellState.OccupyFrog()
//    matrix.matrix.find {
//        (it.x == 0 && it.y ==1)
//    }?.state = CellState.OccupyFrog()
//    println(matrix.checkNeighborsForFrog(matrix.matrix[0]))
//    println((matrix.matrix[0].state as CellState.OccupyFrog).findAPair)

//    matrix.matrix.forEach {
//        println((it.state::class.java))
//    }
//    println("///////////////////////////////////////////////////")
//    matrix.movePredators()
//
//    matrix.matrix.forEach {
//        println((it.state::class.java))
//        if (it.state is CellState.OccupyFrog  )
//            println((it.state as CellState.OccupyFrog).findAPair )
//        if (it.state is CellState.OccupyPredator  )
//            println((it.state as CellState.OccupyPredator).findAPair )
//    }

    for (i in 0 until 10){
        println(("$i cycle"))
        matrix.movePredators()
        matrix.moveFrogs()
        Thread.sleep(1000)
    }

    matrix.matrix.forEach {
        matrix.spawnNew(it)
        println((it.state::class.java))
    }



}

