package org.example.text_parsers

import org.example.data_classes.Sentence

interface Parser {
    fun parse(text: String): List<Sentence>
}