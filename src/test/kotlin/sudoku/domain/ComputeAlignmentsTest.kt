package sudoku.domain

import assertk.assertThat
import assertk.assertions.*
import sudoku.domain.AlignmentDirection.*
import sudoku.domain.ColumnIndex.*
import sudoku.domain.RowIndex.*
import org.junit.Test

class ComputeAlignmentsTest {




    @Test
    fun `should compute alignments on an empty grid`() {
        // GIVEN
        val grid = GridImpl()

        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).isNotNull()
    }




    @Test
    fun `all aligned positions are contained in the alignment`() {

        // GIVEN
        val grid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   |   |   |   |   |   |   ")
            r("   | R |   |   | Y | Y | Y | Y |   ")
        }


        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).hasSize(1)

        val alignment = alignments.first()

        assertThat(alignment.size).isEqualTo(4)
        assertThat(alignment.contains(GridPosition(COLUMN_3, ROW_0))).isTrue()
        assertThat(alignment.contains(GridPosition(COLUMN_4, ROW_0))).isTrue()
        assertThat(alignment.contains(GridPosition(COLUMN_5, ROW_0))).isTrue()
        assertThat(alignment.contains(GridPosition(COLUMN_6, ROW_0))).isTrue()
    }



    @Test
    fun `should detect horizontal alignments`() {

        // GIVEN
        val grid = gridOf {
            r("   |   |   |   |   |   |   |   |   ")
            r("   |   |   |   |   |   |   |   |   ")
            r("   | R | R |   | Y | Y | Y | Y |   ")
        }


        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_0, ROW_0), Horizontal),
                Alignment(GridPosition(COLUMN_3, ROW_0), Horizontal)
        )
    }




    @Test
    fun `should detect vertical alignments`() {
        // GIVEN
        val grid = gridOf {
            r("   | R |   |   |   |   | Y |   |   ")
            r("   | R |   |   | Y |   | Y |   |   ")
            r("   | R |   |   | Y |   | Y |   |   ")
        }


        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_0, ROW_0), Vertical),
                Alignment(GridPosition(COLUMN_3, ROW_0), Vertical),
                Alignment(GridPosition(COLUMN_5, ROW_0), Vertical)
        )
    }





    @Test
    fun `should detect diagonal alignments`() {
        // GIVEN
        val grid = gridOf {
            r("   |   |   | R |   | R |   |   |   ")
            r("   |   |   | Y | R | Y |   |   |   ")
            r("   |   |   | R | Y | R |   |   |   ")
        }

        // WHEN
        var alignments = ComputeAlignments(grid).result

        // THEN
        assertThat(alignments).containsAll(
                Alignment(GridPosition(COLUMN_2, ROW_0), DownLeftUpRight),
                Alignment(GridPosition(COLUMN_2, ROW_2), UpLeftDownRight),
                Alignment(GridPosition(COLUMN_2, ROW_1), UpLeftDownRight),
                Alignment(GridPosition(COLUMN_3, ROW_0), DownLeftUpRight)
        )
    }


}