package com.github.reyst

import com.github.reyst.data.FileResource
import com.github.reyst.data.StringResource
import java.io.File

fun main(vararg params: String) {

    if (params.isEmpty()) return
    val rootPath = params[0]

    val reportFilename = when {
        params.size > 1 -> params[1]
        else -> "report.xls"
    }
    val resourceReader = ResourceReader()
    val folderList = getResourcesFolderList(rootPath)
    val variants = folderList.toSet()
    val result = initDefaultValues(rootPath, variants, resourceReader)

    variants.forEach { folderName -> handleVariant(rootPath, folderName, result, resourceReader) }

    ReportGenerator(reportFilename).createReportFile(result, variants)

}

private fun initDefaultValues(rootPath: String, variants: Set<String>, resourceReader: ResourceReader): HashMap<String, FileResource> {

    val result = HashMap<String, FileResource>()
    val defaultValues = File(rootPath, DEFAULT_RESOURCE_FOLDER_NAME)
    defaultValues.list()
            .map { File(defaultValues, it) }
            .filter { it.isFile }
            .map { Pair(it.name, resourceReader.readStrings(it)) }
            .filter { it.second.isNotEmpty() }
            .forEach {
                val fileResource = FileResource(it.first)
                it.second.forEach { id, value -> fileResource.strings.put(id, StringResource(id, value, variants)) }
                result.put(it.first, fileResource)
            }

    return result
}

fun handleVariant(root: String, folderName: String, result: HashMap<String, FileResource>, resourceReader: ResourceReader) {

    val translationFolder = File(root, folderName)
    translationFolder
            .list()
            .map { Pair(it, File(translationFolder, it)) }
            .filter { it.second.isFile }
            .map { Pair(it.first, resourceReader.readStrings(it.second)) }
            .filter { it.second.isNotEmpty() }
            .forEach {
                val filename = it.first
                val translation = it.second
                translation.forEach { id, value ->
                    result[filename]?.strings?.get(id)?.addTranslation(folderName, value)
                }
            }
}

private fun getResourcesFolderList(path: String): Array<out String> {
    val resourceRoot = File(path)
    return resourceRoot.list { _, name -> name.matches(Regex(LANGUAGE_RESOURCE_PATTERN)) }
}