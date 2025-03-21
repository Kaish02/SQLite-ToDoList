package com.example.todo_sqlite

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class add_New_User : AppCompatActivity() {
    private var SQLiteHelperUserTask = SQLiteHelperUserTask(this)
    lateinit var imageViewSelected: CircleImageView
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables", "SuspiciousIndentation",
        "InlinedApi"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_user)


        imageViewSelected=findViewById(R.id.click_alert_profile_image_selected)
        var editTextUserTitle = findViewById<TextInputEditText>(R.id.editTextUserTitle)
        var editTextUserDescription = findViewById<TextInputEditText>(R.id.editTextUserDescription)
        var clickButtonAddTask = findViewById<AppCompatButton>(R.id.buttonClickAddUserTask)

        clickButtonAddTask.setOnClickListener {
            var title = editTextUserTitle.text.toString()
            var description = editTextUserDescription.text.toString()

            var img=(imageViewSelected.drawable as BitmapDrawable).bitmap



            if (img.byteCount==null){
                val chooseImageLayout=LayoutInflater.from(this).inflate(R.layout.please_chose_your_image_toast,null,false)
                val toast=Toast(this)
                toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,0,0)
                toast.duration=Toast.LENGTH_LONG
                toast.view=chooseImageLayout
                toast.show()

            }
         else if (title.isEmpty()){
               var layout=LayoutInflater.from(this).inflate(R.layout.please_fill_title_toast,null,false)
                val toastTitle=Toast(this)
                toastTitle.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,0,0)
                toastTitle.duration=Toast.LENGTH_LONG
                toastTitle.view=layout
                toastTitle.show()
            }
            else if(description.isEmpty()){
                val layout=LayoutInflater.from(this).inflate(R.layout.please_fill_description_toast,null,false)
                val toastDescription=Toast(this)
                toastDescription.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL,0,0)
                toastDescription.duration=Toast.LENGTH_LONG
                toastDescription.view=layout
                toastDescription.show()
            }
            else {

                var byteArray=getImageBytesFunction(img)

                var model = userTaskModelClass(0, title, description, byteArray)
                SQLiteHelperUserTask.insertNewTaskFun(model)




                val layout = LayoutInflater.from(this).inflate(R.layout.custom_toast_user_task_added, null, false)
                val tost = Toast(this)
                tost.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
                tost.duration = Toast.LENGTH_LONG
                tost.view = layout
                tost.show()

                startActivity(Intent(this, MainActivity::class.java))
            }
        }


        imageViewSelected.setOnClickListener {
            val alert =AlertDialog.Builder(this).create()
            val layoutAlert=LayoutInflater.from(this).inflate(R.layout.choose_image_source_alert,null,false)
                alert.window?.setBackgroundDrawable(getDrawable(R.drawable.radius_alert))



            val clickOpenGallery=layoutAlert.findViewById<TextView>(R.id.textViewGalleryImage)
            val clickOpenCamera=layoutAlert.findViewById<TextView>(R.id.textViewCameraImage)
            val clickOpenGalleryImageView=layoutAlert.findViewById<ImageView>(R.id.imageViewGalleryImageSeleted)
            val clickOpenCameraImageView=layoutAlert.findViewById<ImageView>(R.id.imageViewCameraImageSelected)
            clickOpenGalleryImageView.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(intent,111)
                alert.dismiss()
            }
            clickOpenCameraImageView.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,999)
                alert.dismiss()
            }
            clickOpenGallery.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(intent,111)
                alert.dismiss()
            }

            clickOpenCamera.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,999)
                alert.dismiss()
            }




            alert.setView(layoutAlert)
            alert.show()
        }






















        var clickArrowHomePage = findViewById<ImageView>(R.id.clickArrowBackImageView)
        clickArrowHomePage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
































    fun getImageBytesFunction(bitmap: Bitmap):ByteArray{
        var byteArrayOutputStream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 999 && resultCode == RESULT_OK){
            val bitMap=data?.extras?.get("data") as Bitmap
            imageViewSelected.setImageBitmap(bitMap)
        }
        else if(requestCode == 111 && resultCode == RESULT_OK){
            imageViewSelected.setImageURI(data?.data)
        }
    }
}