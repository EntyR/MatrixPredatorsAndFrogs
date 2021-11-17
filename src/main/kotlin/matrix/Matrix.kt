package matrix

import matrix.CellState.*
import java.util.*
import kotlin.collections.ArrayList

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

    }


    fun getNeighborElements(element: PointOnMap): ArrayList<PointOnMap> {
        val topElement = matrix.find {
            (it.y == element.y+1 && it.x == element.x)
        }
        val downElement = matrix.find {
            (it.y == element.y-1 && it.x == element.x)
        }
        val rightElement = matrix.find {
            (it.y == element.y && it.x == element.x+1)
        }
        val leftElement = matrix.find {
            (it.y == element.y-1 && it.x == element.x)
        }
        val neighbors = arrayListOf<PointOnMap>()
        topElement?.let { neighbors.add(it) }
        downElement?.let {  neighbors.add(it) }
        rightElement?.let { neighbors.add(it) }
        leftElement?.let {  neighbors.add(it) }

        return neighbors

    }

    fun PointOnMap.travelTo(to: PointOnMap, from: PointOnMap){

        to.state = this.state
        this.state = Empty()
    }
    fun checkNeighborsForFrog(pos: PointOnMap): ArrayList<PointOnMap> {
        val allNeighbors = getNeighborElements(pos)
        val pair = allNeighbors.find {
            it.state is OccupyFrog
        }
        pair?.let { (pos.state as OccupyFrog).findAPair = true}
        return allNeighbors

    }
    fun checkNeighborsForPredator(pos: PointOnMap){
        val allNeighbors = getNeighborElements(pos)
        val pair = allNeighbors.find {
            it.state is OccupyPredator
        }
        pair?.let { (pos.state as OccupyPredator).findAPair = true}

    }

    fun choosePathRandomly(availablePaths: List<PointOnMap>): PointOnMap {


        println(availablePaths.size)
        val i = Random().nextInt(availablePaths.size)
        val destination = availablePaths[i]
        println(destination)
        return destination
    }

    fun PointOnMap.doSomething() {
        when (this.state) {
             is OccupyFrog -> {
                val neighbors = checkNeighborsForFrog(this)
                 travelTo(choosePathRandomly(neighbors), this)
            }
            is OccupyPredator -> {
                val neighbors = getNeighborElements(this)
                val pair = neighbors.find {
                    it.state is OccupyPredator
                }
                pair?.let { (this.state as OccupyPredator).findAPair = true}

                val food = neighbors.find {
                    it.state is OccupyFrog
                }
                food?.let {
                    (this.state as OccupyPredator).untilDeathCount += 5
                    travelTo(it, this)

                    return
                }
                travelTo(choosePathRandomly(neighbors), this)
            }
            is Empty -> {}
        }

    }
}



sealed class CellState() {
    class OccupyFrog(): CellState(){
        var findAPair: Boolean = false
    }
    class OccupyPredator(): CellState(){
        var untilDeathCount = 10
        var findAPair: Boolean = false
    }
    class Empty(): CellState()
}


