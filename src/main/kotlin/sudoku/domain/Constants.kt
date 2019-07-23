package sudoku.domain


enum class RowIndex {
    ROW_0,
    ROW_1,
    ROW_2,
    ROW_3,
    ROW_4,
    ROW_5;

    fun uppward() : RowIndex? = get(ordinal+1)
    fun downward() : RowIndex? = get(ordinal-1)

    private fun get(index : Int) : RowIndex?{
        val values = values()
        if (index < 0 || index >= values.size ) {
            return null
        }
        return values[index]
    }
}

enum class ColumnIndex {

    COLUMN_0,
    COLUMN_1,
    COLUMN_2,
    COLUMN_3,
    COLUMN_4,
    COLUMN_5,
    COLUMN_6;

    fun leftward() : ColumnIndex? = get(ordinal-1)


    private fun get(index : Int) : ColumnIndex?{
        val values = ColumnIndex.values()
        if (index < 0 || index >= values.size ) {
            return null
        }
        return values[index]
    }
}


@JvmField val ColumnHeight = RowIndex.values().size

@JvmField val GridWidth = ColumnIndex.values().size
