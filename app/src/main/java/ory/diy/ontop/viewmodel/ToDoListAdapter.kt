package ory.diy.ontop.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_to_do_list_task.view.*
import ory.diy.ontop.R
import ory.diy.ontop.model.ToDoListTask

class ToDoListAdapter(val context: Context, val items: ArrayList<ToDoListTask>)
    : RecyclerView.Adapter<ToDoListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val clToDoListItem = view.cl_to_do_list_task_item
        val tvToDoListTaskName = view.tv_task_name
        val ibToDoListTaskStatus = view.ib_task_status
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.ViewHolder {
        return ToDoListAdapter.ViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_to_do_list_task, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ToDoListTask = items[position]

        holder.tvToDoListTaskName.text = model.taskName

        if (model.status) {
            holder.ibToDoListTaskStatus.setBackgroundResource(R.drawable.radio_button_checked_blue)
        } else {
            holder.ibToDoListTaskStatus.setBackgroundResource(R.drawable.radio_button_blue)
        }

        holder.ibToDoListTaskStatus.setOnClickListener {
            model.changeStatus()
            if (model.status) {
                holder.ibToDoListTaskStatus.setBackgroundResource(R.drawable.radio_button_checked_blue)
            } else {
                holder.ibToDoListTaskStatus.setBackgroundResource(R.drawable.radio_button_blue)
            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}


