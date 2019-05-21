package com.example.ateball;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    ImageView ball;
    TextView text;
    Animation shake;
    Date startTime;
    Date stopTime;
    boolean rolling = false;
    private String [] answers = new String[20];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //text content

        answers[0] = "It is certain.";
        answers[1] = "It is decidedly so";
        answers[2] = "Without a doubt.";
        answers[3] = "Yes - definitely.";
        answers[4] = "You may rely on it.";
        answers[5] = "As I see it, yes.";
        answers[6] = "Most likely.";
        answers[7] = "Outlook good.";
        answers[8] = "Yes.";
        answers[9] = "Signs point to yes.";
        answers[10] = "Reply hazy, try again.";
        answers[11] = "Ask again later.";
        answers[12] = "Better not \n tell you now.";
        answers[13] = "Cannot predict now.";
        answers[14] = "Concentrate \n and ask again.";
        answers[15] = "Don't count on it.";
        answers[16] = "My reply is no.";
        answers[17] = "My sources say no.";
        answers[18] = "Outlook not so good.";
        answers[19] = "Very doubtful.";





                ball = findViewById(R.id.imageView);
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        text = findViewById(R.id.textView);
        SensorManager lightSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor lightSensor = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorManager.registerListener(
                lightSensorListener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private final SensorEventListener lightSensorListener
            = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                //textLIGHT_reading.setText("LIGHT: " + event.values[0]);
                Log.i("TAG", "" + event.values[0]);
                //text.setText("" + event.values[0]);
                if (event.values[0] < 30.0 && !rolling) {
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.eightsize));
                    text.setText("8");
                    startTime = Calendar.getInstance().getTime();
                    Log.i("TIME_START", "" +startTime.getTime());
                    ball.startAnimation(shake);
                    rolling = true;
                } else if (event.values[0] > 30.0 &&  rolling) {
                    stopTime = Calendar.getInstance().getTime();
                    Log.i("TIME_STOP", "" +stopTime.getTime());
                    int result = (int) ((stopTime.getTime() - startTime.getTime()) % 20);
                    Log.i("RESULT", "" +result);
                    rolling = false;
                    text.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            getResources().getDimension(R.dimen.textsize));
                    text.setText(answers[result]);

                } else if (rolling) {
                    ball.startAnimation(shake);
                }
            }
        }

    };
}
