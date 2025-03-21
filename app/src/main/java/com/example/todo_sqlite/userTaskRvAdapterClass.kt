package com.example.todo_sqlite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

class userTaskRvAdapterClass(private val context: Context, private val arrayList: ArrayList<userTaskModelClass>):RecyclerView.Adapter<userTaskRvAdapterClass.ViewHolderClass>() {
private var SQLiteHelperDeleteTask=SQLiteHelperUserTask(context)
    class ViewHolderClass(itemView:View):RecyclerView.ViewHolder(itemView){
        var rvTitle=itemView.findViewById<TextView>(R.id.textViewShowTitle)
        var rvDescription=itemView.findViewById<TextView>(R.id.textViewShowDescription)
        var imageViewTaskUserInserted=itemView.findViewById<ImageView>(R.id.imageViewInsertUserTask)
        var rvOptionMenuClick=itemView.findViewById<ImageView>(R.id.optionMenuImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val rvLayout=LayoutInflater.from(context).inflate(R.layout.rv_list_user_task_layout,parent,false)
        return ViewHolderClass(rvLayout)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val taskView=arrayList[position]
        holder.rvTitle.text=taskView.Title
        holder.rvDescription.text=taskView.Description


        val bitmap=BitmapFactory.decodeByteArray(taskView.Image,0,taskView.Image.size)
        holder.imageViewTaskUserInserted.setImageBitmap(bitmap)


//        Glide.with(context).load(taskView.Image).into(holder.imageViewTaskUserInserted)



        //update activity me image ko set karne ke liye
        val b = (holder.imageViewTaskUserInserted.drawable as BitmapDrawable).bitmap
        val byteArray = getImageBytesFunction(b)



        holder.rvOptionMenuClick.setOnClickListener { view ->
            val popupMenu = PopupMenu(context, view)
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.task_option_menu, popupMenu.menu)


            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.taskUpdate -> {
                       val intent=Intent(context,Update_User_Task::class.java).apply {
                           putExtra("id",taskView.Id)
                           putExtra("Title",taskView.Title)
                           putExtra("Description",taskView.Description)
                           putExtra("Image",byteArray)
                       }
                        context.startActivity(intent)
                        true
                    }
                    R.id.taskDeleted ->{
                        var alert=AlertDialog.Builder(context).create()
                        var layout=LayoutInflater.from(context).inflate(R.layout.delete_task_user_alert_layout,null,false)

                        val clickTaskDelete=layout.findViewById<TextView>(R.id.textViewDeleteTask)
                        clickTaskDelete.setOnClickListener {
                            SQLiteHelperDeleteTask.userTaskDeleted(arrayList[position].Id)
                            notifyDataSetChanged()
                            arrayList.removeAt(position)
                            alert.dismiss()
                        }
                        val clickTaskCancel=layout.findViewById<TextView>(R.id.textViewCancelTask)
                        clickTaskCancel.setOnClickListener {
                            alert.dismiss()
                        }


                        alert.window?.setBackgroundDrawable(context.getDrawable(R.drawable.radius_alert))
                        alert.setView(layout)
                        alert.show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }



    }

    fun getImageBytesFunction(bitmap: Bitmap):ByteArray{
        val byteArrayOutputStream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

}