package sudoku.domain


/**
 * A game Player must be able to play a given color on a given grid.
 * Playing consists in selecting a grid column
 */

interface Player {
    fun play(color : Color, grid : Grid) : ColumnIndex
}


/**
 * different game status : Initialized -> Running -> Terminated
 */
interface GameStatus {

    fun isTerminated() : Boolean

}

/**
 * In Initialized state, a game can be started
 */
interface GameInitialized : GameStatus {

    override fun isTerminated() : Boolean = false

    fun startGameByPlayingRed() : GameRunning

    fun startGameByPlayingYellow() : GameRunning
}


/**
 * While running, it is possible to 'play' the game
 */
interface GameRunning : GameStatus {

    override fun isTerminated() : Boolean = false

    fun play(): GameRunning
}

/**
 * Terminated state may hold a winner
 */
class GameTerminated(val winner : Winner?) : GameRunning {

    override fun isTerminated(): Boolean = true

    override fun play(): GameRunning {
        throw GameTerminatedException("Game is terminated !")
    }
}


/**
 * A Winner is a 'winning' color and gives the 'winning alignment'
 */
class Winner(val color : Color, val alignment: Alignment)


/**
 * A Game is composed of 2 players and a mutable grid
 *
 */
class Game (redPlayer : Player, yellowPlayer : Player, private val gridImpl : GridImpl = GridImpl()) : GameInitialized {

    // stores players by color
    private val playerByColor = mapOf( Color.RED to redPlayer, Color.YELLOW to yellowPlayer )

    // who plays next ? => playingColor
    private var playingColor : Color = Color.RED

    // internal game status, null means 'not started yet'
    var status : GameRunning? = null
        private set

    // read-only view of the grid
    val grid : Grid
        get() = gridImpl



    override fun startGameByPlayingRed() : GameRunning {
        return startGame(Color.RED)
    }

    override fun startGameByPlayingYellow() : GameRunning {
        return startGame(Color.YELLOW)
    }


    override fun isTerminated() : Boolean = status?.isTerminated() ?: false



    private fun startGame(color: Color): GameRunning {
        if (status != null) {
            throw GameAlreadyStartedException("Game is already started !")
        }
        this.playingColor = color
        return play()
    }


    private fun play() : GameRunning {
        val playedColumnIndex = playerByColor.getValue(playingColor).play(playingColor, gridImpl)
        val newStatus = play(playingColor, playedColumnIndex)
        playingColor = nextPlayerColor()
        status = newStatus
        return newStatus
    }



    private fun nextPlayerColor(): Color {
        return when(playingColor) {
            Color.RED -> Color.YELLOW
            else -> Color.RED
        }
    }

    /**
     * internal play : test status to check the game is already terminated or not.
     * makes the move
     * recompute game status
     */
    private fun play(color: Color, position: ColumnIndex) : GameRunning {
        if (isTerminated()) {
            throw GameTerminatedException("Game is terminated !")
        }

        gridImpl.insertDisk(position, color)

        // any winner ?
        return gameStatus()
    }


    /**
     * Computes the game status : whether a player has won or the gridImpl is full
     */
    private fun gameStatus() : GameRunning {
        val winner = findWinner(gridImpl)

        if (winner != null || gridImpl.isFull()) {
            return GameTerminated(winner)
        }

        return Running()
    }


    private fun findWinner(grid: GridImpl): Winner? {
        // fetch alignments, find one with a length of at least 4
        val alignment = ComputeAlignments(grid).result.find {  it.size >= 4 }

        alignment?.let {
            // alignment starts with the winner color
            // ==> just get the color at the 'start' position
            val disk = grid.getDiskColorAt(it.start.first, it.start.second)

            return disk?.let { Winner(disk, alignment) }
        }
        return null
    }

    private inner class Running : GameRunning {
        override fun play(): GameRunning {
            return this@Game.play()
        }
    }






}

class GameTerminatedException(message: String) : RuntimeException(message)
class GameAlreadyStartedException(message: String) : RuntimeException(message)
