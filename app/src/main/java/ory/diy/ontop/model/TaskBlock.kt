package ory.diy.ontop.model

import android.graphics.Color
import ory.diy.ontop.R

class TaskBlock(var taskName: String, var count: Int = 1, var color: Int = TASK_COLOR_BLUE){

    var countDone = 0

    fun increaseCountDone(){
        if(countDone == count){
            countDone = 0
        }else{
            countDone++
        }
    }

    companion object {
        const val TASK_COLOR_BLUE = R.color.color_on_top_blue
        const val TASK_COLOR_GREEN = R.color.color_on_top_green
        const val TASK_COLOR_YELLOW = R.color.color_on_top_yellow
        const val TASK_COLOR_ORANGE = R.color.color_on_top_orange
        const val TASK_COLOR_RED = R.color.color_on_top_red
    }


}