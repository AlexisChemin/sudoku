package sudoku.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import com.nhaarman.mockito_kotlin.*
import sudoku.domain.AlignmentDirection.*
import sudoku.domain.ColumnIndex.*
import sudoku.domain.Color.*
import sudoku.domain.RowIndex.*
import org.junit.Test

class GameTest {





    @Test
    fun `player1 should play RED in a REDvsYELLOW game`() {
        // GIVEN
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_0)
        }
        val yellowPlayer = mock<Player> {
            makingMove(YELLOW, COLUMN_5)
        }

        val game = Game(redPlayer, yellowPlayer)

        // WHEN
        game
                .startGameByPlayingRed()
                .play()

        // THEN
        assertThat(game.grid.getDiskColorAt(COLUMN_0, ROW_0)).isEqualTo(RED)
        assertThat(game.grid.getDiskColorAt(COLUMN_5, ROW_0)).isEqualTo(YELLOW)
    }



    @Test
    fun `player1 should play YELLOW in a YELLOWvsRED game`() {
        // GIVEN
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_0)
        }
        val yellowPlayer = mock<Player> {
            makingMove(YELLOW, COLUMN_5)
        }

        val game = Game(redPlayer, yellowPlayer)

        // WHEN
        game
                .startGameByPlayingYellow()
                .play()

        // THEN
        assertThat(game.grid.getDiskColorAt(COLUMN_5, ROW_0)).isEqualTo(YELLOW)
        assertThat(game.grid.getDiskColorAt(COLUMN_0, ROW_0)).isEqualTo(RED)
    }




    @Test
    fun `should terminate when a player has 4 aligned disks`() {
        // GIVEN
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_0)
            makingMove(RED, COLUMN_0)
            makingMove(RED, COLUMN_0)
            makingMove(RED, COLUMN_0)
        }
        val yellowPlayer = mock<Player> {
            makingMove(YELLOW, COLUMN_1)
            makingMove(YELLOW, COLUMN_2)
            makingMove(YELLOW, COLUMN_3)
        }

        val game = Game(redPlayer, yellowPlayer)

        // WHEN
        var status = game
                .startGameByPlayingRed() // red
                .play()  // yellow
                .play()  // red
                .play()  // yellow
                .play()  // red
                .play()  // yellow
                .play()  // red

        // THEN
        assertThat(game.isTerminated()).isTrue()
        val winner = (status as GameTerminated).winner
        assertThat(winner?.color).isEqualTo(RED)
//        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_0,ROW_0), Vertical, 4) )
        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_0,ROW_0), Vertical) )
    }



    @Test
    fun `should let player RED play and win`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   | R | R |   |   |   ")
            r("   |   |   | R | Y | R |   |   |   ")
            r("   | R | R | Y | R | Y | Y |   |   ")
        }
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_4)
        }
        val yellowPlayer = mock<Player> {}

        val game = Game(redPlayer, yellowPlayer, initialGrid)
        assertThat(game.isTerminated()).isFalse()

        // WHEN
        game
                .startGameByPlayingRed()


        // THEN
        assertThat(game.isTerminated()).isTrue()
        val winner = (game.status as GameTerminated).winner
        assertThat(winner?.color).isEqualTo(RED)
        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_1,ROW_0), DownLeftUpRight) )
//        assertThat(winner?.alignment).isEqualTo( Alignment(GridPosition(COLUMN_1,ROW_0), DownLeftUpRight, 4) )
    }





    @Test(expected = GameTerminatedException::class)
    fun `should not be able to play a terminated game`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   | Y |   |   | Y |   |   ")
            r("   | R | R | R | R | Y | Y |   |   ")
        }
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_4)
        }

         val game = Game(redPlayer , mock {}, initialGrid)

        // WHEN
        game
                .startGameByPlayingRed()
                .play()

        // THEN
        // exception thrown
    }



    @Test(expected = GameAlreadyStartedException::class)
    fun `should not be able to restart a started game`() {
        // GIVEN
        val redPlayer = mock<Player> {
            makingMove(RED, COLUMN_4)
        }
        val yellowPlayer = mock<Player> { }
        val game = Game(redPlayer , yellowPlayer)

        // WHEN
        game
                .startGameByPlayingRed()
        game
                .startGameByPlayingYellow()

        // THEN
        // exception thrown
    }




    @Test
    fun `should terminate when the grid is full without any winner`() {
        // GIVEN
        val initialGrid = gridOf {
            r("   | R |   | R | R | Y | Y | R |   ")
            r("   | R | R | Y | Y | R | Y | R |   ")
            r("   | R | Y | R | R | Y | R | R |   ")
            r("   | Y | R | R | Y | R | Y | Y |   ")
            r("   | Y | Y | R | Y | Y | R | R |   ")
            r("   | Y | R | Y | Y | R | Y | Y |   ")
        }
        val redPlayer = mock<Player> {}
        val yellowPlayer = mock<Player> {
            makingMove(YELLOW, COLUMN_1)
        }
        val game = Game(redPlayer, yellowPlayer, initialGrid)
        assertThat(game.isTerminated()).isFalse()

        // WHEN
        game
                .startGameByPlayingYellow()

        // THEN

        assertThat(game.isTerminated()).isTrue()
        val winner = (game.status as GameTerminated).winner
        assertThat(winner).isNull()
    }


    /**
     * Utility function to declare a given player move
     */
    private fun KStubbing<Player>.makingMove(color: Color, columnIndex: ColumnIndex) {
        on { play( eq(color), any()) } doReturn columnIndex
    }
}