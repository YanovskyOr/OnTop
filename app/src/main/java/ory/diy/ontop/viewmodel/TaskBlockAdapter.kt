package ory.diy.ontop.viewmodel

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task_block.view.*
import ory.diy.ontop.R
import ory.diy.ontop.model.TaskBlock

class TaskBlockAdapter(val context: Context, val items: ArrayList<TaskBlock>)
    : RecyclerView.Adapter<TaskBlockAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvTaskCardItem = view.cv_task_card
        val tvTaskCardName = view.tv_task_card_name
        val ibTaskCardStatus = view.ib_task_card_status
        val tvTaskCardCount = view.tv_task_card_count
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.item_task_block, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: TaskBlock = items[position]

        holder.tvTaskCardName.text = model.taskName
        holder.cvTaskCardItem.setCardBackgroundColor(ContextCompat.getColor(context, model.color))


        if(model.countDone > 0){
            holder.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button_checked)
        }else if(model.countDone == 0){
            holder.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button)
        }


        if(model.count > 1){
            holder.tvTaskCardCount.text = "" + model.countDone + "/" + model.count
        }else{
            holder.tvTaskCardCount.text = ""
        }



        holder.ibTaskCardStatus.setOnClickListener {
            model.increaseCountDone()
            if(model.countDone > 0){
                holder.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button_checked)
            }else if(model.countDone == 0){
                holder.ibTaskCardStatus.setBackgroundResource(R.drawable.radio_button)
            }

            if(model.count > 1){
                holder.tvTaskCardCount.text = "" + model.countDone + "/" + model.count
            }else{
                holder.tvTaskCardCount.text = ""
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }



}