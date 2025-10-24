package org.example.StatsCalculator

import org.example.data_classes.Sentence
import org.example.data_classes.TextStats

/**
 * Интерфейс для расчета статистики текста из коллекции предложений.
 *
 * Определяет контракт для классов, которые реализуют различные алгоритмы расчета
 * статистических показателей текста. Позволяет легко заменять реализации калькулятора
 * без изменения клиентского кода.
 *
 * @see SentencesStatsCalculator для стандартной реализации
 * @see TextStats для формата возвращаемых данных
 *
 * @example
 * ```
 * // Использование с различными реализациями
 * val calculator: TextStatsCalculator = SentencesStatsCalculator()
 * val stats = calculator.calculate(sentences)
 *
 * // Возможность подменить реализацию
 * val advancedCalculator: TextStatsCalculator = AdvancedStatsCalculator()
 * val advancedStats = advancedCalculator.calculate(sentences)
 * ```
 */
interface TextStatsCalculator {
    /**
     * Вычисляет статистику текста на основе переданных предложений.
     *
     * Реализации этого метода должны анализировать предоставленные предложения
     * и возвращать комплексную статистику текста.
     *
     * @param sentences список предложений для анализа. Может быть пустым.
     * @return объект [TextStats] с рассчитанной статистикой текста
     * @example
     * ```
     * val sentences = listOf(
     *     Sentence("Первое предложение."),
     *     Sentence("Второе предложение!")
     * )
     * val stats = calculator.calculate(sentences)
     * println("Слов: ${stats.wordCount}")
     * ```
     */
    fun calculate(sentences: List<Sentence>): TextStats
}