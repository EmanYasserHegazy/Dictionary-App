package com.user.dictionaryapplication.data.source

import com.user.dictionaryapplication.data.cache.WordEntity
import com.user.dictionaryapplication.domain.entities.Word


fun Word.toWordEntity(): WordEntity {
    return WordEntity(word = this.word, definition = this.definition)
}


fun WordEntity.toWord(): Word {
    return Word(word = this.word, definition = this.definition)
}

fun List<WordEntity>.toWordList(): List<Word> {
    return map { it.toWord() }
}
