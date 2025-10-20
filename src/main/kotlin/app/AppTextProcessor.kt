package org.example.app

import org.example.StatsCalculator.SentencesStatsCalculator
import org.example.data_classes.Sentence
import org.example.text_parsers.TextParser
import org.example.text_readers.ConsoleTextReader
import org.example.text_readers.FileReader
import org.example.text_savers.OriginalTextSaver
import org.example.text_savers.TextSaver
import java.util.*

class AppTextProcessor(
    private val consoleTextReader: ConsoleTextReader = ConsoleTextReader(),
    private val fileReader: FileReader = FileReader(),
    private val textParser: TextParser = TextParser(),
    private val plainTextSaver: TextSaver = OriginalTextSaver(),
    private val statsCalculator: SentencesStatsCalculator = SentencesStatsCalculator()
) : App {
    private val consoleInput = Scanner(System.`in`)
    private var currentSentences: List<Sentence> = emptyList()

    override fun run() {
        greetings()
        chooseFileOrCommandLine()
    }

    private fun greetings() {
        println("Привет, я помогу тебе собрать статистику по тексту и привести его в читаемый вид, убрав лишнее")
        println("Ты хочешь обработать файл или поработать из консоли?")
        println("Введи 'файл' для обработки файла или 'консоль' для работы с консолью")
    }

    private fun chooseFileOrCommandLine() {
        while (true) {
            when (readlnOrNull()?.trim()?.lowercase()) {
                "файл" -> {
                    processFile()
                    break
                }

                "консоль" -> {
                    processFromCommandLine()
                    break
                }

                else -> {
                    println("Неизвестный выбор. Пожалуйста, выберите 'файл' или 'консоль'.")
                }
            }
        }
    }

    private fun processFile() {
        println("Выбран режим обработки файла.")
        val text = readFileContent()
        processText(text)
    }

    private fun processFromCommandLine() {
        println("Выбран режим обработки из консоли.")
        println("Введи свой текст для обработки:")
        val text = consoleTextReader.read()
        processText(text)

    }

    private fun readFileContent(): String {
        println("Укажи путь для чтения файла:")
        val pathRead = readlnOrNull()?.trim() ?: ""
        return fileReader.read(pathRead)
    }

    private fun processText(text: String) {
        val parsedText = textParser.parse(text)
        val stats = statsCalculator.calculate(parsedText)
        println(stats)
        println("Укажите путь для сохранения отредактированного файла")
        val path = readlnOrNull()?.trim()
        plainTextSaver.save(parsedText, path.toString())
    }

}