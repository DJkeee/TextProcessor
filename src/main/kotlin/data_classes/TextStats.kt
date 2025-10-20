package org.example.data_classes

data class TextStats(
    val wordCount: Int,
    val charCount: Int,
    val wordMeetingRate: Map<String, Int>,
    val memoryUsed: Int,
    val uniqueWordCount: Int
) {
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

    private fun getTopWords(limit: Int): Map<String, Int> {
        return wordMeetingRate.entries
            .sortedByDescending { it.value }
            .take(limit)
            .associate { it.key to it.value }
    }

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

    private fun formatFullWordMap(): String {
        if (wordMeetingRate.isEmpty()) {
            return "   Нет данных о словах"
        }

        val sortedWords = wordMeetingRate.entries.sortedByDescending { it.value }
        val maxWordLength = sortedWords.maxOfOrNull { it.key.length } ?: 0
        val wordWidth = maxOf(maxWordLength, 12) + 2
        val countWidth = 10

        val header = "   ┌${"─".repeat(wordWidth)}┬${"─".repeat(countWidth)}┐\n" +
                "   │ ${"СЛОВО".padEnd(wordWidth - 1)}│ ${"ВСТРЕЧАЕМОСТЬ".padEnd(countWidth - 1)}│\n" +
                "   ├${"─".repeat(wordWidth)}┼${"─".repeat(countWidth)}┤"

        val rows = sortedWords.joinToString("\n") { (word, count) ->
            val percentage = "%.1f%%".format(count.toDouble() / wordCount * 100)
            "   │ ${word.padEnd(wordWidth - 1)}│ ${"$count ($percentage)".padEnd(countWidth - 1)}│"
        }

        val footer = "   └${"─".repeat(wordWidth)}┴${"─".repeat(countWidth)}┘"

        return "$header\n$rows\n$footer"
    }
}