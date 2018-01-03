package com.github.reyst.data

class StringResource(val id: String, val defaultValue: String, variants: Set<String>) {
    private val translations = HashMap<String, String>(variants.size, 2f)

    fun getTranslation(variant: String) = translations.getOrDefault(variant, "")

    fun addTranslation(variant: String, value: String) {
        translations.put(variant, value)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringResource

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "StringResource(id='$id', defaultValue='$defaultValue', translations=$translations)"
    }
}