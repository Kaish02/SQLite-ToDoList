package com.example.todo_sqlite

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.EventLogTags.Description
import android.view.Gravity
import android.view.LayoutInflater
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
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class Update_User_Task : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private var taskId: Int = -1
    private var SQLiteHelperUpdateTask = SQLiteHelperUserTask(this)
    lateinit var imageViewSelected: CircleImageView
    @SuppressLint("MissingInflatedId", "UseCompatLoadingForDrawables", "InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_user_task)
        imageViewSelected = findViewById(R.id.profile_image_user_update_selected)



        val byteArray = intent.getByteArrayExtra("Image")
        if (byteArray != null) {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            imageViewSelected.setImageBitmap(bitmap)
        }





        var clickArrowHomePage = findViewById<ImageView>(R.id.clickArrowBackImageViewUpdate)
        clickArrowHomePage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        taskId = intent.getIntExtra("id", -1)
        if (taskId == -1) {
            finish()
            return
        }
        var editTextUserTitle = findViewById<TextInputEditText>(R.id.editTextUserTitleUpdated)
        var editTextUserDescription =
            findViewById<TextInputEditText>(R.id.editTextUserDescriptionUpdated)


        var TitleGetAndSetEditTextIntent = intent.getStringExtra("Title")
        var DescriptionGetAndSetEditTextIntent = intent.getStringExtra("Description")
        editTextUserTitle.setText(TitleGetAndSetEditTextIntent)
        editTextUserDescription.setText(DescriptionGetAndSetEditTextIntent)


        var clickButtonAddTask = findViewById<AppCompatButton>(R.id.buttonClickUpdateUserTask)
        clickButtonAddTask.setOnClickListener {
            var title = editTextUserTitle.text.toString()
            var description = editTextUserDescription.text.toString()

            val image=(imageViewSelected.drawable as BitmapDrawable).bitmap
            val byteArray=getByteArrayFun(image)

            SQLiteHelperUpdateTask.userTaskUpdate(userTaskModelClass(taskId, title, description,
                byteArray
            ))


            var alertToastLayout = LayoutInflater.from(this).inflate(R.layout.cusom_toast_task_user_update, null, false)
            val toast = Toast(this)
            toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
            toast.duration = Toast.LENGTH_LONG
            toast.view = alertToastLayout
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))

        }






        imageViewSelected.setOnClickListener {
            val alertLayout=LayoutInflater.from(this).inflate(R.layout.choose_image_source_alert,null,false)
            val alert=AlertDialog.Builder(this).create()
            alert.window?.setBackgroundDrawable(getDrawable(R.drawable.radius_alert))




            val clickOpenGallery=alertLayout.findViewById<TextView>(R.id.textViewGalleryImage)
            val clickOpenCamera=alertLayout.findViewById<TextView>(R.id.textViewCameraImage)
            val clickOpenGalleryImageView=alertLayout.findViewById<ImageView>(R.id.imageViewGalleryImageSeleted)
            val clickOpenCameraImageView=alertLayout.findViewById<ImageView>(R.id.imageViewCameraImageSelected)
            clickOpenGallery.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(intent,666)
                alert.dismiss()
            }
            clickOpenGalleryImageView.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_PICK_IMAGES)
                startActivityForResult(intent,666)
                alert.dismiss()
            }
            clickOpenCamera.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,777)
                alert.dismiss()
            }
            clickOpenCameraImageView.setOnClickListener {
                val intent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,777)
                alert.dismiss()
            }




            alert.setView(alertLayout)
            alert.show()
        }

    }

    fun getByteArrayFun(bitmap: Bitmap):ByteArray{
        var byteArrayOutputStream=ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 777 && resultCode == RESULT_OK){
            val bitMap=data?.extras?.get("data") as Bitmap
            imageViewSelected.setImageBitmap(bitMap)
        }
        else if(requestCode == 666 && resultCode == RESULT_OK){
            imageViewSelected.setImageURI(data?.data)
        }
    }
}

