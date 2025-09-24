package com.github.reyst

import java.util.regex.Pattern

class LanguageDetector {

    private val regexp = Regex(LANGUAGE_RESOURCE_PATTERN)

    fun getLanguageCode(folderName: String): String {

        val pattern = Pattern.compile(regexp.pattern)
        val regExpResult = pattern.matcher(folderName)

        if(regExpResult.matches()) {
            return regExpResult.group(2)
        }
        return ""
    }
}