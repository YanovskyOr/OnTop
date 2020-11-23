package ory.diy.ontop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*
import ory.diy.ontop.model.TaskBlock
import ory.diy.ontop.preferences.MyPreferences
import ory.diy.ontop.viewmodel.TaskBlockAdapter

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set theme according to shared preferences
        checkTheme()

        //dark mode button
        ib_dark_mode.setOnClickListener {
            chooseThemeDialog()
        }



//        rv_task_blocks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val itemAdapter = TaskBlockAdapter(this, getTaskBlockList())
        rv_task_blocks.adapter = itemAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

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

        val builder = AlertDialog.Builder(this)
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



    private fun getTaskBlockList(): ArrayList<TaskBlock> {
       val taskBlockArrayList = ArrayList<TaskBlock>()

        var task1 = TaskBlock("Make bed", true)
        var task2 = TaskBlock("Reset Workspace", false)
        var task3 = TaskBlock("Workout", true)
        var task4 = TaskBlock("Drink Water", false)

        taskBlockArrayList.add(task1)
        taskBlockArrayList.add(task2)
        taskBlockArrayList.add(task3)
        taskBlockArrayList.add(task4)

        return taskBlockArrayList
    }


}