package com.jimmy.basecompose.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimmy.basecompose.R
import com.jimmy.basecompose.ui.theme.LightGreyTextFieldBackground
import com.jimmy.basecompose.ui.theme.TextFieldCheckColor
import com.jimmy.basecompose.ui.theme.TextFieldHintColor

@Composable
fun ValidationTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    hint: String,
    isValid: Boolean,
    modifier: Modifier = Modifier) {
    val state = rememberTextFieldState()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(color = LightGreyTextFieldBackground, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val state = rememberTextFieldState(value)
        LaunchedEffect(state.text) {
            onValueChange(state.text.toString())
        }
        BasicTextField(
            state = state,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(16.dp),
            textStyle =  MaterialTheme.typography.bodyLarge,
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { innerTextField ->
                if(state.text.isEmpty()) {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextFieldHintColor
                        ),
                    )
                }
                innerTextField()
            }
        )
        if(isValid) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = "Is Valid Icon",
                tint = TextFieldCheckColor,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(16.dp)
            )
        }
    }
}


@Preview(
    showBackground = false,
)
@Composable
private fun ValidationTextFieldNoUsernamePreview() {
    ValidationTextField(
        hint = "Username",
        isValid = false,
        modifier = Modifier
    )
}

@Preview(
    showBackground = false,
)
@Composable
private fun ValidationTextFieldNotValidUsernamePreview() {
    ValidationTextField(
        hint = "Username",
        isValid = false,
        modifier = Modifier
    )
}

@Preview(
    showBackground = false,
)
@Composable
private fun ValidationTextFieldValidUsernamePreview() {
    ValidationTextField(
        hint = "Username",
        isValid = true,
        modifier = Modifier
    )
}