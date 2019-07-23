package sudoku.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isNull
import assertk.assertions.isTrue
import sudoku.domain.ColumnIndex.*
import sudoku.domain.Color.*
import sudoku.domain.RowIndex.*
import org.junit.Test

class GridTest {




    @Test
    fun `a new Grid should be empty`() {
        // GIVEN

        // WHEN
        val grid = GridImpl()

        // THEN
        assertThat(grid.isEmpty()).isTrue()
    }



    @Test
    fun `should be able to insert a disk in a Grid`() {
        // GIVEN
        val grid = GridImpl()

        // WHEN
        grid.insertDisk(COLUMN_3, YELLOW)

        // THEN
        assertThat(grid.isEmpty()).isFalse()
        assertThat(grid.size()).isEqualTo(1)
    }


    @Test
    fun `should be able to insert serveral disks in a Grid`() {
        // GIVEN
        val grid = GridImpl()

        // WHEN
        grid.insertDisk(COLUMN_3, YELLOW)
        grid.insertDisk(COLUMN_3, RED)
        grid.insertDisk(COLUMN_0, RED)

        // THEN
        assertThat(grid.isEmpty()).isFalse()
        assertThat(grid.size()).isEqualTo(3)
    }



    @Test
    fun `should be able to get the disk at given position`() {
        // GIVEN
        //              a three Disks GridImpl
        val grid = GridImpl()
        grid.insertDisk(COLUMN_1, YELLOW)
        grid.insertDisk(COLUMN_1, RED)
        grid.insertDisk(COLUMN_0, RED)


        // WHEN
        //              get disks
        val disk0 = grid.getDiskColorAt(COLUMN_1, ROW_0)
        val disk1 = grid.getDiskColorAt(COLUMN_1, ROW_1)
        val disk2 = grid.getDiskColorAt(COLUMN_0, ROW_0)


        // THEN
        assertThat(disk0).isEqualTo(YELLOW)
        assertThat(disk1).isEqualTo(RED)
        assertThat(disk2).isEqualTo(RED)
    }



    @Test
    fun `should return null when getting a disk at a position over -column's height-`() {
        // GIVEN
        //              a three Disks Column
        val grid = GridImpl()
        grid.insertDisk(COLUMN_3, YELLOW)


        // WHEN
        //              get disks from position 3
        val disk3 = grid.getDiskColorAt(COLUMN_3, ROW_1) // row over height
        val disk4 = grid.getDiskColorAt(COLUMN_0, ROW_0)

        // THEN
        assertThat(disk3).isNull()
        assertThat(disk4).isNull()
    }


}