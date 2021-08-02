package com.bawp.todoister.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.bawp.todoister.model.Priority
import com.bawp.todoister.model.Task
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object{
        fun formatDate(date: Date): String{
            var simpleDateFormat: SimpleDateFormat=
                SimpleDateFormat.getDateInstance() as SimpleDateFormat
            simpleDateFormat.applyPattern("EEE, MMM d")
            return simpleDateFormat.format(date)

        }

        fun hideSoftKeyboard(view: View){
           var imm: InputMethodManager = view.context.getSystemService(
               Context.INPUT_METHOD_SERVICE
           ) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun priorityColor(task: Task):Int{
            var color: Int
            if(task.priority==Priority.HIGH)
            {
                color = Color.argb(200,201,21,23)
            }else if(task.priority==Priority.MEDIUM)
            {
                color = Color.argb(200,155,179,0)
            }else
            {
                color = Color.argb(200,51,181,129)
            }
            return color
        }
    }
}