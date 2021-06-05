package com.mta.hwapplication.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtil {

    fun serverToMobileFormat(dateString: String): String? {
        if (!dateString.isEmpty()) {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val output = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            var d: Date? = null
            try {
                d = input.parse(dateString)
                val formatted = output.format(d)
                return formatted
            } catch (e: ParseException) {
                e.printStackTrace()
                return ""
            }
//        Log.i("DATE", "" + formatted)
        } else return ""
    }

    fun dateToMilisec(stringDate: String?): Long{
        val sdf = SimpleDateFormat("dd MMM yyyy")
        try {
            //formatting the dateString to convert it into a Date
            val date = sdf.parse(stringDate)
            val calendar = Calendar.getInstance()
            //Setting the Calendar date and time to the given date and time
            calendar.time = date
            return calendar.timeInMillis
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0L
    }

    fun getDateInDays(date: String): Long{
        val dateFormat = serverToMobileFormat(date)
        val dateInMili = dateToMilisec(dateFormat)
        val msDiff: Long = Calendar.getInstance().timeInMillis - dateInMili
        return TimeUnit.MILLISECONDS.toDays(msDiff)
    }
}