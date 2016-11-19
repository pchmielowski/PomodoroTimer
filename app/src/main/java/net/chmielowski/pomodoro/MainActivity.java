package net.chmielowski.pomodoro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView mTextField = (TextView) findViewById(R.id.main_tv);

        findViewById(R.id.main_button_start).setOnClickListener(
                new View.OnClickListener() {
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
                                5000,
                                pendingIntent
                        );
                    }
                }
        );
    }
}

