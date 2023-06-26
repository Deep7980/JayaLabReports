package com.jaya.app.labreports.presentation.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaya.app.labreports.presentation.ui.theme.PrimaryVariant
import com.jaya.app.labreports.presentation.ui.theme.Surface


const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1
@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = Color.Black,
    digitSize: TextUnit = 25.sp,
    containerSize: Dp = digitSize.value.dp * 3,
    digitCount: Int = 4,
    type: Int = PIN_VIEW_TYPE_BORDER,
) {

    val focusMgr = LocalFocusManager.current

    BasicTextField(value = pinText,
        onValueChange = { otp ->
            if (otp.length == digitCount) {
                focusMgr.clearFocus()
            }

            onPinTextChange(otp)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        cursorBrush = SolidColor(PrimaryVariant),
        textStyle = MaterialTheme.typography.h5.copy(
            fontWeight = FontWeight.Bold
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(digitCount) { index ->
                    DigitView(index, pinText, digitColor, digitSize, containerSize, type = type)
                    if (index != digitCount - 1) Spacer(modifier = Modifier.width(18.dp))
                }
            }
        })
}

@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    digitColor: Color,
    digitSize: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
) {
    val modifier = if (type == PIN_VIEW_TYPE_BORDER) {
        Modifier
            .width(containerSize)
            .height(containerSize)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .clip(CircleShape)
            .background(color = Surface)
            .padding(16.dp)
    } else Modifier.width(containerSize)

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (index >= pinText.length) "--" else pinText[index].toString(),
            style = MaterialTheme.typography.h5,
            color = digitColor,
            fontSize = digitSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
        if (type == PIN_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(digitColor)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}



