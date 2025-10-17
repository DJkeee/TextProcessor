package org.example.data_classes

data class TextStats(val wordCount: Int,
                     val charCount: Int,
                     val wordAppearCount: Int,
                     val wordMeetingRate: Map<String, Int>,
                     val memoryUsed: Int,
                     val uniqueWordCount: Int,
)