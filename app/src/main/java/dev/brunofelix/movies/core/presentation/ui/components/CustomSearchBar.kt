package dev.brunofelix.movies.core.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.brunofelix.movies.R
import dev.brunofelix.movies.core.presentation.ui.theme.PMovieTheme

/**
 * A highly customizable and reusable search bar component based on Material 3 guidelines.
 *
 * This component uses state hoisting, meaning it does not hold its own state. The input value
 * and its updates are managed externally via the [query] and [onQueryChange] parameters.
 * It also handles focus management automatically, clearing the focus and hiding the keyboard
 * when a search is submitted or the query is cleared.
 *
 * @param modifier The [Modifier] to be applied to the search bar.
 * @param query The current text input to be displayed in the search bar.
 * @param onQueryChange Callback triggered whenever the user types or modifies the input.
 * @param onSearch Callback triggered when the user presses the search action on the keyboard.
 * Passes the current [query] as a parameter.
 * @param placeholderText The text to be displayed when the input is empty.
 * @param labelText Optional label to be displayed inside the text field container.
 * @param enabled Controls the enabled state of the search bar. If false, it becomes unclickable and unfocusable.
 * @param shape Defines the shape of the search bar. Defaults to [CircleShape] for a pill-shaped look.
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
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    placeholderText: String = stringResource(R.string.search_bar),
    labelText: String? = null,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    unfocusedBorderColor: Color = Color.Transparent,
    focusedBorderColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    hintColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
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
                IconButton(onClick = {
                    onQueryChange("")
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
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
            disabledTextColor = textColor.copy(alpha = 0.38f),

            // Icons
            focusedLeadingIconColor = iconColor,
            unfocusedLeadingIconColor = iconColor,
            focusedTrailingIconColor = iconColor,
            unfocusedTrailingIconColor = iconColor,

            // Placeholder (Hint)
            focusedPlaceholderColor = hintColor,
            unfocusedPlaceholderColor = hintColor,

            // Label
            focusedLabelColor = focusedBorderColor,
            unfocusedLabelColor = labelColor
        )
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LightPreview() {
    PMovieTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CustomSearchBar(query = "")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DarkPreview() {
    PMovieTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CustomSearchBar(query = "")
        }
    }
}