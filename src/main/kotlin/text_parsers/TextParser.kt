package org.example.text_parsers

import org.example.data_classes.Sentence
//получает данные и возвращает список нормализованных предложений - никаких лишних пробелов и запятых

class TextParser : Parser {
    companion object {
        private val SENTENCE_SPLIT_REGEX = Regex("""(?<=[.!?])\s+""")
        private val MULTIPLE_SPACE_REGEX = Regex("""\s+""")
        private val MULTIPLE_COMMAS_REGEX = Regex(""",{2,}""")
    }

    override fun parse(text: String): List<Sentence> {
        if (text.isBlank()) return emptyList()

        return text
            .splitToSentences()
            .map { cleanSentence(it) }
            .filter { it.isNotBlank() }
            .map { Sentence(it) }
    }

    private fun String.splitToSentences(): List<String> {
        return split(SENTENCE_SPLIT_REGEX)
    }

    private fun cleanSentence(sentence: String): String {
        return sentence
            .replace(MULTIPLE_COMMAS_REGEX, ",")
            .replace(MULTIPLE_SPACE_REGEX, " ")
            .trim()
    }
}