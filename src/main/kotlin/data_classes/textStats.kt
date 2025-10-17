package org.example.data_classes

data class TextStats(val wordCount: Int,
                     val charCount: Int,
                     val wordAppearCount: Int,
                     val wordMeetingsAt: Map<String, Int>,
                     val memoryUsed: Int
                        )
