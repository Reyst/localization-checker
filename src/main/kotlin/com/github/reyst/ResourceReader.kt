package com.github.reyst

import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class ResourceReader {

    fun readStrings(file: File): Map<String, String> {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val parser = builder.parse(file)

        val xPath = XPathFactory.newInstance().newXPath()
        val expression = "//string"
        val strs = xPath.compile(expression).evaluate(parser, XPathConstants.NODESET) as NodeList

        val result = HashMap<String, String>()

        (0..strs.length)
                .map { strs.item(it) }
                .forEach {
                    if (it != null && it.hasAttributes()) {
                        val translatable = it.attributes.getNamedItem("translatable")
                        if (translatable == null || translatable.nodeValue != "false") {
                            val name = it.attributes.getNamedItem("name").nodeValue
                            val txt = it.textContent
                            result.put(name, txt)
                        }
                    }
                }

        return result
    }


}