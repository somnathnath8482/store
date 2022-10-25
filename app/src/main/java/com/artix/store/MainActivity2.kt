package com.artix.store

import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artix.store.Helper.OnselectFile
import com.artix.store.Helper.picItCall
import com.hbisoft.pickit.PickiT


class MainActivity2 : AppCompatActivity() {
    var pickiT: PickiT? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val picItCall = picItCall(object : OnselectFile {
            override fun OnSelect(path: String?) {
                val pats: MutableList<String> = ArrayList()
                pats.add(path!!)
                try {
                    val s = UploadAsync(applicationContext,pats).execute().get()
                    Log.d("UPLOAD", "on Complete: $s")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        pickiT = PickiT(this,  picItCall, this)
        queryImageStorage()
    }

    fun queryImageStorage() {
        Toast.makeText(this,"Called",Toast.LENGTH_SHORT).show()
        val imageProjection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media._ID
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )

        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)

                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // add the URI to the list
                    // generate the thumbnail
                    Log.e("TAG", "File NAme" + name)
                    pickiT!!.getPath(contentUri,  Build.VERSION.SDK_INT)
                  //  Toast.makeText(this,""+name,Toast.LENGTH_SHORT).show()
                   // return;
                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
                Toast.makeText(this,"Null Cursor",Toast.LENGTH_SHORT).show()
            }
        }
    }
}