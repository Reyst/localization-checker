package com.github.reyst.data

class FileResource(val filename: String) {

    val strings = HashMap<String, StringResource>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileResource

        if (filename != other.filename) return false

        return true
    }

    override fun hashCode(): Int {
        return filename.hashCode()
    }

    override fun toString(): String {
        return "FileResource(filename='$filename', strings=$strings)"
    }


}