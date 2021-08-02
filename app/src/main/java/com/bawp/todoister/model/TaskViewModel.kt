package com.bawp.todoister.model


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bawp.todoister.data.DoisterRepository
import com.bawp.todoister.model.TaskViewModel

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val allTasks: LiveData<List<Task>>

    companion object {
        lateinit var repository: DoisterRepository
        fun insert(task: Task) {
            repository.insert(task)
        }

        fun update(task: Task) {
            repository.update(task)
        }

        fun delete(task: Task){
            repository.delete(task)
        }
    }

    init {
        repository = DoisterRepository(application)
        allTasks = repository.getAllTasks()
    }

    @JvmName("getAllTasks1")
    fun getAllTasks(): LiveData<List<Task>> {
        return allTasks
    }


    fun get(id: Long): LiveData<Task>
    {
        return repository.get(id)
    }


}