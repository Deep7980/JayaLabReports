package com.jaya.app.labreports.presentation.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jaya.app.labreports.presentation.ui.theme.Secondary
import com.jaya.app.labreports.utilities.MobileNumberValidator

@UiComposable
@Composable
internal fun MobileNumberInputField(
    onNumberChange: (String) -> Unit,
    modifier: Modifier,
    textFieldColors: TextFieldColors,
) {
    val phoneNumber = rememberSaveable {
        mutableStateOf("")
    }
    val focusMgr = LocalFocusManager.current
    OutlinedTextField(
        value = phoneNumber.value,
        onValueChange = {
            if (it.isEmpty() || it.contains(Regex("^\\d+\$"))) {
                phoneNumber.value = derivedStateOf {
                    it.take(MobileNumberValidator.MOBILE_NUMBER_PATTERN.count {
                        it == MobileNumberValidator.MASK_CHAR
                    })
                }.value
            }
            if (phoneNumber.value.length == 10) focusMgr.clearFocus()
        },
        modifier = modifier,

        maxLines = 1,
        shape = RoundedCornerShape(8.dp),
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Default.Phone,
//                contentDescription = "",
//                tint = Secondary,
//                modifier = Modifier
//                    .size(45.dp)
//                    .padding(12.dp)
//            )
//        },
        textStyle = MaterialTheme.typography.h5.copy(
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusMgr.clearFocus()
            }
        ),
        visualTransformation = MobileNumberValidator(),
        colors = textFieldColors,
        placeholder = {
            Text(
//                text = MobileNumberValidator.MOBILE_NUMBER_PATTERN,
                text = "Enter your Mobile Number",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.h6.copy(color = Color.LightGray)
            )
        }
    )

    LaunchedEffect(key1 = phoneNumber.value) {
        onNumberChange(phoneNumber.value)
    }
}