package ory.diy.ontop.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task_block.view.*
import ory.diy.ontop.R
import ory.diy.ontop.model.TaskBlock

class TaskBlockAdapter(val context: Context, val items: ArrayList<TaskBlock>)
    : RecyclerView.Adapter<TaskBlockAdapter.ViewHolder>(){


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvTaskCardItem = view.cv_task_card
        val tvTaskCardDescription = view.tv_task_card_description
        val rbTaskCardStatus = view.rb_task_card_status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
            .inflate(R.layout.item_task_block, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: TaskBlock = items[position]

        holder.tvTaskCardDescription.text = model.taskCardDescription
        holder.rbTaskCardStatus.isChecked = model.taskCardStatus
    }

    override fun getItemCount(): Int {
        return items.size
    }

}