package dev.brunofelix.movies.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import dev.brunofelix.movies.core.designsystem.R
import dev.brunofelix.movies.designsystem.theme.PMovieTheme
import dev.brunofelix.movies.designsystem.theme.shapeCircle
import dev.brunofelix.movies.designsystem.theme.spacing16

private const val DISABLED_ALPHA = 0.38f

/**
 * A highly customizable and reusable search bar component based on Material 3 guidelines.
 *
 * The input value is hoisted: it is managed externally via the [query] and [onQueryChange]
 * parameters. Only the caret position is kept internally, so that regaining focus does not
 * drop it back to the start of the text.
 * It also handles focus management automatically, clearing the focus and hiding the keyboard
 * when a search is submitted.
 *
 * @param query The current text input to be displayed in the search bar.
 * @param modifier The [Modifier] to be applied to the search bar.
 * @param onQueryChange Callback triggered whenever the user types or modifies the input.
 * @param onSearch Callback triggered when the user presses the search action on the keyboard.
 * Passes the current [query] as a parameter.
 * @param placeholderText The text to be displayed when the input is empty.
 * @param labelText Optional label to be displayed inside the text field container.
 * @param enabled Controls the enabled state of the search bar. If false, it becomes unclickable and unfocusable.
 * @param shape Defines the shape of the search bar. Defaults to a pill-shaped look.
 * @param containerColor The background color of the search bar container.
 * @param unfocusedBorderColor The color of the border when the search bar is not focused.
 * @param focusedBorderColor The color of the border when the search bar is currently focused.
 * @param textColor The color of the inputted text.
 * @param hintColor The color of the placeholder text.
 * @param labelColor The color of the label text when the field is unfocused.
 * @param iconColor The color of the leading (search) and trailing (clear) icons.
 */
@Composable
fun CustomSearchBar(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    placeholderText: String = stringResource(R.string.search_bar_hint),
    labelText: String? = null,
    enabled: Boolean = true,
    shape: Shape = shapeCircle,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedBorderColor: Color = Color.Transparent,
    focusedBorderColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    hintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val focusManager = LocalFocusManager.current

    // Mirrors [query] so the caret survives losing focus, which a plain String value cannot
    // express: it would send the caret back to index 0 and type into the start of the text.
    var fieldValue by remember { mutableStateOf(TextFieldValue(query, TextRange(query.length))) }

    if (fieldValue.text != query) {
        fieldValue = TextFieldValue(query, TextRange(query.length))
    }

    OutlinedTextField(
        value = fieldValue,
        onValueChange = { newValue ->
            fieldValue = newValue
            if (newValue.text != query) onQueryChange(newValue.text)
        },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true,
        shape = shape,
        label = labelText?.let { { Text(text = it) } },
        placeholder = { Text(text = placeholderText) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                // Focus is deliberately kept, so the keyboard stays up to type a new query.
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.search_bar_clear)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(query)
                focusManager.clearFocus()
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            // Background
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,

            // Border
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            disabledBorderColor = unfocusedBorderColor,

            // Text
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor.copy(alpha = DISABLED_ALPHA),

            // Icons
            focusedLeadingIconColor = iconColor,
            unfocusedLeadingIconColor = iconColor,
            focusedTrailingIconColor = iconColor,
            unfocusedTrailingIconColor = iconColor,

            // Placeholder (Hint)
            focusedPlaceholderColor = hintColor.copy(alpha = DISABLED_ALPHA),
            unfocusedPlaceholderColor = hintColor.copy(alpha = DISABLED_ALPHA),

            // Cursor
            cursorColor = hintColor,

            // Label
            focusedLabelColor = focusedBorderColor,
            unfocusedLabelColor = labelColor
        )
    )
}

@Preview
@Composable
private fun EmptyPreview() {
    PMovieTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(spacing16)
        ) {
            CustomSearchBar(query = "")
        }
    }
}

@Preview
@Composable
private fun FilledPreview() {
    PMovieTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(spacing16)
        ) {
            CustomSearchBar(query = "Dune")
        }
    }
}
