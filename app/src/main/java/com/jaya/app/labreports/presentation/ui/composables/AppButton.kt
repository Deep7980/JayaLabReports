package com.jaya.app.labreports.presentation.ui.composables

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jaya.app.labreports.presentation.ui.theme.PrimaryVariant
import com.jaya.app.labreports.utilities.ResponsiveText
import com.jaya.app.labreports.utilities.animationSpec

@Composable
fun AppButton(
    loadingState: Boolean = false,
    btnState: Boolean = true,
    modifier: Modifier? = null,
    @StringRes text: Int,
    onBtnClicked: () -> Unit,
    btnColor: Color? = null,
    textColor: Color? = null
) {
    ElevatedButton(
        onClick = onBtnClicked,
        modifier = modifier ?: Modifier
            .fillMaxWidth(fraction = .90f)
            .height(height = 60.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = btnColor ?: PrimaryVariant,
            contentColor = MaterialTheme.colors.background,
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            pressedElevation = 7.dp,
        ),
        enabled = if (loadingState) false else btnState
    ) {
        AnimatedContent(
            targetState = loadingState,
            contentAlignment = Alignment.Center,
            transitionSpec = {
                fadeIn(animationSpec()) togetherWith fadeOut(animationSpec())
            }
        ) { state ->
            when (state) {
                true -> CircularProgressIndicator(
                    color = MaterialTheme.colors.background,
                )
                false -> text.ResponsiveText(
                    style = MaterialTheme.typography.button.copy(
                        color = textColor ?: Color.White
                    )
                )
            }
        }
    }
}