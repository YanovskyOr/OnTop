package ory.diy.ontop.model

class ToDoListTask (var taskName: String, var status: Boolean = false){

    fun changeStatus(){
        if(status){
            status = false
        }else if(!status){
            status = true
        }
    }
}