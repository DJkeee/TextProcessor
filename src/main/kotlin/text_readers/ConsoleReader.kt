package org.example.text_readers

import java.util.Scanner

/**
 * Класс для чтения текста из консольного ввода.
 * Обеспечивает интерактивное чтение многострочного текста от пользователя
 * до ввода специальной строки-терминатора. Поддерживает построчное чтение
 * и объединение введенных строк в единый текст.
*/
class ConsoleTextReader() {
    private val scanner = Scanner(System.`in`)

    /**
     * Читает многострочный текст из консоли до ввода строки-терминатора.
     *
     * Метод последовательно читает строки из системного ввода и объединяет их
     * в единый текст. Чтение прекращается при вводе указанной строки-терминатора.
     * Строка-терминатор не включается в результат.
     *
     * @param terminalString строка, при вводе которой чтение прекращается.
     *                       По умолчанию пустая строка (чтение до первой пустой строки).
     * @return объединенный текст из всех введенных строк (без терминатора)
     *
     * @example
     * ```
     * val reader = ConsoleTextReader()
     *
     * // Чтение до ввода "КОНЕЦ"
     * println("Введите текст (для завершения введите 'КОНЕЦ'):")
     * val text1 = reader.read("КОНЕЦ")
     *
     * // Чтение до пустой строки
     * println("Введите текст (пустая строка для завершения):")
     * val text2 = reader.read()
     * ```
     */
    fun read(terminalString: String = ""): String {
        val text = StringBuilder()
        while (scanner.hasNextLine()) {
            val line = scanner.nextLine()
            if (line == terminalString) break
            text.appendLine(line)
        }
        return text.toString()
    }
}