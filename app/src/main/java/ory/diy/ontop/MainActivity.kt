package ory.diy.ontop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import ory.diy.ontop.model.*
import ory.diy.ontop.preferences.MyPreferences
import ory.diy.ontop.viewmodel.PlanesAdapter
import ory.diy.ontop.viewmodel.TaskBlockAdapter
import ory.diy.ontop.viewmodel.ToDoListAdapter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var planesList: ArrayList<IPlane>? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set theme according to shared preferences
        checkTheme()

        //dark mode button
        ib_dark_mode.setOnClickListener {
            chooseThemeDialog()
        }
        setTopGreeting()

//        rv_task_blocks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val taskBlockItemAdapter = TaskBlockAdapter(this, getTaskBlockList())
//        rv_task_blocks.adapter = taskBlockItemAdapter
//
//        val toDoListItemAdapter = ToDoListAdapter(this, getToDoList())
//        rv_task_list.adapter = toDoListItemAdapter

        planesList = getPlanesList()
        val planesItemAdapter =  PlanesAdapter(this, planesList!!)
        rv_planes.adapter = planesItemAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(rv_planes)

    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }



    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
            2 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                delegate.applyDayNight()
            }
        }
    }

    private fun chooseThemeDialog() {

        val builder = AlertDialog.Builder(this, R.style.DialogAlert_Style_OnTop)
        builder.setTitle("Choose Theme")
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = MyPreferences(this).darkMode

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->

            when (which) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    MyPreferences(this).darkMode = 0
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    MyPreferences(this).darkMode = 1
                    delegate.applyDayNight()
                    dialog.dismiss()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    MyPreferences(this).darkMode = 2
                    delegate.applyDayNight()
                    dialog.dismiss()
                }

            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),0){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            var startPosition = viewHolder.adapterPosition
            var endPosition = target.adapterPosition

            Collections.swap(planesList, startPosition, endPosition)
            rv_planes.adapter?.notifyItemMoved(startPosition, endPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }

    }


    fun getPlanesList() : ArrayList<IPlane>{
        val planesArrayList = ArrayList<IPlane>()

        val taskBlocksPlane = TaskBlocksPlane("Daily", getTaskBlockList())
        val toDoListPlane = ToDoListPlane("To Do", getToDoList())
        val taskBlocksPlane2 = TaskBlocksPlane("Daily2", getTaskBlockList())
        val toDoListPlane2 = ToDoListPlane("To Do2", getToDoList())
        val toDoListPlane3 = ToDoListPlane("To Do3", getToDoList())

        planesArrayList.add(taskBlocksPlane)
        planesArrayList.add(toDoListPlane)
        planesArrayList.add(taskBlocksPlane2)
        planesArrayList.add(toDoListPlane2)
        planesArrayList.add(toDoListPlane3)



        return planesArrayList
    }


    private fun getTaskBlockList(): ArrayList<TaskBlock> {
       val taskBlockArrayList = ArrayList<TaskBlock>()

        var task1 = TaskBlock("Make bed", 1, TaskBlock.TASK_COLOR_GREEN)
        var task2 = TaskBlock("Reset Workspace", 1, TaskBlock.TASK_COLOR_YELLOW)
        var task3 = TaskBlock("Workout", 1, TaskBlock.TASK_COLOR_RED)
        var task4 = TaskBlock("Drink Water", 9)


        taskBlockArrayList.add(task1)
        taskBlockArrayList.add(task2)
        taskBlockArrayList.add(task3)
        taskBlockArrayList.add(task4)

        return taskBlockArrayList
    }


    private fun getToDoList() : ArrayList<ToDoListTask> {
        val toDoListArrayList = ArrayList<ToDoListTask>()

        var task1 = ToDoListTask("Work on app")
        var task2 = ToDoListTask("Organize Finances")
        var task3 = ToDoListTask("Clean book closet")
        var task4 = ToDoListTask("Finish Song")

        toDoListArrayList.add(task1)
        toDoListArrayList.add(task2)
        toDoListArrayList.add(task3)
        toDoListArrayList.add(task4)

        return toDoListArrayList
    }

    private fun setTopGreeting() {
        var calendar = Calendar.getInstance()
        var timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        when (timeOfDay) {
            in 0..2 -> tv_greeting.text = "Don't Stay up too late!"
            in 3..4 -> tv_greeting.text = "Just... why?"
            in 5..6 -> tv_greeting.text = "Why so early???"
            in 7..11 -> tv_greeting.text = "Good Morning :)"
            in 12..15 -> tv_greeting.text = "Good Afternoon"
            in 16..20 -> tv_greeting.text = "Good Evening"
            in 21..23 -> tv_greeting.text = "Good Night"
        }
    }

}