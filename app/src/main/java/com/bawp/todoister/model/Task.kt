package com.bawp.todoister.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "task_table")
class Task: Serializable {

    @ColumnInfo(name="task_id")
    @PrimaryKey(autoGenerate = true)
    var taskId: Long?=null //better to keep taskId as Long because it is auto-generated

    var task: String? = null //no need of assign columnInfo if the column name would be same as var

     var priority: Priority? = Priority.LOW   //Enum class

    @ColumnInfo(name = "due_date")
    var dueDate: Date? = null

    @ColumnInfo(name = "created_at")
    var dateCreated: Date? = null

    @ColumnInfo(name = "is_done")
    var isDone: Boolean? = null

    constructor(
        task: String?,
        priority: Priority?,
        dueDate: Date?,
        dateCreated: Date?,
        isDone: Boolean?
    ) {
        this.task = task
        this.priority = priority
        this.dueDate = dueDate
        this.dateCreated = dateCreated
        this.isDone = isDone
    }

    override fun toString(): String {
        return "Task(taskId=$taskId, task=$task, priority=$priority, dueDate=$dueDate, dateCreated=$dateCreated, isDone=$isDone)"
    }


}