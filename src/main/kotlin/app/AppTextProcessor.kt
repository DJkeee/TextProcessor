package org.example.app

import org.example.StatsCalculator.TextStatsCalculator
import org.example.data_classes.Sentence
import org.example.text_parsers.TextParser
import org.example.text_readers.ConsoleTextReader
import org.example.text_readers.FileReader
import org.example.text_savers.TextSaver
import java.util.*

class AppTextProcessor(
    private val consoleTextReader: ConsoleTextReader = ConsoleTextReader(),
    private val fileReader: FileReader = FileReader(),
    private val textParser: TextParser,
    private val plainTextSaver: TextSaver,
    private val markdownTextSaver: TextSaver,
    private val statsCalculator: TextStatsCalculator
) : App {
    private val consoleInput = Scanner(System.`in`)
    private var currentSentences: List<Sentence> = emptyList()

    override fun run() {
        greetings()
        chooseFileOrCommandLine()



    }
    private fun chooseFileOrCommandLine() {
        val userInput = readLine()?.trim()?.lowercase()

        when (userInput) {
            "файл" -> processFile()
            "строка" -> processFromCommandLine()
            else -> {
                println("Неизвестный выбор. Пожалуйста, выберите 'файл' или 'строка'.")
                chooseFileOrCommandLine() // рекурсивно запросим ввод снова
            }
        }
    }

    private fun greetings() {
        println("Привет, как я помогу тебе собрать статистику по приложению и привести его в читаемый вид, убрав лишнее")
        println("Ты хочешь обработать файл или поработать из строки?")
    }

    private fun processFile() {
        println("Выбран режим обработки файла.")

    }

    private fun processFromCommandLine() {
        println("Выбран режим обработки из строки.")
//