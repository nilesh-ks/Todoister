package com.bawp.todoister.adapter

import com.bawp.todoister.model.Task
import java.text.FieldPosition

interface OnTodoClickListener {
    fun onTodoClick(task: Task)
    fun onTodoRadioButtonClick(task: Task)
}