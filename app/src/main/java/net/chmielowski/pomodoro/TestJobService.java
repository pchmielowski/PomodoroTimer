package net.chmielowski.pomodoro;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class TestJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("pchm", "on start job: " + params.getJobId());

        Uri notification = RingtoneManager.getDefaultUri(
                RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(
                getApplicationContext(), notification);
        r.play();
        Intent i = new Intent();
        i.setClassName(
                "net.chmielowski.pomodoro",
                "net.chmielowski.pomodoro.MainActivity"
        );
        Log.d("pchm", "end");

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
