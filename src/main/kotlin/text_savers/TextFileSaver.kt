package org.example.text_savers

import org.example.data_classes.Sentence
import org.slf4j.LoggerFactory
import java.io.File

class OriginalTextSaver : TextSaver {
    private val logger = LoggerFactory.getLogger(OriginalTextSaver::class.java)
    override fun save(sentences: List<Sentence>, path: String) {
        try {
            val text = sentences.joinToString(separator = "\n") { it.sentence }
            File(path).writeText(text)
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
    }
}