package org.example.data_classes

/**
 * Класс для хранения и отображения статистики текста.
 *
 * Содержит основную статистику текста: количество слов, символов, частоту встречаемости слов,
 * использование памяти и количество уникальных слов. Предоставляет методы для форматированного
 * вывода статистики в виде текстовых таблиц.
 *
 * @property wordCount общее количество слов в тексте
 * @property charCount общее количество символов в тексте
 * @property wordMeetingRate карта частоты встречаемости слов (слово -> количество вхождений)
 * @property memoryUsed объем использованной памяти в байтах
 * @property uniqueWordCount количество уникальных слов в тексте
 *
 * @example
 * ```
 * val stats = TextStats(
 *     wordCount = 150,
 *     charCount = 1250,
 *     wordMeetingRate = mapOf("привет" to 5, "мир" to 3),
 *     memoryUsed = 2048,
 *     uniqueWordCount = 100
 * )
 * println(stats) // выводит краткую статистику
 * println(stats.getFullStatistics()) // выводит полную статистику
 * ```
 */
data class TextStats(
    val wordCount: Int,
    val charCount: Int,
    val wordMeetingRate: Map<String, Int>,
    val memoryUsed: Int,
    val uniqueWordCount: Int
) {
    companion object {
        /** Минимальная ширина колонки для отображения слов в таблице */
        private const val MIN_WORD_COLUMN_WIDTH = 12

        /** Ширина колонки для отображения частоты слов в таблице */
        private const val COUNT_WIDTH = 10
    }

    /**
     * Возвращает строковое представление статистики в кратком формате.
     *
     * Включает основную статистику и таблицу топ-10 самых частых слов.
     * Форматирует данные в виде читабельного отчета с разделителями и иконками.
     *
     * @return форматированная строка с краткой статистикой текста
     */
    override fun toString(): String {
        val separator = "=".repeat(50)
        val topWords = getTopWords(10)

        return """
        |$separator
        |           СТАТИСТИКА ТЕКСТА
        |$separator
        |
        |📊 ОСНОВНАЯ СТАТИСТИКА:
        |   • Количество слов: $wordCount
        |   • Количество символов: $charCount
        |   • Уникальных слов: $uniqueWordCount
        |   • Использовано памяти: ${"%.2f".format(memoryUsed / 1024.0)} КБ
        |
        |📈 ТОП-10 САМЫХ ЧАСТЫХ СЛОВ:
        |${formatWordMap(topWords)}
        |
        |$separator
        """.trimMargin()
    }

    /**
     * Форматирует карту слов в виде текстовой таблицы.
     *
     * Создает таблицу в псевдографическом стиле с колонками "СЛОВО" и "ЧАСТОТА".
     * Автоматически подбирает ширину колонок на основе данных.
     *
     * @param wordMap карта слов для отображения (слово -> частота)
     * @return форматированная строка с таблицей слов
     */
    private fun formatWordMap(wordMap: Map<String, Int>): String {
        if (wordMap.isEmpty()) {
            return "   Нет данных о словах"
        }

        val maxWordLength = wordMap.keys.maxOfOrNull { it.length } ?: 0
        val wordWidth = maxOf(maxWordLength, 8) + 2
        val countWidth = 8

        val header = "   ┌${"─".repeat(wordWidth)}┬${"─".repeat(countWidth)}┐\n" +
                "   │ ${"СЛОВО".padEnd(wordWidth - 1)}│ ${"ЧАСТОТА".padEnd(countWidth - 1)}│\n" +
                "   ├${"─".repeat(wordWidth)}┼${"─".repeat(countWidth)}┤"

        val rows = wordMap.entries.joinToString("\n") { (word, count) ->
            "   │ ${word.padEnd(wordWidth - 1)}│ ${count.toString().padEnd(countWidth - 1)}│"
        }

        val footer = "   └${"─".repeat(wordWidth)}┴${"─".repeat(countWidth)}┘"

        return "$header\n$rows\n$footer"
    }

    /**
     * Возвращает топ-N самых частых слов.
     *
     * Сортирует слова по убыванию частоты и возвращает указанное количество самых частых слов.
     *
     * @param limit максимальное количество возвращаемых слов
     * @return карта с наиболее часто встречающимися словами (слово -> частота)
     */
    private fun getTopWords(limit: Int): Map<String, Int> {
        return wordMeetingRate.entries
            .sortedByDescending { it.value }
            .take(limit)
            .associate { it.key to it.value }
    }

    /**
     * Возвращает полную статистику текста в расширенном формате.
     *
     * Включает расширенную статистику (включая среднюю длину слова) и полную таблицу
     * всех слов с их частотой и процентным соотношением.
     *
     * @return форматированная строка с полной статистикой текста
     */
    fun getFullStatistics(): String {
        val separator = "=".repeat(60)

        return """
        |$separator
        |           ПОЛНАЯ СТАТИСТИКА ТЕКСТА
        |$separator
        |
        |📊 ОСНОВНАЯ СТАТИСТИКА:
        |   • Общее количество слов: $wordCount
        |   • Количество символов: $charCount
        |   • Уникальных слов: $uniqueWordCount
        |   • Использовано памяти: ${"%.2f".format(memoryUsed / 1024.0)} КБ
        |   • Средняя длина слова: ${"%.1f".format(charCount.toDouble() / wordCount)} символов
        |
        |📈 ЧАСТОТА СЛОВ (всего ${wordMeetingRate.size} уникальных слов):
        |${formatFullWordMap()}
        |
        |$separator
        """.trimMargin()
    }

    /**
     * Форматирует полную карту слов с процентным соотношением.
     *
     * Создает подробную таблицу со всеми словами, их абсолютной частотой и
     * процентным соотношением от общего количества слов. Слова сортируются по убыванию частоты.
     *
     * @return форматированная строка с полной таблицей слов
     */
    private fun formatFullWordMap(): String {
        if (wordMeetingRate.isEmpty()) {
            return "   Нет данных о словах"
        }

        val sortedWords = wordMeetingRate.entries.sortedByDescending { it.value }
        val maxWordLength = sortedWords.maxOfOrNull { it.key.length } ?: 0
        val wordWidth = maxOf(maxWordLength, MIN_WORD_COLUMN_WIDTH) + 2

        val header = "   ┌${"─".repeat(wordWidth)}┬${"─".repeat(COUNT_WIDTH)}┐\n" +
                "   │ ${"СЛОВО".padEnd(wordWidth - 1)}│ ${"ВСТРЕЧАЕМОСТЬ".padEnd(COUNT_WIDTH - 1)}│\n" +
                "   ├${"─".repeat(wordWidth)}┼${"─".repeat(COUNT_WIDTH)}┤"

        val rows = sortedWords.joinToString("\n") { (word, count) ->
            val percentage = "%.1f%%".format(count.toDouble() / wordCount * 100)
            "   │ ${word.padEnd(wordWidth - 1)}│ ${"$count ($percentage)".padEnd(COUNT_WIDTH - 1)}│"
        }

        val footer = "   └${"─".repeat(wordWidth)}┴${"─".repeat(COUNT_WIDTH)}┘"

        return "$header\n$rows\n$footer"
    }
}