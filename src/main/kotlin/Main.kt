package org.example

import org.example.StatsCalculator.SentencesStatsCalculator

import org.example.StatsCalculator.TextStatsCalculator
import org.example.text_parsers.TextParser
import org.example.text_readers.ConsoleReader
import org.example.text_readers.FileReader
import java.io.File

fun main() {
    val parser = TextParser()
    val ConsoleReader = FileReader()
    val testText = ConsoleReader.read("C:\\Users\\Dell\\Documents\\GitHub\\text_processor\\src\\main\\kotlin\\example.txt")
    //  val testText = "Hello,,,,              words!! Это     тестовый    текст... Как дела?"
    val sentences = parser.parse(testText)
    sentences.forEach {
        println(it.words())
    }
    println(sentences)
    val calc: SentencesStatsCalculator = SentencesStatsCalculator();
    val result = calc.calculate(sentences)
    println(result)
}