package org.example.StatsCalculator

import org.example.data_classes.Sentence
import org.example.data_classes.TextStats

interface TextStatsCalculator {
    fun calculate(sentences: List<Sentence>): TextStats;
}