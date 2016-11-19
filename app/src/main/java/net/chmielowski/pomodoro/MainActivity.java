package net.chmielowski.pomodoro;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int MINUTE = 1000 * 60;
        final long POMODORO = MINUTE * 25;
        findViewById(R.id.main_button_start_pomodoro).setOnClickListener(
                new StartTimer(POMODORO)
        );
        final long SHORT_BREAK = 1000 * 60 * 5;
        findViewById(R.id.main_button_start_short).setOnClickListener(
                new StartTimer(SHORT_BREAK)
        );
        final long LONG_BREAK = 1000 * 60 * 10;
        findViewById(R.id.main_button_start_long).setOnClickListener(
                new StartTimer(LONG_BREAK)
        );

        ((TextView) findViewById(R.id.main_tv_last))
                .setText(String.valueOf(
                        PreferenceManager.getDefaultSharedPreferences(
                                MainActivity.this).getLong("last", 0)));
    }

    private class StartTimer implements View.OnClickListener {
        private final long mTime;

        private StartTimer(long time) {this.mTime = time;}

        @Override
        public void onClick(View view) {
            PreferenceManager.getDefaultSharedPreferences(
                    MainActivity.this).edit().putLong(
                    "last", mTime / 1000 / 60).apply();
            Log.d("pchm", "start for " + String.valueOf(mTime));
            JobInfo.Builder builder = new JobInfo.Builder(
                    0,
                    new ComponentName(
                            getApplicationContext(), ShowTimeoutService.class)
            );
            builder.setMinimumLatency(mTime);
            builder.setOverrideDeadline(mTime);
            JobScheduler jobScheduler =
                    (JobScheduler) getApplication().getSystemService(
                            Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());

        }
    }


}

