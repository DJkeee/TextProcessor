package org.example.text_savers

import org.example.data_classes.Sentence
import org.slf4j.LoggerFactory
import java.io.File

class OriginalTextSaver : TextSaver {
    private val logger = LoggerFactory.getLogger(OriginalTextSaver::class.java)

    override fun save(sentences: List<Sentence>, path: String) {
        try {
            val textContent = buildString {
                appendLine("=== ИСХОДНЫЙ ТЕКСТ ===")
                appendLine()
                sentences.forEach { sentence ->
                    // Если у Sentence есть свойство для исходного текста, используем его
                    // Иначе собираем из слов
                    val sentenceText = if (hasOriginalTextProperty(sentence)) {
                        getOriginalText(sentence)
                    } else {
                        sentence.words().joinToString(" ") { it.word }
                    }
                    appendLine(sentenceText)
                }

                appendLine()
                appendLine("=== СТАТИСТИКА ТЕКСТА ===")
                appendLine()

                // Добавляем статистику
                appendStatistics(sentences)
            }

            File(path).writeText(textContent)
        } catch (e: Exception) {
            logger.error("Ошибка при сохранении файла: ${e.message}", e)
        }
    }

    private fun StringBuilder.appendStatistics(sentences: List<Sentence>) {
        val allWords = sentences.flatMap { it.words() }
        val wordMeetings = allWords.groupingBy { it.word }.eachCount()
        val totalChars = allWords.sumOf { it.word.length }

        appendLine("ОБЩАЯ СТАТИСТИКА:")
        appendLine("- Количество предложений: ${sentences.size}")
        appendLine("- Общее количество слов: ${allWords.size}")
        appendLine("- Количество уникальных слов: ${wordMeetings.size}")
        appendLine("- Количество символов (без пробелов): $totalChars")
        appendLine()

        appendLine("ЧАСТОТНОСТЬ СЛОВ (ТОП-10):")
        wordMeetings.entries
            .sortedByDescending { it.value }
            .take(10)
            .forEachIndexed { index, (word, count) ->
                appendLine("${index + 1}. $word - $count раз")
            }
        appendLine()

        appendLine("СРЕДНИЕ ПОКАЗАТЕЛИ:")
        appendLine("- Средняя длина предложения: ${"%.1f".format(allWords.size.toDouble() / sentences.size)} слов")
        appendLine("- Средняя длина слова: ${"%.1f".format(totalChars.toDouble() / allWords.size)} символов")
    }

    // Вспомогательные методы для работы с исходным текстом
    private fun hasOriginalTextProperty(sentence: Sentence): Boolean {
        return try {
            sentence::class.java.getMethod("getOriginalText") != null ||
                    sentence::class.java.declaredFields.any { it.name == "originalText" }
        } catch (e: Exception) {
            false
        }
    }

    private fun getOriginalText(sentence: Sentence): String {
        return try {
            // Пытаемся получить исходный текст через геттер
            val method = sentence::class.java.getMethod("getOriginalText")
            method.invoke(sentence) as String
        } catch (e: Exception) {
            try {
                // Пытаемся получить через поле
                val field = sentence::class.java.getDeclaredField("originalText")
                field.isAccessible = true
                field.get(sentence) as String
            } catch (e: Exception) {
                // Если не получается - собираем из слов
                sentence.words().joinToString(" ") { it.word }
            }
        }
    }
}