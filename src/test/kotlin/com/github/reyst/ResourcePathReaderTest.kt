package com.github.reyst

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class LanguageDetectorByResourceNameTest {

    private lateinit var languageDetector: LanguageDetector

    @Before
    fun setUp() {
        languageDetector = LanguageDetector()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun shouldReturnLanguageCode() {

        assertThat(languageDetector.getLanguageCode("values-fr-port"), equalTo("fr"))
        assertThat(languageDetector.getLanguageCode("values-fr"), equalTo("fr"))
        assertThat(languageDetector.getLanguageCode("values-fr-v17"), equalTo("fr"))
        assertThat(languageDetector.getLanguageCode("values-fr-v4"), equalTo("fr"))
        assertThat(languageDetector.getLanguageCode("values-fr-land"), equalTo("fr"))
        assertThat(languageDetector.getLanguageCode("values-de"), equalTo("de"))
        assertThat(languageDetector.getLanguageCode("values-car"), equalTo(""))

    }
}