package net.chmielowski.pomodoro;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public final class MainActivity extends AppCompatActivity {

    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = new Counter(
                PreferenceManager.getDefaultSharedPreferences(
                        MainActivity.this));

        final int MINUTE = 1000 * 60;
        final long POMODORO = MINUTE * 25;
        findViewById(R.id.main_button_start_pomodoro).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        counter.incrementCounter();
                        new StartTimer(POMODORO).onClick();
                        showCounter();
                    }
                }
        );
        final long SHORT_BREAK = 1000 * 60 * 5;
        findViewById(R.id.main_button_start_short).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new StartTimer(SHORT_BREAK).onClick();
                    }
                }

        );
        final long LONG_BREAK = 1000 * 60 * 10;
        findViewById(R.id.main_button_start_long).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new StartTimer(LONG_BREAK).onClick();
                    }
                }

        );
        showCounter();
    }

    private void showCounter() {
        ((TextView) findViewById(R.id.main_tv_last))
                .setText(String.valueOf(
                        counter.number()));
    }

    static class Counter {

        private final SharedPreferences mPrefs;

        public Counter(SharedPreferences preferences) {
            mPrefs = preferences;
        }

        private long number() {
            return mPrefs.getLong("counter", 0);
        }

        private void incrementCounter() {
            mPrefs.edit().putLong(
                    "counter", number() + 1).apply();
        }
    }

    private class StartTimer {
        private final long mTime;

        private StartTimer(long time) {
            this.mTime = time;
        }

        void onClick() {
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

