package com.bawp.todoister

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.Group
import androidx.lifecycle.ViewModelProvider
import com.bawp.todoister.model.Priority
import com.bawp.todoister.model.SharedViewModel
import com.bawp.todoister.model.Task
import com.bawp.todoister.model.TaskViewModel
import com.bawp.todoister.util.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import java.util.*

class BottomSheetFragment : BottomSheetDialogFragment(), View.OnClickListener {
    private lateinit var enterTodo: EditText
    private lateinit var calendarButton: ImageButton
    private lateinit var priorityButton: ImageButton
    private lateinit var priorityRadioGroup: RadioGroup
    private lateinit var selectedRadioButton: RadioButton
    private var selectedButtonId: Int = 0
    private lateinit var saveButton: ImageButton
    private lateinit var calendarView: CalendarView
    private lateinit var calendarGroup: Group
    private var dueDate: Date? = Calendar.getInstance().time
    var calendar: Calendar= Calendar.getInstance()
    private lateinit var sharedViewModel: SharedViewModel
    private var isEdit: Boolean = false
    private var priority: Priority= Priority.LOW


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View=inflater.inflate(R.layout.bottom_sheet, container, false)
        calendarGroup=view.findViewById(R.id.calendar_group)
        calendarView=view.findViewById(R.id.calendar_view)
        calendarButton=view.findViewById(R.id.today_calendar_button)
        enterTodo=view.findViewById(R.id.enter_todo_et)
        saveButton=view.findViewById(R.id.save_todo_button)
        priorityButton=view.findViewById(R.id.priority_todo_button)
        priorityRadioGroup=view.findViewById(R.id.radioGroup_priority)
        //dueDate=view.findViewById(R.id.textView)

        var todayChip: Chip =view.findViewById(R.id.today_chip)
        todayChip.setOnClickListener(this)
        var tomorrowChip: Chip =view.findViewById(R.id.tomorrow_chip)
        tomorrowChip.setOnClickListener(this)
        var nextWeekChip: Chip =view.findViewById(R.id.next_week_chip)
        nextWeekChip.setOnClickListener(this)

        return view
    }

    override fun onResume() {
        super.onResume()

        if (sharedViewModel.getSelectedItem().value!=null)
        {
            isEdit = sharedViewModel.getIsEdit()

            var task: Task = sharedViewModel.getSelectedItem().value!!
            enterTodo.setText(task.task)
            Log.d("MY", "onViewCreated: " + task.task)
            //the text should always be changing according to the task selected
        }
    }
    override fun onViewCreated(@NonNull view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel= ViewModelProvider(requireActivity())
            .get(SharedViewModel::class.java)
        //instantiation of sharedViewModel shall be written inside onViewCreated
        // because this all happens once the view is created


        calendarButton.setOnClickListener {
            if(calendarGroup.visibility== GONE)
                calendarGroup.visibility=View.VISIBLE
            else calendarGroup.visibility= GONE
            Utils.hideSoftKeyboard(view)
        }

        calendarView.setOnDateChangeListener { calendarView, year, month, day ->
            calendar.clear()
            calendar.set(year, month, day)
            dueDate=calendar.time
            //Log.d("Cal","onViewCreated: ==> month"+ (month+1)+", dayOfMonth "+day)
        }

        priorityButton.setOnClickListener {
            Utils.hideSoftKeyboard(view)
            if (priorityRadioGroup.visibility == View.GONE) priorityRadioGroup.visibility =
                View.VISIBLE
            else priorityRadioGroup.visibility = View.GONE

            priorityRadioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
                if (priorityRadioGroup.visibility == View.VISIBLE) {
                    selectedButtonId = checkedId
                    selectedRadioButton = view.findViewById(selectedButtonId)
                    if (selectedRadioButton.id == R.id.radioButton_high)
                        priority = Priority.HIGH
                    else if (selectedRadioButton.id == R.id.radioButton_med)
                        priority = Priority.MEDIUM
                    else if (selectedRadioButton.id == R.id.radioButton_high)
                        priority = Priority.LOW
                    else
                        priority = Priority.LOW

                }else priority=Priority.LOW

            }
        }

        saveButton.setOnClickListener {
            val task: String=enterTodo.text.toString().trim()
            if(!TextUtils.isEmpty(task) && dueDate!=null && priority!=null)
            {
                val myTask = Task(task, priority,
                dueDate, Calendar.getInstance().time, false)

                if(isEdit){
                    val updateTask: Task = sharedViewModel.getSelectedItem().value!!
                    updateTask.task = task
                    updateTask.dateCreated = Calendar.getInstance().time
                    updateTask.priority = priority
                    updateTask.dueDate = dueDate
                    TaskViewModel.update(updateTask)
                    sharedViewModel.setIsEdit(false)

                }else{
                    TaskViewModel.insert(myTask)
                }

                enterTodo.setText("")
                if (this.isVisible) this.dismiss()//once the task is saved, the bottomSheetFragment will be dismissed
            }else{
                //Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG).show()
                Toast.makeText(context, R.string.empty_field, Toast.LENGTH_SHORT).show()
            }
            //bottomSheet.setState(BottomSheetBehavior.STATE_HIDDEN, 0, 0)

        }
    }

    override fun onClick(p0: View?) {
        val id: Int= p0!!.id
        if (id == R.id.today_chip)
        {
            //calendar.clear()
            val cal: Calendar= Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, 0)
            val date=cal.time
            dueDate=date
            //date=null
            Log.d("TIME", "onClick: "+ dueDate.toString())
        }else if (id==R.id.tomorrow_chip)
        {
            //calendar.clear()
            val cal: Calendar= Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, 1)
            val date=cal.time
            dueDate=date

            Log.d("TIME", "onClick: "+ dueDate.toString())
        }else if(id == R.id.next_week_chip)
        {
            //calendar.clear()
            val cal: Calendar= Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, 7)
            val date=cal.time
            dueDate=date
            Log.d("TIME", "onClick: "+ dueDate.toString())
            Toast.makeText(context,"Next Week", Toast.LENGTH_SHORT).show()
        }

    }
}