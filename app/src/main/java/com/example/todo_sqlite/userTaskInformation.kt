package com.example.todo_sqlite

import java.util.ArrayList

object userTaskInformation {
    private lateinit var dataList: ArrayList<userTaskModelClass>

    fun getShowData():ArrayList<userTaskModelClass>{
        dataList=ArrayList()
//        dataList.add(userTaskModelClass(0,"kaish","bihar"))
//        dataList.add(userTaskModelClass(0,"jahir","Patna",))
//        dataList.add(userTaskModelClass(0,"Anjan","Delhi",))

        return dataList
    }
}