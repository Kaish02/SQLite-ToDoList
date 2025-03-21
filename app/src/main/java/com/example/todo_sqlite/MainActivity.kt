package com.example.todo_sqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recylerView:RecyclerView
    private lateinit var rvAdapter:userTaskRvAdapterClass
    private var SQLiteHelperUserTask=SQLiteHelperUserTask(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        recylerView=findViewById(R.id.recyleViewAllTask)
        recylerView.layoutManager=LinearLayoutManager(this)
        rvAdapter=userTaskRvAdapterClass(this,SQLiteHelperUserTask.viewUserTask())
        recylerView.adapter=rvAdapter


























        var clicckButtonAddAcvityPage=findViewById<FloatingActionButton>(R.id.floatingActionButtonClick)
        clicckButtonAddAcvityPage.setOnClickListener {
            startActivity(Intent(this,add_New_User::class.java))
        }


    }
}