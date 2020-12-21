package ory.diy.ontop.model

import android.graphics.Color
import ory.diy.ontop.R

class TaskBlock(var taskName: String, var count: Int = 1, var color: Int = Block.BLOCK_COLOR_BLUE) : Block("Task - $taskName", type="TYPE_TASK_BLOCK") {

    var countDone = 0

    fun increaseCountDone(){
        if(countDone == count){
            countDone = 0
        }else{
            countDone++
        }
    }

}