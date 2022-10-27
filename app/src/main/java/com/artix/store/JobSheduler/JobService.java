package com.artix.store.JobSheduler;

import android.app.job.JobParameters;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import com.artix.store.MainActivity2;

public class JobService extends android.app.job.JobService {
    private static final  String TAG ="Background Job";
    private  boolean jobCanceled =false;

    public JobService() {
        Configuration.Builder builder = new Configuration.Builder();
        builder.setJobSchedulerJobIdRange(0,9000);

    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: Start");
        doBackground(jobParameters);
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "canceled befour complete: ");
        jobCanceled = true;
        return true;
    }


    private void doBackground(JobParameters params) {
        if (!jobCanceled){

            MainActivity2.Companion.queryImageStorage(getApplication());


            Log.d(TAG, "run: Finished");
            jobFinished(params,false);

        }
    }
}
