package org.example.text_parsers

import org.example.data_classes.Sentence

/**
 * Реализация парсера текста, который разбивает текст на предложения и выполняет их очистку.
 *
 * Парсер выполняет следующие операции нормализации:
 * - Разбивает текст на предложения по точкам, восклицательным и вопросительным знакам
 * - Удаляет лишние пробелы, оставляя только одинарные
 * - Удаляет повторяющиеся запятые
 * - Обрезает пробелы в начале и конце предложений
 * - Фильтрует пустые предложения
 *
 * @see Parser базовый интерфейс парсера
 * @see Sentence класс, представляющий предложение
 *
 * @example
 * ```
 * val parser = TextParser()
 * val text = "Привет,  мир!!   Как дела?...   Все хорошо."
 * val sentences = parser.parse(text)
 * // Результат:
 * // [Sentence("Привет, мир!"), Sentence("Как дела?"), Sentence("Все хорошо.")]
 * ```
 */
class TextParser : Parser {
    companion object {
        /**
         * Регулярное выражение для разделения текста на предложения.
         *
         * Разделяет текст после символов [.!?], за которыми следует один или более пробельных символов.
         * Использует positive lookbehind ((?<=...)) для включения разделителей в результат.
         */
        private val SENTENCE_SPLIT_REGEX = Regex("""(?<=[.!?])\s+""")

        /**
         * Регулярное выражение для поиска множественных пробелов.
         *
         * Заменяет последовательности из двух и более пробельных символов на один пробел.
         */
        private val MULTIPLE_SPACE_REGEX = Regex("""\s+""")

        /**
         * Регулярное выражение для поиска множественных запятых.
         *
         * Заменяет последовательности из двух и более запятых на одну запятую.
         */
        private val MULTIPLE_COMMAS_REGEX = Regex(""",{2,}""")
    }

    /**
     * Разбирает текст на список нормализованных предложений.
     *
     * @param text исходный текст для парсинга
     * @return список объектов [Sentence] с очищенными предложениями.
     *         Возвращает пустой список, если текст пустой или содержит только пробелы.
     *
     * @example
     * ```
     * val parser = TextParser()
     * val result = parser.parse("Привет!     Как дела?")
     * // result: [Sentence("Привет!"), Sentence("Как дела?")]
     * ```
     */
    override fun parse(text: String): List<Sentence> {
        if (text.isBlank()) return emptyList()

        return text
            .splitToSentences()
            .map { cleanSentence(it) }
            .filter { it.isNotBlank() }
            .map { Sentence(it) }
    }

    /**
     * Разбивает строку на предложения с использованием регулярного выражения.
     *
     * @return список строк, каждая из которых представляет отдельное предложение
     */
    private fun String.splitToSentences(): List<String> {
        return split(SENTENCE_SPLIT_REGEX)
    }

    /**
     * Очищает предложение от лишних символов.
     *
     * Выполняет следующие операции очистки:
     * - Заменяет множественные запятые на одинарные
     * - Заменяет множественные пробелы на одинарные
     * - Обрезает пробелы в начале и конце строки
     *
     * @param sentence исходное предложение для очистки
     * @return очищенное предложение
     */
    private fun cleanSentence(sentence: String): String {
        return sentence
            .replace(MULTIPLE_COMMAS_REGEX, ",")
            .replace(MULTIPLE_SPACE_REGEX, " ")
            .trim()
    }
}