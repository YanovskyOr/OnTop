package ory.diy.ontop.viewmodel

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.add_task_block.view.*
import kotlinx.android.synthetic.main.item_task_block.view.*
import kotlinx.android.synthetic.main.item_task_block.view.cv_task_card
import ory.diy.ontop.MainActivity
import ory.diy.ontop.R
import ory.diy.ontop.model.AddBlock
import ory.diy.ontop.model.Block
import ory.diy.ontop.model.TaskBlock

class BlockAdapter(private val context: Context, private val blocks: ArrayList<Block>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //ITEM TYPES DEFINITIONS
    companion object {
        private const val TYPE_ADD_BLOCK = 0
        private const val TYPE_TASK_BLOCK = 1
        private const val TYPE_COUNTER_TASK_BLOCK = 2
        //private const val TYPE_ = 3
    }

    //ADD BLOCK VIEW HOLDER
    class AddBlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ibAddBlock = itemView.ib_add_block
    }


    //TASK BLOCK VIEW HOLDER
    class TaskBlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvTaskCardItem = itemView.cv_task_card
        val tvTaskCardName = itemView.tv_task_card_name
        val ibTaskCardStatus = itemView.ib_task_card_status
        val tvTaskCardCount = itemView.tv_task_card_count
    }

    //COUNTER TASK BLOCK VIEW HOLDER
    class CounterTaskBlockViewHolder(){

    }


    //INFLATE LAYOUTS ON VIEW HOLDER CREATION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_ADD_BLOCK -> AddBlockViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.add_task_block, parent, false))
        TYPE_TASK_BLOCK -> TaskBlockViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task_block, parent, false))
//        TYPE_COUNTER_TASK_BLOCK -> CounterTaskBlockViewHolder(LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_task_block, parent, false))
        else -> throw IllegalArgumentException()
    }

    //GET ITEM TYPE
    override fun getItemViewType(position: Int): Int =
            when (blocks[position]) {
                is AddBlock -> TYPE_ADD_BLOCK
                is TaskBlock -> TYPE_TASK_BLOCK
                //is CounterTaskBlock -> TYPE_COUNTER_TASK_BLOCK
                else -> throw IllegalArgumentException()
            }

    //BIND VIEW HOLDER TO EACH ITEM TYPE
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            when (holder.itemViewType) {
                TYPE_ADD_BLOCK -> onBindAddBlock(holder, blocks[position] as AddBlock)
                TYPE_TASK_BLOCK -> onBindTaskBlock(holder, blocks[position] as TaskBlock)
//                TYPE_COUNTER_TASK_BLOCK -> onBindCounterTaskBlock(holder, blocks[position] as CounterTaskBlock)
                else -> throw IllegalArgumentException()
    }

    //BIND ADD BLOCK VIEWS TO MODEL
    private fun onBindAddBlock(holder: RecyclerView.ViewHolder, block: AddBlock){
        val addBlock = holder as AddBlockViewHolder
        addBlock.ibAddBlock.setOnClickListener {
            Toast.makeText(context.applicationContext, "add task", Toast.LENGTH_SHORT).show()
        }
    }

    //BIND TASK BLOCK VIEWS TO MODEL
    private fun onBindTaskBlock(holder: RecyclerView.ViewHolder, block: TaskBlock){
        val taskBlock = holder as TaskBlockViewHolder

        taskBlock.tvTaskCardName.text = block.taskName
        taskBlock.cvTaskCardItem.setCardBackgroundColor(ContextCompat.getColor(context, block.color))


        if(block.countDone > 0){
            taskBlock.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button_checked)
        }else if(block.countDone == 0){
            taskBlock.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button)
        }


        if(block.count > 1){
            taskBlock.tvTaskCardCount.text = "" + block.countDone + "/" + block.count
        }else{
            taskBlock.tvTaskCardCount.text = ""
        }



        taskBlock.ibTaskCardStatus.setOnClickListener {
            block.increaseCountDone()
            if(block.countDone > 0){
                taskBlock.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button_checked)
            }else if(block.countDone == 0){
                taskBlock.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button)
            }

            if(block.count > 1){
                taskBlock.tvTaskCardCount.text = "" + block.countDone + "/" + block.count
            }else{
                taskBlock.tvTaskCardCount.text = ""
            }
        }
    }

    override fun getItemCount(): Int {
        return blocks.size
    }



}