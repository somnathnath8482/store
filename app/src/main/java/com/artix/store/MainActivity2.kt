package com.artix.store

import android.annotation.SuppressLint
import android.app.Application
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.ContentUris
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.artix.store.Helper.OnselectFile
import com.artix.store.Helper.picItCall
import com.artix.store.JobSheduler.JobService
import com.artix.store.JobSheduler.ShowNotification
import com.artix.store.MyView.PiData
import com.artix.store.MyView.PieChart
import com.hbisoft.pickit.PickiT
import kotlinx.coroutines.Runnable


class MainActivity2 : AppCompatActivity() {
lateinit var pyu : PieChart;
    @SuppressLint("MissingInflatedId")
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
        pyu = findViewById(R.id.pyu)
        pyu.isShowText = true;
        val piData: MutableList<PiData> = java.util.ArrayList()
        piData.add(PiData(1, "One", 40))
        piData.add(PiData(2, "two", 50))
        piData.add(PiData(3, "three", 75))
        piData.add(PiData(4, "four", 25))
        piData.add(PiData(5, "five", 50))
        piData.add(PiData(6, "six", 30))
        piData.add(PiData(7, "seven", 5))
        piData.add(PiData(8, "eight", 30))
        piData.add(PiData(9, "nine", 50))
        piData.add(PiData(10, "ten", 80))
        pyu.SetList(piData)

        //pyu.draw(Canvas())
        //queryImageStorage()
       // startBackgroundJob()
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
    var handler = Handler()
   @SuppressLint("StaticFieldLeak")
    var sow: ShowNotification? = null

    var pickiT: PickiT? = null
    fun queryImageStorage(cont: Application) {

        Thread {
            handler.post(object : Runnable {
                override fun run() {
                    Toast.makeText(cont,"Called",Toast.LENGTH_SHORT).show()
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
                            sow  = ShowNotification(cont)
                            sow!!.showNotification( it.count, 1)






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
                                // Log.e("TAG", "File NAme" + name)
                                //--pickiT!!.getPath(contentUri,  Build.VERSION.SDK_INT)
                                //  Toast.makeText(this,""+name,Toast.LENGTH_SHORT).show()
                                //return;

                                Thread.sleep(1000)
                                sow!!.UpdateNotification(tot, it.position)

                            }
                        } ?: kotlin.run {
                            Log.e("TAG", "Cursor is null!")
                            Toast.makeText(cont,"Null Cursor",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }.start()

    }
}


}