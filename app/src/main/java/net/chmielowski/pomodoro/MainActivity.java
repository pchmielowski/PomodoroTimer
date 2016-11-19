package net.chmielowski.pomodoro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long pomodoroTime = 1000 * 60 * 25;
        findViewById(R.id.main_button_start_pomodoro).setOnClickListener(
                new StartTimer(pomodoroTime)
        );
        long shortBreakTime = 1000 * 60 * 5;
        findViewById(R.id.main_button_start_short).setOnClickListener(
                new StartTimer(shortBreakTime)
        );
        long longBreakTime = 1000 * 60 * 10;
        findViewById(R.id.main_button_start_long).setOnClickListener(
                new StartTimer(longBreakTime)
        );
    }

    private class StartTimer implements View.OnClickListener {
        private final long mTime;

        private StartTimer(long time) {this.mTime = time;}

        @Override
        public void onClick(View view) {
            Log.d("pchm", "click");
            AlarmManager processTimer =
                    (AlarmManager) getSystemService(
                            ALARM_SERVICE);
            Intent intent = new Intent(
                    getApplicationContext(), Receiver.class);
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(
                            getApplicationContext(), 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            processTimer.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    mTime,
                    pendingIntent
            );
        }
    }
}

