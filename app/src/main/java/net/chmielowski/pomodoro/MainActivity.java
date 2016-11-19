package net.chmielowski.pomodoro;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
                        new CountDownTimer(30000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                mTextField.setText(String.valueOf(
                                        millisUntilFinished / 1000));
                            }

                            public void onFinish() {
                                mTextField.setText("Done!");
                            }
                        }.start();


                    }
                }
        );
    }
}
