package org.example.text_readers

import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.lang.System.Logger

/**
 * Класс для чтения текстовых файлов.
 *
 * Предоставляет функциональность для чтения содержимого текстовых файлов в кодировке UTF-8.
 * Обрабатывает различные исключительные ситуации, такие как отсутствие файла, ошибки ввода-вывода
 * и другие неожиданные ошибки, возвращая пустую строку в случае ошибок и логируя детали.
 *
 * @example
 * ```
 * val reader = FileReader()
 * val content = reader.read("data.txt")
 * if (content.isNotEmpty()) {
 *     println("Файл прочитан успешно, символов: ${content.length}")
 * } else {
 *     println("Не удалось прочитать файл")
 * }
 * ```
 */
class FileReader {
    companion object {
        /**
         * Логгер для записи ошибок и отладочной информации.
         */
        private val log = LoggerFactory.getLogger(FileReader::class.java)
    }

    /**
     * Читает содержимое текстового файла в кодировке UTF-8.
     *
     * @param filePath путь к файлу для чтения
     * @return содержимое файла в виде строки. Возвращает пустую строку в случае:
     *         - файл не существует
     *         - ошибки ввода-вывода при чтении
     *         - любых других исключений
     *
     * @example
     * ```
     * val reader = FileReader()
     *
     * // Успешное чтение
     * val content = reader.read("document.txt")
     *
     * // Чтение несуществующего файла - вернет "" и запишет в лог
     * val empty = reader.read("nonexistent.txt")
     * ```
     */
    fun read(filePath: String): String {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                log.error("Ошибка - файла не существует: $filePath")
                return ""
            }
            file.readText(Charsets.UTF_8)
        } catch (e: IOException) {
            log.error("Ошибка чтения файла: $filePath", e)
            ""
        } catch (e: Exception) {
            log.error("Неизвестная ошибка: $filePath", e)
            ""
        }
    }
}