package com.jaya.app.labreports.presentation.viewstates

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.jaya.app.labreports.R
import com.jaya.app.labreports.presentation.ui.navigation.Destination

enum class DrawerMenus(
    @StringRes val mName: Int,
    @DrawableRes val mIcon: Int,
    val mDestination: Destination
) {
    AvailableQuotes(
        mName = R.string.available_quote,
        mIcon = R.drawable.user,
        mDestination = Destination.AvailableQuote
    ),
    Logout(R.string.logout, R.drawable.logo, Destination.NONE)
}