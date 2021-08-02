package com.bawp.todoister.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bawp.todoister.model.Task

@Dao
interface TaskDao {
@Insert
 fun insertTask(task: Task): Void

 @Query("DELETE FROM task_table")
 fun deleteAll()

 @Query("SELECT * FROM task_table")
 fun getTasks(): LiveData<List<Task>>

 @Query("SELECT * FROM task_table WHERE task_id==:id")
 fun get(id: Long): LiveData<Task>

 @Update
 fun update(task: Task): Void

 @Delete
 fun delete(task: Task): Void
}