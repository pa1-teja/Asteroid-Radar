package com.example.asteroidradar

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.provider.SyncStateContract
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import android.text.format.DateUtils
import timber.log.Timber


 class Utils {

    fun getNextSevenDaysFormatted(context: Context):ArrayList<String>{
        val formattedDateList = ArrayList<String>()

        val calendar = Calendar.getInstance()

        for (i in 0..context.getString(R.string.DEFAULT_END_DATE_DAYS).toInt()){
            val currentTime = calendar.time
            val dateFormat = SimpleDateFormat(context.getString(R.string.API_QUERY_DATE_FORMAT),Locale.getDefault())
            formattedDateList.add(dateFormat.format(currentTime))
            calendar.add(Calendar.DAY_OF_YEAR,1) // change date by one day ahead.
        }

        return formattedDateList
    }

      fun getTodayDate(context: Context): String{
         val calendar = Calendar.getInstance()
         val currentTime = calendar.time
         val dateFormat = SimpleDateFormat(context.getString(R.string.API_QUERY_DATE_FORMAT), Locale.getDefault())
         return dateFormat.format(currentTime)
     }

     fun getYesterdayDate(context: Context): String{
         val calendar = Calendar.getInstance()
         calendar.add(Calendar.DAY_OF_YEAR,-1)
         val dateFormat = SimpleDateFormat(context.getString(R.string.API_QUERY_DATE_FORMAT), Locale.getDefault())
         return dateFormat.format(calendar.time)

     }

      fun showAlertMessage(message:String, context:Context){
         AlertDialog.Builder(context).setMessage(message).setPositiveButton(R.string.okay_message,
             DialogInterface.OnClickListener { dialogInterface, i ->
                 dialogInterface.dismiss() }).create().show()
     }
}