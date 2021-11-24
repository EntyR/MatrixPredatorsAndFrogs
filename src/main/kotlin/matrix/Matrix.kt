package matrix

import matrix.CellState.*
import kotlin.random.Random

data class PointOnMap(
    val x: Int, val y: Int, var state: CellState
)

class Matrix {
    val matrix = arrayListOf<PointOnMap>()

    init {
        for (xi in 0 until 10) {
            for (yi in 0 until 10) {
                matrix.add(PointOnMap(xi, yi, CellState.Empty()))
            }
        }
        spam()

    }

    fun movePredators(){
        val predators = matrix.filter {
            it.state is CellState.OccupyPredator
        }
        predators.forEach{
            doSomething(it)
        }
    }
    fun moveFrogs(){
        val frogs = matrix.filter {
            it.state is CellState.OccupyFrog
        }
        frogs.forEach{
            doSomething(it)
        }
    }

    private fun spam() {
        matrix.forEach {
            when(Random.nextInt(0, 10)){
                0 -> it.state = CellState.OccupyPredator()
                1 -> it.state = CellState.OccupyFrog()
                else -> Unit
            }
        }
    }

    fun getNeighborElements(element: PointOnMap): ArrayList<PointOnMap> {
        val topElement = matrix.find {
            (it.y == element.y + 1 && it.x == element.x)
        }
        val downElement = matrix.find {
            (it.y == element.y - 1 && it.x == element.x)
        }
        val rightElement = matrix.find {
            (it.y == element.y && it.x == element.x + 1)
        }
        val leftElement = matrix.find {
            (it.y == element.y - 1 && it.x == element.x)
        }
        val neighbors = arrayListOf<PointOnMap>()
        topElement?.let { neighbors.add(it) }
        downElement?.let { neighbors.add(it) }
        rightElement?.let {  neighbors.add(it) }
        leftElement?.let { neighbors.add(it) }

        return neighbors

    }

    fun travelTo(to: PointOnMap, from: PointOnMap) {

        to.state = from.state
        from.state = Empty()
    }



    fun choosePathRandomly(availablePaths: List<PointOnMap>): PointOnMap {



        val i = Random.nextInt(availablePaths.size)
        val destination = availablePaths[i]
        return destination
    }

    fun spawnNew(point: PointOnMap){
        val paths = getNeighborElements(point).filter {
            it.state is CellState.Empty
        }
        if (point.state is OccupyFrog){
            if((point.state as OccupyFrog).findAPair){
                choosePathRandomly(paths).state = OccupyFrog()
            }
        }else if (point.state is OccupyPredator){
            if((point.state as OccupyPredator).findAPair){
                choosePathRandomly(paths).state = OccupyPredator()
            }
        }

    }

    fun doSomething(point: PointOnMap) {
        when (point.state) {
            is OccupyFrog -> {
                val allNeighbors = getNeighborElements(point)
                allNeighbors.filter {
                    it.state is CellState.Empty
                }
                val pair = allNeighbors.find {
                    it.state is OccupyFrog
                }
                pair?.let { (point.state as OccupyFrog).findAPair = true }

                travelTo(choosePathRandomly(allNeighbors), point)
            }
            is OccupyPredator -> {
                val neighbors = getNeighborElements(point)
                neighbors.filter {
                    it.state is CellState.Empty ||  it.state is CellState.OccupyFrog
                }
                val pair = neighbors.find {
                    it.state is OccupyPredator
                }
                pair?.let { (point.state as OccupyPredator).findAPair = true }

                val food = neighbors.find {
                    it.state is OccupyFrog
                }
                food?.let {
                    println("Predator X ${point.x} Y ${point.y} foodFound X ${it.x}  Y ${it.y}")
                    (point.state as OccupyPredator).untilDeathCount += 5
                    travelTo(it, point)

                    return
                }
                (point.state as OccupyPredator).untilDeathCount -= 1
                travelTo(choosePathRandomly(neighbors), point)
            }
            is Empty -> {
            }
        }

    }
}


sealed class CellState {
    class OccupyFrog : CellState() {
        var findAPair: Boolean = false
    }

    class OccupyPredator : CellState() {
        var untilDeathCount = 10
        var findAPair: Boolean = false
    }

    class Empty : CellState()
}


