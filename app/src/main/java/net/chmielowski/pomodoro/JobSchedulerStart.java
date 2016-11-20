package net.chmielowski.pomodoro;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

abstract class AbstractStart {

    final long mTime;
    final MainActivity mainActivity;

    AbstractStart(MainActivity mainActivity, long time) {
        this.mainActivity = mainActivity;
        this.mTime = time;
    }

    abstract void perform();
}

class JobSchedulerStart extends AbstractStart {
    JobSchedulerStart(MainActivity mainActivity, long time) {
        super(mainActivity, time);
    }

    @Override
    void perform() {
        Log.d("pchm", "start for " + String.valueOf(mTime));
        JobInfo.Builder builder = new JobInfo.Builder(
                0,
                new ComponentName(
                        mainActivity.getApplicationContext(),
                        ShowTimeoutService.class
                )
        );
        builder.setMinimumLatency(mTime);
        builder.setOverrideDeadline(mTime);
        JobScheduler jobScheduler =
                (JobScheduler) mainActivity.getApplication().getSystemService(
                        Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}