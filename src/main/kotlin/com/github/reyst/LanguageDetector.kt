package com.github.reyst

import sun.misc.Regexp
import java.util.regex.Pattern

class LanguageDetector {

    private val regexp = Regexp(LANGUAGE_RESOURCE_PATTERN)

    fun getLanguageCode(folderName: String): String {

        val pattern = Pattern.compile(regexp.exp)
        val regExpResult = pattern.matcher(folderName)

        if(regExpResult.matches()) {
            return regExpResult.group(2)
        }
        return ""
    }
}