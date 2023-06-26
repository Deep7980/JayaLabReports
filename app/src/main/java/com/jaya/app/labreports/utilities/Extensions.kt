package com.jaya.app.labreports.utilities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jaya.app.labreports.core.common.constants.DataEntry
import com.jaya.app.labreports.core.common.constants.EntryType
import com.jaya.app.labreports.core.common.sealed.Response
import kotlinx.coroutines.flow.FlowCollector
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


fun String.shortToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}
@Composable
fun Int.resource() = stringResource(id = this)

val density: Float
    get() = Resources.getSystem().displayMetrics.density


fun Int.string(): String = Resources.getSystem().getString(this)

fun <T> Any.castListToRequiredTypes(): List<T>? {
    val items = mutableListOf<T>()

    if (this !is List<*>) return null

    forEach { item -> item?.let { items.add(it as T) } }

    return items.toList()
}

inline fun <reified T1, reified T2> Any.castMapToRequiredTypes(): Map<T1, T2>? {

    val data = mutableMapOf<T1, T2>()

    if (this !is Map<*, *>) return null

    forEach { (k, v) -> data[k as T1] = v as T2 }

    return data.toMap()
}


suspend inline fun <reified R> FlowCollector<DataEntry>.handleFailedResponse(
    response: Response.Error<R>,
    type: EntryType = EntryType.NETWORK_ERROR,
) = emit(DataEntry(type, response.message))

inline fun <reified T> Any.castValueToRequiredTypes(): T? {
    return this as? T
}

inline fun <reified K, reified V> Any.castPairToRequiredType(): Pair<K, V>? {

    if (this !is Pair<*, *>) return null

    return (this.first as K) to (this.second as V)
}


@Composable
fun <T> MutableState<T>.OnEffect(
    intentionalCode: suspend (T) -> Unit,
    clearance: () -> T,
) {
    LaunchedEffect(key1 = value) {
        value?.let {
            intentionalCode(it)
            value = clearance()
        }
    }
}

fun Modifier.statusBarColor(color: Color = Color.White): Modifier = composed {
    val sysUiController = rememberSystemUiController()
    sysUiController.setStatusBarColor(
        color = color,
        darkIcons = true,
        transformColorForLightContent = { color }
    )
    this
}

@Composable
fun Int.Text(style: TextStyle, modifier: Modifier = Modifier) =
    androidx.compose.material.Text(
        text = stringResource(id = this),
        style = style,
        modifier = modifier
    )


@Composable
fun String.Text(style: TextStyle, modifier: Modifier = Modifier) =
    androidx.compose.material.Text(text = this, style = style, modifier = modifier)

@UiComposable
@Composable
fun Int.ResponsiveText(
    textAlign: TextAlign? = null,
    fontSize: TextUnit? = null,
    style: TextStyle = LocalTextStyle.current,
    color: Color = style.color,
    modifier: Modifier = Modifier
) = com.jaya.app.labreports.presentation.ui.composables.ResponsiveText(
    text = stringResource(id = this),
    modifier = modifier,
    textStyle = style,
    textAlign = textAlign ?: TextAlign.Center,
    targetTextSizeHeight = fontSize ?: style.fontSize, color = color)

@UiComposable
@Composable
fun String.ResponsiveText(
    textAlign: TextAlign? = null,
    fontSize: TextUnit? = null,
    style: TextStyle = LocalTextStyle.current,
    color: Color = style.color,
    modifier: Modifier = Modifier
) = com.jaya.app.labreports.presentation.ui.composables.ResponsiveText(
    text = this,
    modifier = modifier,
    textStyle = style,
    textAlign = textAlign ?: TextAlign.Center,
    targetTextSizeHeight = fontSize ?: style.fontSize, color = color)

@Composable
fun Int.Image(modifier: Modifier = Modifier, scale: ContentScale = ContentScale.Fit) =
    androidx.compose.foundation.Image(
        painter = painterResource(id = this),
        contentDescription = toString(),
        modifier = modifier,
        contentScale = scale,
    )

@Composable
fun Int.Icon(modifier: Modifier = Modifier, tint: Color? = null) =
    androidx.compose.material.Icon(
        painter = painterResource(id = this),
        contentDescription = toString(),
        modifier = modifier,
        tint = tint ?: LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    )

fun DataEntry.handleErrors(): String? = when (type) {
    EntryType.NETWORK_ERROR -> data?.castValueToRequiredTypes<String>()
    EntryType.BACKEND_ERROR -> data?.castValueToRequiredTypes<String>()
    else -> null
}

val screenHeight
    @Composable
    @ReadOnlyComposable
    get() = LocalConfiguration.current.screenHeightDp.dp

val screenWidth
    @Composable
    @ReadOnlyComposable
    get() = LocalConfiguration.current.screenWidthDp.dp

fun Context.openPlayStore(link: String) {
    val intent = Intent("market://details?id=$packageName")
    var isFound = false

    val otherApps: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
    for (otherApp in otherApps) {
        // look for Google Play application
        if (otherApp.activityInfo.applicationInfo.packageName
                .equals("com.android.vending")
        ) {
            val otherAppActivity: ActivityInfo = otherApp.activityInfo
            val componentName = ComponentName(
                otherAppActivity.applicationInfo.packageName,
                otherAppActivity.name
            )
            // make sure it does NOT open in the stack of your activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // task reparenting if needed
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            // if the Google Play was already open in a search result
            //  this make sure it still go to the app page you requested
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            // this make sure only the Google Play app is allowed to
            // intercept the intent
            intent.component = componentName
            startActivity(intent)
            isFound = true
            break
        }
    }

    if (!isFound) {
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        startActivity(webIntent)
    }
}

inline fun <reified T> T.encodeJson(): String = Gson().toJson(this, object : TypeToken<T>() {}.type)

inline fun <reified T> String.decodeJson(): T =
    Gson().fromJson(this, object : TypeToken<T>() {}.type)

fun Long.millisToDateString(format: String = "dd MMMM yyyy"): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.ofEpochMilli(this).atZone(ZoneId.of("IST"))
            .toLocalDate().format(
                DateTimeFormatter.ofPattern(format)
            )
    } else {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("IST"))
        calendar.timeInMillis = this
        SimpleDateFormat(format, Locale.ENGLISH).format(calendar)
    }


fun <T> animationSpec() = tween<T>(
    durationMillis = 550,
    easing = LinearOutSlowInEasing
)