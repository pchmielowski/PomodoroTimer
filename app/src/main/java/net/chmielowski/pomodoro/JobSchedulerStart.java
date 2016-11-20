package net.chmielowski.pomodoro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

class TimeManagerStart extends AbstractStart {

    TimeManagerStart(MainActivity mainActivity, long time) {
        super(mainActivity, time);
    }

    @Override
    void perform() {
        AlarmManager manager = (AlarmManager) mainActivity.getSystemService(
                Context.ALARM_SERVICE);
        long wakeupTime = System.currentTimeMillis() + mTime;
        manager.setExact(
                AlarmManager.RTC_WAKEUP,
                wakeupTime,
                PendingIntent.getService(mainActivity, 0,
                                         new Intent(
                                                 mainActivity,
                                                 ShowTimeoutService.class
                                         ),
                                         PendingIntent.FLAG_UPDATE_CURRENT
                )
        );
    }
}