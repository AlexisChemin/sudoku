package sudoku.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import assertk.assertions.isTrue
import sudoku.domain.Color.*
import sudoku.domain.RowIndex.*
import org.junit.Test

class ColumnTest {




    @Test
    fun `a new Column is empty and its size is 0`() {
        // GIVEN

        // WHEN
        val column = Column()

        // THEN
        assertThat(column.isEmpty()).isTrue()
        assertThat(column.height()).isEqualTo(0)
    }



    @Test
    fun `should be able to insert disks within column`() {
        // GIVEN
        val column = Column()

        // WHEN
        column.insertDisk(YELLOW)
        column.insertDisk(RED)
        column.insertDisk(RED)

        // THEN
        assertThat(column.height()).isEqualTo(3)
    }




    @Test(expected = IndexOutOfBoundsException::class)
    fun `could not insert more than -ColumnHeight- disks`() {
        // GIVEN

        //              a filled up column
        val column = Column()
        for (x in 1..ColumnHeight) {
            column.insertDisk(YELLOW)
        }
        assertThat(column.height()).isEqualTo(ColumnHeight)

        // WHEN
        //              add one more color
        column.insertDisk(YELLOW)

        // THEN
        //              exception thrown
    }



    @Test
    fun `getting a disk below -column's height- returns a non null disk`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(YELLOW)
        column.insertDisk(RED)
        column.insertDisk(YELLOW)


        // WHEN
        //              get disks
        val disk0 = column.getDiskAt(ROW_0)
        val disk1 = column.getDiskAt(ROW_1)
        val disk2 = column.getDiskAt(ROW_2)

        // THEN
        assertThat(disk0).isEqualTo(YELLOW)
        assertThat(disk1).isEqualTo(RED)
        assertThat(disk2).isEqualTo(YELLOW)
    }



    @Test
    fun `getting a disk over -column's height- returns null`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(YELLOW)
        column.insertDisk(RED)
        column.insertDisk(YELLOW)


        // WHEN
        //              get disks from position 3
        val disk3 = column.getDiskAt(ROW_3)
        val disk4 = column.getDiskAt(ROW_4)

        // THEN
        assertThat(disk3).isNull()
        assertThat(disk4).isNull()
    }





    @Test
    fun `-clear- column should remove all disks of that column`() {
        // GIVEN
        //              a three Disks Column
        val column = Column()
        column.insertDisk(YELLOW)
        column.insertDisk(RED)
        column.insertDisk(YELLOW)

        // WHEN
        column.clear()

        // THEN
        assertThat(column.isEmpty())
        assertThat(column.height()).isEqualTo(0)
    }



}