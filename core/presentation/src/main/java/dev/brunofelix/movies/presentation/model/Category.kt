package dev.brunofelix.movies.presentation.model

import androidx.annotation.StringRes

/**
 * An option of a [dev.brunofelix.movies.presentation.components.CategorySelector].
 */
interface Category {
    @get:StringRes
    val titleResId: Int
}
