@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.mosque.utils

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Handler
import androidx.core.app.ActivityCompat
import com.example.mosque.R
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.PREF_KEY_SERVICE_NAME
import com.example.mosque.common.Constans.PREF_KEY_SERVICE_STATE
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.seconds


var timeCountTime: String= ""

fun isPermissionsGranted(context: Context) =
    ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

fun setServiceState(context: Context, state: Constans.ServiceState) {
    val sharedPrefs = getPreferences(context)
    sharedPrefs.edit().let {
        it.putString(PREF_KEY_SERVICE_STATE, state.name)
        it.apply()
    }
}

fun getServiceState(context: Context): Constans.ServiceState {
    val sharedPrefs = getPreferences(context)
    val value =
        sharedPrefs.getString(PREF_KEY_SERVICE_STATE, Constans.ServiceState.STOPPED.name)
    return Constans.ServiceState.valueOf(value.toString())
}

private fun getPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences(PREF_KEY_SERVICE_NAME, 0)
}

fun convertDateFromString (strResponsDate: String?): String {

    if (strResponsDate.isNullOrEmpty()){
        return  "-"
    } else {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID"))
        val date = format.parse(strResponsDate)
        val mFormat = SimpleDateFormat("dd MMMM yyyy", Locale("in", "ID"))
        return mFormat.format(date)
    }

}

fun getCurrentTime(): String { //date output format
    val dateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale("in", "ID"))
    val cal = Calendar.getInstance()
    return dateFormat.format(cal.time)
} // end of getCurrentTime()

fun convertDateFromStringToDay (strResponsDate: String?): String {

    if (strResponsDate.isNullOrEmpty()){
        return  "-"
    } else {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID"))
        val date = format.parse(strResponsDate)
        val mFormat = SimpleDateFormat("EEEE", Locale("in", "ID"))
        return mFormat.format(date)
    }

}


/*
DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
String gregorianString = "01-08-1994";
LocalDate gregorianDate = LocalDate.parse(gregorianString, dateFormatter);
;
System.out.println("Islamic date: " + islamicDate);
 */


fun convertTime(strResponsDate: String): String {
    return when {
        strResponsDate.isEmpty() -> {
            "-"
        }
        else -> {
            val dateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale("in", "ID"))
            val time: Date = dateFormat.parse(strResponsDate)
            val mFormat = SimpleDateFormat("HH:mm z", Locale("in", "ID"))
            mFormat.timeZone.getDisplayName(false, TimeZone.SHORT, Locale("in", "ID"))
            mFormat.format(time)
        }
    }
}

fun convertTimeNoTimeZone(strResponsDate: String): Date {
    val dateFormat: DateFormat = SimpleDateFormat("HH:mm aa", Locale("in", "ID"))
    val time: Date = dateFormat.parse(strResponsDate)
    val mFormat = SimpleDateFormat("HH:mm:ss", Locale("in", "ID"))
    return mFormat.parse(mFormat.format(time))
}

fun mTimeDifference(startTime: String, stopTime: String): String {

    Handler().postDelayed(
        {
            val dateFormatStart = SimpleDateFormat("HH:mm aa", Locale("in", "ID"))
            val timeStart: Date = dateFormatStart.parse(startTime)
            val dateFormatStop = SimpleDateFormat("HH:mm z", Locale("in", "ID"))
            val timeStop: Date = dateFormatStop.parse(stopTime)
            val diff: Long = timeStop.time - timeStart.time
            val diffSeconds = System.currentTimeMillis() / 1000 % 60
            val diffMinutes = diff / (60 * 1000) % 60
            val diffHours = diff / (60 * 60 * 1000) % 24
            timeCountTime = String.format("%s Jam %s Menit %s Detik lagi ", diffHours, diffMinutes, diffSeconds)

        },
        100
    )

    return timeCountTime


    }



fun convertDateShortFromString (strResponsDate: String?): String {

    if (strResponsDate.isNullOrEmpty()){
        return  "-"
    } else {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale("in", "ID"))
        val date = format.parse(strResponsDate)
        val mFormat = SimpleDateFormat("dd MM yyyy", Locale("in", "ID"))
        return mFormat.format(date)
    }


}


/**
 * Returns a formatted string containing the amount of time (days, hours,
 * minutes, seconds) between the current time and the specified future date.
 *
 * @param context
 * @param futureDate
 * @return
 */
fun getCountdownText(context: Context, futureDate: Date): CharSequence? {
    println("MAIN UTILSD $futureDate")
    val countdownText = StringBuilder()
    // Calculate the time between now and the future date.
    var timeRemaining = futureDate.time - Date().time
    // If there is no time between (ie. the date is now or in the past), do nothing
    if (timeRemaining > 0) {
        val resources: Resources = context.resources
        // Calculate the days/hours/minutes/seconds within the time difference.
        //
        // It's important to subtract the value from the total time remaining after each is calculated.
        // For example, if we didn't do this and the time was 25 hours from now,
        // we would get `1 day, 25 hours`.
        val hours = TimeUnit.MILLISECONDS.toHours(timeRemaining) as Int
        timeRemaining -= TimeUnit.HOURS.toMillis(hours.toLong())
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemaining) as Int
        timeRemaining -= TimeUnit.MINUTES.toMillis(minutes.toLong())
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemaining)as Int
        // For each time unit, add the quantity string to the output, with a space.
        if ( hours > 0) {
            countdownText.append(resources.getQuantityString(R.plurals.hours, hours, hours))
            countdownText.append(" ")
        }
        if (hours > 0 || minutes > 0) {
            countdownText.append(resources.getQuantityString(R.plurals.minutes, minutes, minutes))
            countdownText.append(" ")
        }
        if (hours > 0 || minutes > 0 || seconds > 0) {
            countdownText.append(resources.getQuantityString(R.plurals.seconds, seconds, seconds))
            countdownText.append(" ")
        }
    }
    return countdownText.toString()
}

