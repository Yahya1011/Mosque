@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.mosque.utils

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.mosque.common.Constans
import com.example.mosque.common.Constans.PREF_KEY_SERVICE_NAME
import com.example.mosque.common.Constans.PREF_KEY_SERVICE_STATE
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


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


/**
 * getCurrentTime() it will return system time
 *
 * @return
 */
fun getCurrentTime(): String { //date output format
    val dateFormat: DateFormat = SimpleDateFormat("hh:mm aa", Locale("in", "ID"))
    val cal = Calendar.getInstance()
    return dateFormat.format(cal.time)
} // end of getCurrentTime()

