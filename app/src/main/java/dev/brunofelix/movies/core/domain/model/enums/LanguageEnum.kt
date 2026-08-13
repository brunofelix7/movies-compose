package dev.brunofelix.movies.core.domain.model.enums

/**
 * Supported languages for the TMDB API.
 *
 * @property code The ISO 639-1 language code used by the API.
 * @property description Human-readable description of the language.
 */
enum class LanguageEnum(val code: String, val description: String) {
    ENGLISH("en", "English"),
    PORTUGUESE("pt-BR", "Português"),
    SPANISH("es", "Español");

    companion object {
        /**
         * Returns the [LanguageEnum] corresponding to the given [code], or [ENGLISH] as fallback.
         */
        fun fromCode(code: String?): LanguageEnum {
            return entries.find { it.code == code } ?: ENGLISH
        }
    }
}