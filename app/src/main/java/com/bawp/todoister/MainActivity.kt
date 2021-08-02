package com.bawp.todoister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bawp.todoister.R
import com.bawp.todoister.adapter.OnTodoClickListener
import com.bawp.todoister.adapter.RecyclerViewAdapter
import com.bawp.todoister.model.Priority
import com.bawp.todoister.model.SharedViewModel
import com.bawp.todoister.model.Task
import com.bawp.todoister.model.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), OnTodoClickListener {
    companion object{
        private val TAG: String = "ITEM"
    }
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private var counter: Int=0
    lateinit var bottomSheetFragment: BottomSheetFragment
    lateinit var constraintLayout: ConstraintLayout
    lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomSheetFragment= BottomSheetFragment()
        constraintLayout=findViewById(R.id.bottomSheet)
        bottomSheetBehavior=BottomSheetBehavior.from(constraintLayout)
        bottomSheetBehavior.isHideable=true
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN)
        recyclerView=findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(this)

        taskViewModel=ViewModelProvider.AndroidViewModelFactory(
            this@MainActivity.application)
            .create(TaskViewModel::class.java)
        //The declaration provides all the
        // TaskViewModel methods to the variable here

        sharedViewModel = ViewModelProvider(this)
            .get(SharedViewModel::class.java)

        taskViewModel.getAllTasks().observe(this,{tasks->
//            run {
//                for (task: Task in tasks) {
//                    Log.d(TAG, "onCreate: " + task.taskId)
//                }
//            }

           recyclerViewAdapter=RecyclerViewAdapter(tasks, this)
            recyclerView.adapter=recyclerViewAdapter

        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
//            val task: Task = Task("Task "+counter++,Priority.HIGH,Calendar.getInstance().time,
//                Calendar.getInstance().time, false)
//            TaskViewModel.insert(task)
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag) //tag is given to every fragment that we create
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onTodoClick( task: Task) {
        //Log.d("Click", "onTodoClick: " + adapterPosition+" "+task.task)
        sharedViewModel.selectItem(task)
        sharedViewModel.setIsEdit(true)
        showBottomSheetDialog()

    }

    override fun onTodoRadioButtonClick(task: Task) {
        Log.d("Click", "onRadioButton: " +task.task)
        TaskViewModel.delete(task)
        recyclerViewAdapter.notifyDataSetChanged()
    }
}