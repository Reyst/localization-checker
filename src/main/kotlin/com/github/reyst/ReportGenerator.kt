package com.github.reyst

import com.github.reyst.data.FileResource
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


class ReportGenerator(filename: String = "report.xls") {

    private val reportFile = File(filename)

    fun createReportFile(data: HashMap<String, FileResource>, variants: Set<String>) {

        val workbook = HSSFWorkbook()

        val font = workbook.createFont()
        font.color = workbook.customPalette.findColor(0xFF.toByte(), 0.toByte(), 0.toByte()).index
        val errorStyle = workbook.createCellStyle()
        errorStyle.setFont(font)

        val sheet = workbook.createSheet("report")

        var rowNum = 0
        var colNum = 0

        val row = sheet.createRow(rowNum)
        row.createCell(colNum).setCellValue("ID")
        row.createCell(++colNum).setCellValue("Default")
        variants.forEach {
            row.createCell(++colNum).setCellValue(it)
        }

        sheet.createRow(++rowNum)

        data.forEach { _, item ->
            colNum = 0
            sheet.createRow(++rowNum).createCell(colNum).setCellValue(item.filename)
            item.strings.values.forEach { stringRes ->
                val dataRow = sheet.createRow(++rowNum)
                colNum = 0
                dataRow.createCell(colNum).setCellValue(stringRes.id)
                dataRow.createCell(++colNum).setCellValue(stringRes.defaultValue)
                variants.forEach { variant ->
                    val translationCell = dataRow.createCell(++colNum)
                    val translation = stringRes.getTranslation(variant)
                    translationCell.setCellValue(translation)

                    if (translation.isEmpty()) {
                        dataRow.getCell(0).setCellStyle(errorStyle)
                    }

                }
            }
            sheet.createRow(++rowNum)
        }

        try {
            FileOutputStream(reportFile).use { out -> workbook.write(out) }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun createReportFileXML(data: HashMap<String, FileResource>, variants: Set<String>) {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val doc = builder.newDocument()

        val root = doc.createElement("strings")
        doc.appendChild(root)

        data.forEach { _, item ->

            val file = doc.createElement("file")
            file.setAttribute("filename", item.filename)

            item.strings.values.forEach { stringRes ->

                val strRes = doc.createElement("string")
                strRes.setAttribute("name", stringRes.id)
                strRes.setAttribute("default", stringRes.defaultValue)

                variants.forEach { variant ->
                    val translation = doc.createElement("translation")
                    translation.setAttribute("language", variant)
                    translation.appendChild(doc.createTextNode(stringRes.getTranslation(variant)))
                    strRes.appendChild(translation)
                }
                file.appendChild(strRes)
            }
            root.appendChild(file)
        }


        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.transform(DOMSource(doc), StreamResult(reportFile))
    }
}