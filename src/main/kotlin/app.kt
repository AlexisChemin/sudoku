import sudoku.ai.DumbPlayer
import sudoku.console.Console
import sudoku.console.ConsolePlayer
import sudoku.domain.Game
import sudoku.domain.GameTerminated

fun main(args: Array<String>) {

    val redPlayer = ConsolePlayer()
    val yellowPlayer = DumbPlayer()


//    val initialGrid = gridOf {
//        r("   | R |   | R | R | Y | Y | R |   ")
//        r("   | R |   | Y | Y | R | Y | R |   ")
//        r("   | R | Y | R | R | Y | R | R |   ")
//        r("   | Y | R | R | Y | R | Y | Y |   ")
//        r("   | Y | Y | R | Y | Y | R | R |   ")
//        r("   | Y | R | Y | Y | R | Y | Y |   ")
//    }


    val game = Game(redPlayer, yellowPlayer)

    var status = game
            .startGameByPlayingRed()

    while (status !is GameTerminated ){
        status = status.play()
    }

    Console.showGrid(game.grid, status.winner?.alignment)

    // any winner ?
    status.winner?.let {
        println( it.color.name  + " has won " )
    } ?: run {
        println("No winner")
    }

}