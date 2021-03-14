package com.elican.examplechatapp

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*


val simpleHourFormat = SimpleDateFormat("HH:mm")
fun generatePrettyTimeFromTimestamp(timestampInSeconds: Long): String {
    val timestampInMs = timestampInSeconds * 1000
    val date = Calendar.getInstance()
    date.timeInMillis = timestampInMs
    val timeString = simpleHourFormat.format(timestampInMs)
    return "$timeString - ${date.get(Calendar.DAY_OF_MONTH)} ${generateDayStringFromCode(
        date.get(
            Calendar.DAY_OF_WEEK
        )
    )}, ${generateMonthStringFromCode(date.get(Calendar.MONTH))}"
}


fun generateDayStringFromCode(dayCode: Int): String {
    return when (dayCode) {
        1 -> "Sunday"
        2 -> "Monday"
        3 -> "Tuesday"
        4 -> "Wednesday"
        5 -> "Thursday"
        6 -> "Friday"
        7 -> "Saturday"
        else-> "Unknown"
    }
}

fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}
fun Context.convertDpToPx(dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        this.resources.displayMetrics
    )
}
fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = Math.round(this.convertDpToPx(50F))
    return heightDiff > marginOfError
}

fun hideKeyboard(activity: Activity) {
    val imm: InputMethodManager =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
}

fun generateMonthStringFromCode(monthCode: Int): String {
    return when (monthCode) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> ""
    }
}