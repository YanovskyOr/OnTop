package ory.diy.ontop.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task_blocks_plane.view.*
import kotlinx.android.synthetic.main.item_to_do_list_plane.view.*
import ory.diy.ontop.R
import ory.diy.ontop.model.*

class PlanesAdapter(val context: Context, private val rows: List<IPlane>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
    private const val TYPE_TASK_BLOCKS_PLANE = 0
    private const val TYPE_TO_DO_LIST_PLANE = 1
    }


    class TaskBlockPlaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskBlocksPlaneName = itemView.tv_task_blocks_plane_name
        val rvTaskBlocksPlaneBlocks = itemView.rv_task_blocks
    }

    class ToDoListPlaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvToDoListPlaneName = itemView.tv_to_do_list_plane_name
        val rvToDoListPlaneTasks = itemView.rv_task_list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_TASK_BLOCKS_PLANE -> TaskBlockPlaneViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task_blocks_plane, parent, false))
        TYPE_TO_DO_LIST_PLANE -> ToDoListPlaneViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_to_do_list_plane, parent, false))
        else -> throw IllegalArgumentException()
    }

    override fun getItemViewType(position: Int): Int =
            when (rows[position]) {
                is TaskBlocksPlane -> TYPE_TASK_BLOCKS_PLANE
                is ToDoListPlane -> TYPE_TO_DO_LIST_PLANE
                else -> throw IllegalArgumentException()
            }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            when (holder.itemViewType) {
                TYPE_TASK_BLOCKS_PLANE -> onBindTaskBlocksPlane(holder, rows[position] as TaskBlocksPlane)
                TYPE_TO_DO_LIST_PLANE -> onBindToDoListPlane(holder, rows[position] as ToDoListPlane)
                else -> throw IllegalArgumentException()
            }


    private fun onBindTaskBlocksPlane(holder: RecyclerView.ViewHolder, row: TaskBlocksPlane) {
        val taskBlocksPlane = holder as TaskBlockPlaneViewHolder
        taskBlocksPlane.tvTaskBlocksPlaneName.text = row.title
        val taskBlockItemAdapter = TaskBlockAdapter(context, row.taskBlockArrayList)
        taskBlocksPlane.rvTaskBlocksPlaneBlocks.adapter = taskBlockItemAdapter
    }

    private fun onBindToDoListPlane(holder: RecyclerView.ViewHolder, row: ToDoListPlane) {
        val toDoListPlane = holder as ToDoListPlaneViewHolder
        toDoListPlane.tvToDoListPlaneName.text = row.title
        val toDoListTaskAdapter = ToDoListAdapter(context, row.toDoListArrayList)
        toDoListPlane.rvToDoListPlaneTasks.adapter = toDoListTaskAdapter
    }

    override fun getItemCount() = rows.count()

    fun getAdapterList() : List<IPlane>{
        return rows
    }
}