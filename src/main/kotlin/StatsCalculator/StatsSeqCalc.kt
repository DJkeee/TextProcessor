package org.example.StatsCalculator

import org.example.data_classes.Sentence
import org.example.data_classes.TextStats

/**
 * Калькулятор статистики для коллекции предложений.
 *
 * Вычисляет различные метрики текста на основе списка предложений, включая:
 * - общее количество слов и символов
 * - частоту встречаемости слов
 * - использование памяти
 * - количество уникальных слов
 * ```
 */
class SentencesStatsCalculator {
    /**
     * Размер одного символа в байтах для расчета использования памяти.
     *
     * Используется значение 2, так как в Kotlin/JVM строки хранятся в кодировке UTF-16,
     * где каждый символ занимает 2 байта.
     */
    val CHAR_SIZE_IN_BYTES = 2

    /**
     * Вычисляет статистику текста на основе переданных предложений.
     *
     * Анализирует коллекцию предложений и возвращает статистику,
     * включая подсчет слов, символов, частоту встречаемости слов и использование памяти.
     *
     * @param sentences список предложений для анализа
     * @return объект [TextStats] с рассчитанной статистикой
     * @see TextStats для подробной информации о возвращаемых данных
     * @see Sentence для информации о структуре предложения
     */
    fun calculate(sentences: List<Sentence>): TextStats {
        val allWords = sentences.flatMap { it.words() }
        val wordCount = allWords.size
        val charCount = allWords.sumOf { it.word.length }
        val wordMeetingsRate = allWords
            .groupingBy { it.word }
            .eachCount()

        val unWordsCount = wordMeetingsRate.size
        val memoryUsed = sentences.sumOf { it.sentence.length * CHAR_SIZE_IN_BYTES }

        return TextStats(
            wordCount = wordCount,
            charCount = charCount,
            wordMeetingRate = wordMeetingsRate,
            memoryUsed = memoryUsed,
            uniqueWordCount = unWordsCount
        )
    }
}