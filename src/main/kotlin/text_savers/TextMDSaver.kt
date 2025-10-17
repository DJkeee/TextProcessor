package org.example.text_savers

import org.example.data_classes.Sentence
import org.example.text_readers.FileReader
import org.slf4j.LoggerFactory
import java.io.File

class MarkdownTextSaver : TextSaver {
    companion object {
        private val log = LoggerFactory.getLogger(FileReader::class.java)
    }
    private val LINE_BREAK = 5;
    override fun save(text: List<Sentence>, path: String) {
        val markdownContent = buildMarkdownContent(text)
        try {
        File(path).writeText(markdownContent)}
        catch (e: Exception) {
            log.error(e.message,e)
        }
    }

    private fun buildMarkdownContent(sentences: List<Sentence>): String {
        return buildString {
            appendLine("# Текст анализа")
            appendLine()

            appendLine("## Статистика документа")
            appendStatistics(sentences)
            appendLine()

            appendLine("## Содержимое")
            appendSentences(sentences)
            appendLine()

            appendLine("## Уникальные слова")
            appendUniqueWords(sentences)
        }
    }

    private fun StringBuilder.appendStatistics(sentences: List<Sentence>) {
        val allWords = sentences.flatMap { it.words() }
        val wordMeetingsAt = allWords.groupingBy { it.word }.eachCount()

        appendLine("- **Общее количество предложений:** ${sentences.size}")
        appendLine("- **Общее количество слов:** ${allWords.size}")
        appendLine("- **Количество уникальных слов:** ${wordMeetingsAt.size}")
        appendLine("- **Количество символов:** ${allWords.sumOf { it.word.length }}")
        appendLine()
        appendLine("### Частотность слов (топ-10):")

        wordMeetingsAt.entries
            .sortedByDescending { it.value }
            .take(10)
            .forEach { (word, count) ->
                appendLine("1. `$word` - встречается $count раз")
            }
    }

    private fun StringBuilder.appendSentences(sentences: List<Sentence>) {
        sentences.forEachIndexed { index, sentence ->
            appendLine("${index + 1}. ${sentence.sentence}")
            appendLine()
        }
    }

    private fun StringBuilder.appendUniqueWords(sentences: List<Sentence>) {
        val uniqueWords = sentences
            .flatMap { it.words() }
            .map { it.word }
            .distinct()
            .sorted()

        uniqueWords.forEachIndexed { index, word ->
            append("`$word`")
            if (index < uniqueWords.size - 1) {
                append(", ")
            }
            if ((index + 1) % LINE_BREAK == 0) {
                appendLine()
            }
        }
    }
}