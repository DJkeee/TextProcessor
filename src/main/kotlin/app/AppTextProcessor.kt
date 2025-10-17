package org.example.app

import org.example.text_parsers.Parser
import org.example.text_parsers.TextParser
import org.example.text_readers.ConsoleReader
import org.example.text_readers.FileReader
import java.io.File

class AppTextProcessor(parser: Parser,
                       consoleReader: ConsoleReader = ConsoleReader(),
                       fileReader: FileReader = FileReader(),
                       textParser: TextParser) : App {

    public override fun run() {

    }

}