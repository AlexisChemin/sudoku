package sudoku.domain

enum class Value {

    _1,
    _2,
    _3,
    _4,
    _5,
    _6,
    _7,
    _8,
    _9
}


@JvmField val NumberOfValues = Value.values().size