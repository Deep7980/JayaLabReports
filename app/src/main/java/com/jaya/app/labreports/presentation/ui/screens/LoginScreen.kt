package com.jaya.app.labreports.presentation.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaya.app.labreports.R
import com.jaya.app.labreports.presentation.ui.composables.AppButton
import com.jaya.app.labreports.presentation.ui.composables.MobileNumberInputField
import com.jaya.app.labreports.presentation.ui.composables.PinView
import com.jaya.app.labreports.presentation.ui.navigation.AppRoute
import com.jaya.app.labreports.presentation.ui.navigation.Destination
import com.jaya.app.labreports.presentation.ui.theme.PrimaryVariant
import com.jaya.app.labreports.presentation.viewModels.LoginViewModel
import com.jaya.app.labreports.presentation.viewstates.SavableMutableState
import com.jaya.app.labreports.utilities.Image
import com.jaya.app.labreports.utilities.OnEffect
import com.jaya.app.labreports.utilities.Text
import com.jaya.app.labreports.utilities.animationSpec
import com.jaya.app.labreports.utilities.statusBarColor



object LoginScreen : AppRoute<LoginViewModel> {
    override val destination: Destination
        get() = Destination.Login

    @Composable
    override fun attachedVM(): LoginViewModel = hiltViewModel()

    @Composable
    override fun Content(viewModel: LoginViewModel) = LoginScreen(viewModel = viewModel)

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarColor(
                color = Color.White
            ),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                UpperSection()
                LowerSection(
                    onNumberChange = viewModel::onNumberChange,
                    sendCode = viewModel::sendCodeToNumber,
                    sendStatus = viewModel.sendStatus,
                    btnLoading = viewModel.sendLoading,
                    btnState = viewModel.getCodeBtnState,
                    mobileNumber = viewModel.mobileNumber,
                    onEditNumber = viewModel::editNumber,
                    code = viewModel.userCode,
                    onChangeCode = viewModel::onChangeCode,
                    verifyCode = viewModel::verifyCode,
                    resendOtpClicked = viewModel::sendCodeToNumber,
                    otpSending = viewModel.sendLoading,
                    codeBtnLoading = viewModel.codeVerifyLoading,
                    codeBtnState = viewModel.codeBtnState
                )

            }
            R.string.all_rights_reserved.Text(
                style = MaterialTheme.typography.body1.copy(
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }


    }

    EffectHandlers(viewModel, snackbarHostState)

}

@Composable
private fun ColumnScope.UpperSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(2f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        R.drawable.logo.Image(
            modifier = Modifier
                .fillMaxWidth()
                .size(125.dp),
        )
        R.string.welcome_to_jaya.Text(
            style = MaterialTheme.typography.h4.copy(
                textAlign = TextAlign.Center
            ),
        )
        R.string.login_here.Text(style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun ColumnScope.LowerSection(
    onNumberChange: (String) -> Unit,
    sendCode: () -> Unit,
    sendStatus: SavableMutableState<Boolean>,
    btnLoading: Boolean,
    btnState: SavableMutableState<Boolean>,
    mobileNumber: SavableMutableState<String>,
    onEditNumber: () -> Unit,
    code: SavableMutableState<String>,
    onChangeCode: (String) -> Unit,
    verifyCode: () -> Unit,
    resendOtpClicked: () -> Unit,
    otpSending: Boolean,
    codeBtnState: SavableMutableState<Boolean>,
    codeBtnLoading: Boolean
){

    Crossfade(
        targetState = sendStatus.value,
        modifier = Modifier
            .fillMaxWidth()
            .weight(3f)
            .imePadding(),
        animationSpec = animationSpec()
    ) {
        when (it) {
            true -> Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 18.dp
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(width = 1.dp, color = PrimaryVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        mobileNumber.value.Text(
                            style = MaterialTheme.typography.h5.copy(color = Color.LightGray)
                        )
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(onClick = onEditNumber),
                            tint = Color.LightGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                PinView(
                    pinText = code.value,
                    onPinTextChange = onChangeCode,

                    )
                Spacer(modifier = Modifier.height(25.dp))
                AppButton(
                    text = R.string.verify_now,
                    onBtnClicked = verifyCode,
                    btnState = codeBtnState.value,
                    loadingState = btnLoading
                )
                TextButton(
                    onClick = resendOtpClicked,
                    modifier = Modifier.padding(10.dp)
                ) {
                    when (otpSending) {
                        true -> R.string.otp_sending.Text(
                            style = MaterialTheme.typography.button.copy(
                                color = PrimaryVariant
                            )
                        )

                        false -> R.string.resend_otp.Text(
                            style = MaterialTheme.typography.button.copy(
                                color = PrimaryVariant
                            )
                        )
                    }
                }
            }

            false -> Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MobileNumberInputField(
                    onNumberChange = onNumberChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 18.dp
                        ),
                    textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = PrimaryVariant,
                        backgroundColor = Color.White
                    )
                )
                AppButton(
                    text = R.string.get_otp_verification,
                    onBtnClicked = sendCode,
                    loadingState = btnLoading,
                    btnState = btnState.value
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EffectHandlers(viewModel: LoginViewModel, snackbarHostState: SnackbarHostState) {

    val context = LocalContext.current
    viewModel.notifier.OnEffect(
        intentionalCode = {
            if (it.isNotEmpty()) {
                snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
            }
        },
        clearance = { "" }
    )

}

