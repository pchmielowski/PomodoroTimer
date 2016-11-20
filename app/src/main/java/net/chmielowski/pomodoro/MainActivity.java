package net.chmielowski.pomodoro;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
                        counter.increment();
                        new JobSchedulerStart(MainActivity.this, POMODORO).perform();
                        showCounter();
                    }
                }
        );
        final long SHORT_BREAK = 1000 * 60 * 5;
        findViewById(R.id.main_button_start_short).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new JobSchedulerStart(MainActivity.this, SHORT_BREAK)
                                .perform();
                    }
                }

        );
        final long LONG_BREAK = 1000 * 60 * 10;
        findViewById(R.id.main_button_start_long).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new JobSchedulerStart(MainActivity.this, LONG_BREAK).perform();
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


}

