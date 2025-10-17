package org.example.data_classes

data class Sentence(val sentence: String) {
    companion object {
        private val WORD_PARSING_REGEX_PATTERN = Regex("""[^\p{L}\p{N}\s'-]|(?<!\p{L})['-]|['-](?!\p{L})""")
        private val SPACE_REGEX_PARTITION = Regex("\\s+")
    }

    fun words(): List<Word> {
        return normalizeSentence()
    }

    private fun normalizeSentence(): List<Word> {
        val cleanedSentence = WORD_PARSING_REGEX_PATTERN.replace(sentence, " ")
        return cleanedSentence.split(SPACE_REGEX_PARTITION)
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .map { it.replaceFirstChar { it.uppercase() } }
            .map { Word(it) }
    }
}