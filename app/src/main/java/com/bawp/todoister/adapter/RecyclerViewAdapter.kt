package com.bawp.todoister.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bawp.todoister.MainActivity
import com.bawp.todoister.R
import com.bawp.todoister.model.Task
import com.bawp.todoister.util.Utils
import com.google.android.material.chip.Chip

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {



    private val taskList: List<Task>
    private val todoClickListener: OnTodoClickListener
    //lateinit var utils: Utils


    constructor(taskList: List<Task>, todoClickListener: OnTodoClickListener) : super() {
        this.taskList = taskList
        this.todoClickListener=todoClickListener//this clickListener will
    // point to the Main class clickListener


    }


    inner class ViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var radioButton: AppCompatRadioButton
        var task: AppCompatTextView
        @NonNull var todayChip: Chip
        lateinit var onTodoClickListener: OnTodoClickListener

        init {
            radioButton=itemView.findViewById(R.id.todo_radio_button)
            task=itemView.findViewById(R.id.todo_row_todo)
            todayChip=itemView.findViewById(R.id.todo_row_chip)
            this.onTodoClickListener=todoClickListener
            itemView.setOnClickListener(this)
            radioButton.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            var currTask: Task= taskList.get(adapterPosition)
            val id:Int=p0!!.id
            if (id==R.id.todo_row_layout)
            {
                onTodoClickListener.onTodoClick(currTask)
                //With the help of currTask, we can easily get the
            // position as well as the current task object in one go
            }else if(id==R.id.todo_radio_button){
                onTodoClickListener.onTodoRadioButtonClick(currTask)
            }
        }
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        val task: Task = taskList.get(position)
        val formatted: String= Utils.formatDate(task.dueDate!!)

        var colorStateList: ColorStateList = ColorStateList(
            arrayOf(intArrayOf(-android.R.attr.state_enabled), intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(
                Color.LTGRAY,//disabled
                Utils.priorityColor(task)
            )
        )

        holder.task.text=task.task
        holder.todayChip.text=formatted
        holder.todayChip.setTextColor(Utils.priorityColor(task))
        holder.todayChip.chipIconTint = colorStateList
        holder.radioButton.buttonTintList= colorStateList
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}