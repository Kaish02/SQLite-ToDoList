package com.example.todo_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelperUserTask(private val context: Context):SQLiteOpenHelper(context, DB_NAME,null, VERSION) {
    companion object{
        private const val DB_NAME="Task"
        private const val VERSION=2
        private const val TABLE_NAME="User"
        private const val COL_ID="Id"
        private const val COL_TITLE="Title"
        private const val COL_DESCRIPTION="Description"
        private const val COL_IMAGES="Image"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT ,$COL_TITLE TEXT,$COL_DESCRIPTION TEXT,$COL_IMAGES BLOB)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertNewTaskFun(userTaskModelClass: userTaskModelClass){
        val db=writableDatabase
        val value=ContentValues().apply {
            put(COL_TITLE,userTaskModelClass.Title)
            put(COL_DESCRIPTION,userTaskModelClass.Description)
            put(COL_IMAGES,userTaskModelClass.Image)
        }
        db.insert(TABLE_NAME,null,value)
    }

    fun viewUserTask():ArrayList<userTaskModelClass>{
        val arrayListUser= arrayListOf<userTaskModelClass>()
        val db=readableDatabase
        val selectQuery="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(selectQuery,null)

        while (cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE))
            val description=cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION))
            val image=cursor.getBlob(cursor.getColumnIndexOrThrow(COL_IMAGES))

            val model=userTaskModelClass(id,title,description, image)
            arrayListUser.add(model)
        }
        return arrayListUser
    }

    fun userTaskDeleted(id:Int){
        val db=writableDatabase
        val arrayTask= arrayOf(id.toString())
        val whereC="$COL_ID = ?"
        db.delete(TABLE_NAME,whereC,arrayTask)
    }

    fun userTaskUpdate(userTaskModelClass: userTaskModelClass){
        val db=writableDatabase
        val array= arrayOf(userTaskModelClass.Id.toString())
        val whereC="$COL_ID = ?"
        val valueUpdate=ContentValues().apply {
            put(COL_ID,userTaskModelClass.Id)
            put(COL_TITLE,userTaskModelClass.Title)
            put(COL_DESCRIPTION,userTaskModelClass.Description)
            put(COL_IMAGES,userTaskModelClass.Image)
        }
        db.update(TABLE_NAME,valueUpdate,whereC,array)
    }
}