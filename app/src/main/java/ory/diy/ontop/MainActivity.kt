package ory.diy.ontop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_note_plane.ib_title_handle_note
import kotlinx.android.synthetic.main.item_note_plane.view.*
import kotlinx.android.synthetic.main.item_task_blocks_plane.*
import kotlinx.android.synthetic.main.item_task_blocks_plane.view.*
import kotlinx.android.synthetic.main.item_to_do_list_plane.*
import kotlinx.android.synthetic.main.item_to_do_list_plane.view.*
import ory.diy.ontop.model.*
import ory.diy.ontop.preferences.MyPreferences
import ory.diy.ontop.viewmodel.PlanesAdapter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var planesList: ArrayList<IPlane>? = null
    private var isEditMode = false


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

        //EDIT MODE
        ib_edit_mode.setOnClickListener {
            editMode()
        }
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

        val builder = MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_OnTop_MaterialAlertDialog)
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

    private var simpleCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),ItemTouchHelper.LEFT){

        //top/bottom
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            var startPosition = viewHolder.adapterPosition
            var endPosition = target.adapterPosition

            Collections.swap(planesList!!, startPosition, endPosition)
            rv_planes.adapter?.notifyItemMoved(startPosition, endPosition)
            return true
        }

        //when swiping item left
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }


    private val itemTouchHelper = ItemTouchHelper(simpleCallback)


    fun getPlanesList() : ArrayList<IPlane>{
        val planesArrayList = ArrayList<IPlane>()

        val taskBlocksPlane = TaskBlocksPlane("Daily", getBlockList())
        val notePlane = NotePlane("Extremely Important!", "Send report today!", NotePlane.NOTE_COLOR_ORANGE)
        val toDoListPlane = ToDoListPlane("Focus Today", getToDoList())
        val toDoListPlane2 = ToDoListPlane("Do Tomorrow", getToDoList2())


        planesArrayList.add(taskBlocksPlane)
        planesArrayList.add(notePlane)
        planesArrayList.add(toDoListPlane)
        planesArrayList.add(toDoListPlane2)


        return planesArrayList
    }


    private fun getTaskBlockList(): ArrayList<TaskBlock> {
       val taskBlockArrayList = ArrayList<TaskBlock>()

        var task1 = TaskBlock("Make bed", 1, Block.BLOCK_COLOR_GREEN)
        var task2 = TaskBlock("Reset Workspace", 1, Block.BLOCK_COLOR_YELLOW)
        var task3 = TaskBlock("Drink Water", 9)
        var task4 = TaskBlock("Workout", 1, Block.BLOCK_COLOR_RED)
        var task5 = TaskBlock("Do thing 5", 1, Block.BLOCK_COLOR_CYAN)
        var task6 = TaskBlock("Do thing 6", 1, Block.BLOCK_COLOR_PURPLE)
        var task7 = TaskBlock("Do multiple things", 7, Block.BLOCK_COLOR_PINK)
        var task8 = TaskBlock("Do thing 8", 1, Block.BLOCK_COLOR_ORANGE)


        taskBlockArrayList.add(task1)
        taskBlockArrayList.add(task2)
        taskBlockArrayList.add(task3)
        taskBlockArrayList.add(task4)
        taskBlockArrayList.add(task5)
        taskBlockArrayList.add(task6)
        taskBlockArrayList.add(task7)
        taskBlockArrayList.add(task8)


        return taskBlockArrayList
    }

    private fun getBlockList(): ArrayList<Block> {
        val blockArrayList = ArrayList<Block>()

        var task1 = TaskBlock("Make bed", 1, Block.BLOCK_COLOR_GREEN)
        var task2 = TaskBlock("Reset Workspace", 1, Block.BLOCK_COLOR_YELLOW)
        var task3 = TaskBlock("Drink Water", 9)
        var task4 = TaskBlock("Workout", 1, Block.BLOCK_COLOR_RED)
        var task5 = TaskBlock("Do thing 5", 1, Block.BLOCK_COLOR_CYAN)
        var task6 = TaskBlock("Do thing 6", 1, Block.BLOCK_COLOR_PURPLE)
        var task7 = TaskBlock("Do multiple things", 7, Block.BLOCK_COLOR_PINK)
//        var task8 = AddBlock()

        blockArrayList.add(task1)
        blockArrayList.add(task2)
        blockArrayList.add(task3)
        blockArrayList.add(task4)
        blockArrayList.add(task5)
        blockArrayList.add(task6)
        blockArrayList.add(task7)
//        blockArrayList.add(task8)

        return blockArrayList
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

    private fun getToDoList2() : ArrayList<ToDoListTask> {
        val toDoListArrayList = ArrayList<ToDoListTask>()

        var task1 = ToDoListTask("Work on app")
        var task2 = ToDoListTask("Send email")

        toDoListArrayList.add(task1)
        toDoListArrayList.add(task2)

        return toDoListArrayList
    }


    private fun setTopGreeting() {
        var calendar = Calendar.getInstance()
        var timeOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        when (timeOfDay) {
            in 0..2 -> tv_greeting.text = "Don't stay up too late!"
            in 3..4 -> tv_greeting.text = "Just... why?"
            in 5..6 -> tv_greeting.text = "Why so early???"
            in 7..11 -> tv_greeting.text = "Good morning :)"
            in 12..15 -> tv_greeting.text = "Good afternoon"
            in 16..20 -> tv_greeting.text = "Good evening"
            in 21..23 -> tv_greeting.text = "Good night"
        }
    }

    private fun editMode(){
        //edit mode enabled -> disabled
        if(isEditMode){
            isEditMode = false

            ib_edit_mode.setImageResource(R.drawable.ic_edit_24)
            try {
                var i = 0
                do{
                    var type = (rv_planes.adapter as PlanesAdapter).getItemViewType(i)
                    when(type){
                        0 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_task_blocks?.background = getDrawable(R.color.color_on_top_primary_blue)
                        1 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_to_do_list?.background = getDrawable(R.color.color_on_top_primary_blue)
                        2 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_note?.background = getDrawable(R.color.color_on_top_primary_blue)
                    }
                    i++
                }while(i < rv_planes.layoutManager!!.childCount)
            }catch (e : Exception){
                Log.e("ERROR", "Null plane type, couldn't generate drag handle icon")
            }
            itemTouchHelper.attachToRecyclerView(null)
        }

        //edit mode disabled -> enabled
        else{
            isEditMode = true

            ib_edit_mode.setImageResource(R.drawable.ic_check_24)
            try {
                var i = 0
                do {
                    var type = (rv_planes.adapter as PlanesAdapter).getItemViewType(i)
                    when (type) {
                        0 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_task_blocks?.background = getDrawable(R.drawable.ic_drag_handle_24)
                        1 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_to_do_list?.background = getDrawable(R.drawable.ic_drag_handle_24)
                        2 -> rv_planes.layoutManager!!.getChildAt(i)?.ib_title_handle_note?.background = getDrawable(R.drawable.ic_drag_handle_24)
                    }
                    i++
                } while (i < rv_planes.layoutManager!!.childCount)
            }catch (e : Exception){
                Log.e("ERROR", "Null plane type, couldn't generate drag handle icon")
            }
            itemTouchHelper.attachToRecyclerView(rv_planes)
        }
    }

}