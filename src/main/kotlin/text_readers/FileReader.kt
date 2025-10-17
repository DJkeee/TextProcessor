package org.example.text_readers

import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException
import java.lang.System.Logger

class FileReader {
    companion object {
        private val log = LoggerFactory.getLogger(FileReader::class.java)
    }

    fun read(filePath: String): String {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                log.error("File does not exist: $filePath")
                return ""
            }
            file.readText(Charsets.UTF_8)
        } catch (e: IOException) {
            log.error("Error reading file: $filePath", e)
            ""
        } catch (e: Exception) {
            log.error("Unexpected error reading file: $filePath", e)
            ""
        }
    }
}