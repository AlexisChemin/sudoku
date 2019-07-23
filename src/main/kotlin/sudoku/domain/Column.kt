package sudoku.domain

import java.util.*


/**
 * A Column of the GridImpl. Up to 'ColumnHeight' Disks can be inserted into a Column
 */

class Column {

    private var disks = Stack<Color>()


    fun insertDisk(color: Color) {
        if (isFull()) {
            throw IndexOutOfBoundsException()
        }
        disks.push(color)
    }



    fun getDiskAt(rowIndex : RowIndex) : Color? {
        val stackIndex = rowIndex.ordinal
        if (stackIndex >= height()) {
            return null
        }
        return disks.elementAt(stackIndex)
    }



    fun height(): Int {
        return disks.size
    }



    fun isEmpty() : Boolean {
        return disks.isEmpty()
    }

    fun clear() {
        disks.clear()
    }

    inline fun isFull(): Boolean {
        return height() >= ColumnHeight
    }

}
