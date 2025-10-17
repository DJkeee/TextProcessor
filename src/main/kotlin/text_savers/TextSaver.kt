package org.example.text_savers

import org.example.data_classes.Sentence

interface TextSaver {
    fun save(text: List<Sentence>, path: String)
}