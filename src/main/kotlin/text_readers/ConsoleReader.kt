package org.example.text_readers

import java.util.Scanner

class ConsoleReader() {
    private val scanner = Scanner(System.`in`)

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