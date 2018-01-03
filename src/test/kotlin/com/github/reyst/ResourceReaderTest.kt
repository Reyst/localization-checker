package com.github.reyst

import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File
import java.net.URL

class ResourceReaderTest {

    private lateinit var resourceReader: ResourceReader

    @Before
    fun setUp() {
        resourceReader = ResourceReader()
    }

    @Test
    fun shouldReturnMapOfTranslatableStringWithKeysFromDocument() {

        val defaultStrings = resourceReader.readStrings(getFile("values/bottom_bar_strings.xml"))
        assertThat(defaultStrings, notNullValue())
        assertThat(defaultStrings.keys, hasItems("str_tourists", "str_inbox", "str_profile_bn", "str_events"))
    }

    @Test
    fun shouldReturnMapOfTranslatableStringWithKeysFromDocument2() {

        val defaultStrings = resourceReader.readStrings(getFile("values-de/bottom_bar_strings.xml"))
        assertThat(defaultStrings, notNullValue())
        assertThat(defaultStrings.keys, hasItems("str_tourists", "str_inbox", "str_profile_bn", "str_events"))
    }

    private fun getFile(path: String): File {
        val url = getFileUrl(path)
        return File(url.toURI())
    }

    private fun getFileUrl(pathToResource: String): URL {
        return Thread.currentThread().contextClassLoader.getResource(pathToResource)
    }


}