package com.tech4gen.eLearning.database.model.paper

enum class PaperType {
    FIRST_TERM_PAPER(1),
    SECOND_TERM_PAPER(2),
    THIRD_TERM_PAPER(3),
    PAST_PAPER(4);

    companion object {
        fun fromValue(value: Int): PaperType? {
            return PaperType.entries.find { it.value == value }
        }
    }

    val value: Int
    constructor(value: Int) {
        this.value = value
    }
}