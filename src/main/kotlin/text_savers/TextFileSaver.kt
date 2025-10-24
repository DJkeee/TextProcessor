package org.example.text_savers

import org.example.data_classes.Sentence
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Реализация интерфейса TextSaver для сохранения текста и статистики в файл.
 *
 * Сохраняет предложения в файл с дополнительной статистической информацией, включая:
 * - Текст, собранный из нормализованных слов
 * - Общую статистику текста (количество предложений, слов, символов)
 * - Топ-10 самых частых слов
 * - Средние показатели текста
 *
 * @see TextSaver базовый интерфейс для сохранения текста
 * @see Sentence класс, представляющий предложение
 */
class OriginalTextSaver : TextSaver {
    private val logger = LoggerFactory.getLogger(OriginalTextSaver::class.java)

    /**
     * Сохраняет список предложений в указанный файл с добавлением статистики.
     *
     * @param sentences список предложений для сохранения
     * @param path путь к файлу для сохранения
     *
     * @example
     * ```
     * val saver = OriginalTextSaver()
     * val sentences = parser.parse("Текст для анализа.")
     * saver.save(sentences, "результат.txt")
     * ```
     */
    override fun save(sentences: List<Sentence>, path: String) {
        try {
            val textContent = buildString {
                appendLine("=== ТЕКСТ ===")
                appendLine()
                sentences.forEach { sentence ->
                    val sentenceText = sentence.words().joinToString(" ") { it.word }
                    appendLine(sentenceText)
                }

                appendLine()
                appendLine("=== СТАТИСТИКА ТЕКСТА ===")
                appendLine()

                appendStatistics(sentences)
                println("Успех! Файл сохранен по пути ${path}")
            }

            File(path).writeText(textContent)
        } catch (e: Exception) {
            logger.error("Ошибка при сохранении файла: ${e.message}", e)
        }
    }

    /**
     * Добавляет статистическую информацию в StringBuilder.
     *
     * @param sentences список предложений для анализа
     */
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
}