package ory.diy.ontop.viewmodel


import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import kotlinx.android.synthetic.main.item_note_plane.view.*
import kotlinx.android.synthetic.main.item_task_blocks_plane.view.*
import kotlinx.android.synthetic.main.item_to_do_list_plane.view.*
import ory.diy.ontop.R
import ory.diy.ontop.model.*


class PlanesAdapter(private val context: Context, private val rows: List<IPlane>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ITEM TYPES DEFINITIONS
    companion object {
        private const val TYPE_TASK_BLOCKS_PLANE = 0
        private const val TYPE_TO_DO_LIST_PLANE = 1
        private const val TYPE_NOTE_PLANE = 2
    }

    //TASK BLOCKS PLANE VIEW HOLDER
    class TaskBlockPlaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTaskBlocksPlaneName = itemView.tv_task_blocks_plane_name
        val rvTaskBlocksPlaneBlocks = itemView.rv_task_blocks
        val ibTitleHandle = itemView.ib_title_handle_task_blocks
        val fabTaskBlocksPlane = itemView.fab_task_blocks_plane
    }

    //TO DO LIST PLANE VIEW HOLDER
    class ToDoListPlaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvToDoListPlaneName = itemView.tv_to_do_list_plane_name
        val rvToDoListPlaneTasks = itemView.rv_task_list
        val ibTitleHandle = itemView.ib_title_handle_to_do_list
    }

    //NOTE PLANE VIEW HOLDER
    class NotePlaneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNotePlaneTitle = itemView.tv_note_plane_title
        val tvNotePlaneText = itemView.tv_note_plane_text
        val clNotePlane = itemView.cl_note_plane
        val ibTitleHandle = itemView.ib_title_handle_note
    }

    //INFLATE LAYOUTS ON VIEW HOLDER CREATION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_TASK_BLOCKS_PLANE -> TaskBlockPlaneViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_task_blocks_plane, parent, false))
        TYPE_TO_DO_LIST_PLANE -> ToDoListPlaneViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_to_do_list_plane, parent, false))
        TYPE_NOTE_PLANE -> NotePlaneViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note_plane, parent, false))
        else -> throw IllegalArgumentException()
    }

    //GET ITEM TYPE
    override fun getItemViewType(position: Int): Int =
            when (rows[position]) {
                is TaskBlocksPlane -> TYPE_TASK_BLOCKS_PLANE
                is ToDoListPlane -> TYPE_TO_DO_LIST_PLANE
                is NotePlane -> TYPE_NOTE_PLANE
                else -> throw IllegalArgumentException()
            }


    //BIND VIEW HOLDER TO EACH ITEM TYPE
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            when (holder.itemViewType) {
                TYPE_TASK_BLOCKS_PLANE -> onBindTaskBlocksPlane(holder, rows[position] as TaskBlocksPlane)
                TYPE_TO_DO_LIST_PLANE -> onBindToDoListPlane(holder, rows[position] as ToDoListPlane)
                TYPE_NOTE_PLANE -> onBindNotePlane(holder, rows[position] as NotePlane)
                else -> throw IllegalArgumentException()
            }


    //BIND TASK BLOCK PLANE VIEWS TO MODEL
    private fun onBindTaskBlocksPlane(holder: RecyclerView.ViewHolder, row: TaskBlocksPlane) {
        val taskBlocksPlane = holder as TaskBlockPlaneViewHolder
        taskBlocksPlane.tvTaskBlocksPlaneName.text = row.title
        //val taskBlockItemAdapter = TaskBlockAdapter(context, row.taskBlockArrayList)
        val taskBlockItemAdapter = BlockAdapter(context, row.taskBlockArrayList)
        taskBlocksPlane.rvTaskBlocksPlaneBlocks.adapter = taskBlockItemAdapter

        //uses external library to snap task blocks to the start of the recyclerview list
        //com.github.rubensousa.gravitysnaphelper
        val snapHelper = GravitySnapHelper(Gravity.START)
        snapHelper.snapToPadding = true
        snapHelper.attachToRecyclerView(taskBlocksPlane.rvTaskBlocksPlaneBlocks)

        taskBlocksPlane.fabTaskBlocksPlane.setOnClickListener(){
            Toast.makeText(context.applicationContext, "test", Toast.LENGTH_LONG).show()
        }

//        taskBlocksPlane.rvTaskBlocksPlaneBlocks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, scrollX: Int, scrollY: Int) {
//
//                taskBlocksPlane.fabTaskBlocksPlane.hide()
//                if (taskBlocksPlane.rvTaskBlocksPlaneBlocks.childCount < 3)
//                    taskBlocksPlane.fabTaskBlocksPlane.show()
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//
//            }
//        })
        taskBlocksPlane.fabTaskBlocksPlane.hide()
        taskBlocksPlane.rvTaskBlocksPlaneBlocks.viewTreeObserver.addOnScrollChangedListener {

            //to test the scrollbar control
//            Toast.makeText(context.applicationContext,
//                    "range: " +
//                    (taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollRange()).toString() +
//                    " offset: " +
//                            (taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollOffset()).toString() +
//                            " extent: " +
//                            (taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollExtent()).toString()
//                    , Toast.LENGTH_SHORT).show()


            when {
                taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollRange() - taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollOffset()
                        <= (taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollExtent()) -> {
                    taskBlocksPlane.fabTaskBlocksPlane.show()
                }
                (taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollRange() - taskBlocksPlane.rvTaskBlocksPlaneBlocks.measuredWidth
                        <= taskBlocksPlane.rvTaskBlocksPlaneBlocks.measuredWidth - taskBlocksPlane.rvTaskBlocksPlaneBlocks.computeHorizontalScrollExtent() ) -> {
                    taskBlocksPlane.fabTaskBlocksPlane.show()
                }
                else -> {
                    taskBlocksPlane.fabTaskBlocksPlane.hide()
                }
            }
        }
    }

    //BIND TO DO LIST PLANE VIEWS TO MODEL
    private fun onBindToDoListPlane(holder: RecyclerView.ViewHolder, row: ToDoListPlane) {
        val toDoListPlane = holder as ToDoListPlaneViewHolder
        toDoListPlane.tvToDoListPlaneName.text = row.title
        val toDoListTaskAdapter = ToDoListAdapter(context, row.toDoListArrayList)
        toDoListPlane.rvToDoListPlaneTasks.adapter = toDoListTaskAdapter
    }

    //BIND NOTE PLANE VIEWS TO MODEL
    private fun onBindNotePlane(holder: RecyclerView.ViewHolder, row: NotePlane) {
        val notePlane = holder as NotePlaneViewHolder
        notePlane.tvNotePlaneTitle.text = row.title
        notePlane.tvNotePlaneText.text = row.text
        notePlane.clNotePlane.setBackgroundColor(ContextCompat.getColor(context, row.color))
    }

    //GET ITEM COUNT
    override fun getItemCount() = rows.count()



}