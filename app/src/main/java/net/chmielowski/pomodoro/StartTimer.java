package net.chmielowski.pomodoro;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

class StartTimer {
    private MainActivity mainActivity;
    private final long mTime;

    StartTimer(MainActivity mainActivity, long time) {
        this.mainActivity = mainActivity;
        this.mTime = time;
    }

    void perform() {
        Log.d("pchm", "start for " + String.valueOf(mTime));
        JobInfo.Builder builder = new JobInfo.Builder(
                0,
                new ComponentName(
                        mainActivity.getApplicationContext(), ShowTimeoutService.class)
        );
        builder.setMinimumLatency(mTime);
        builder.setOverrideDeadline(mTime);
        JobScheduler jobScheduler =
                (JobScheduler) mainActivity.getApplication().getSystemService(
                        Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
