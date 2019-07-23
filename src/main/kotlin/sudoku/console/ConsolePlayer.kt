package sudoku.console

import sudoku.domain.*


class ConsolePlayer : Player {


    override fun play(color: Color, grid: Grid): ColumnIndex {
        Console.showGrid(grid)
        var columnIndex = Console.askColumn(grid.freeColumns())

        println(color.name + " played column " + (columnIndex.ordinal+1) )

        return columnIndex
    }

}