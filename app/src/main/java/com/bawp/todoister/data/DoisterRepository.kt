package com.bawp.todoister.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.bawp.todoister.model.Task
import com.bawp.todoister.util.TaskRoomDatabase

class DoisterRepository(application: Application) {
    private val taskDao: TaskDao
    private val  allTasks: LiveData<List<Task>>

    init {
        val database: TaskRoomDatabase= TaskRoomDatabase.getDatabase(application)!!
        taskDao = database.taskDao()
        allTasks = taskDao.getTasks()
    }

    fun getAllTasks(): LiveData<List<Task>>{
        return allTasks
    }

    fun insert(task: Task){
        TaskRoomDatabase.databaseWriterExecutor.execute { taskDao.insertTask(task) }
    }

    fun get(id: Long): LiveData<Task>{
        return taskDao.get(id)
    }

    fun update(task: Task){
        TaskRoomDatabase.databaseWriterExecutor.execute { taskDao.update(task) }
    }
    fun delete(task: Task){
        TaskRoomDatabase.databaseWriterExecutor.execute { taskDao.delete(task) }
    }



}