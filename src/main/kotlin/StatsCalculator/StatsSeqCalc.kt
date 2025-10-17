package org.example.StatsCalculator

import org.example.data_classes.Sentence
import org.example.data_classes.TextStats

class SentencesStatsCalculator {
    val CHAR_SIZE_IN_BYTES = 2;
    fun calculate(sentences: List<Sentence>): TextStats {
        val allWords = sentences.flatMap { it.words() }
        val wordCount = allWords.size
        val charCount = allWords.sumOf { it.word.length }
        val wordMeetingsRate = allWords
            .groupingBy { it.word }
            .eachCount()

        val wordAppearCount = wordMeetingsRate.values.sum()
        val uniqueWordsCount = wordMeetingsRate.size
        val memoryUsed = sentences.sumOf { it.sentence.length * CHAR_SIZE_IN_BYTES }

        return TextStats(
            wordCount = wordCount,
            charCount = charCount,
            wordAppearCount = wordAppearCount,
            wordMeetingRate = wordMeetingsRate,
            memoryUsed = memoryUsed,
            uniqueWordsCount = uniqueWordsCount
        )
    }
}