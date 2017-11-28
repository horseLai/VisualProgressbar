package com.app.laixiaolong.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.visualprogressbar.widgets.CircleProgressBar;
import com.example.visualprogressbar.widgets.LinearProgressBar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
{

    private LinearProgressBar vProgressH;
    private Timer mTimer;
    private LinearProgressBar vProgressV;
    private CircleProgressBar vProgressA;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.addLogAdapter(new AndroidLogAdapter());

        vProgressH = findViewById(R.id.vProgressH);
        vProgressV = findViewById(R.id.vProgressV);
        vProgressA = findViewById(R.id.vProgressA);
    }

    public void startProgress(View view)
    {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask()
            {
                int progress = 0;

                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            vProgressH.setProgress(progress);
                            vProgressV.setProgress(progress);
                            vProgressA.setProgress(progress);
                            if (progress == vProgressH.getMax()) {
                                mTimer.cancel();
                                mTimer = null;
                                progress = 0;
                            }
                            progress += 2;
                            // Logger.i("progress=%d", progress);
                        }
                    });
                }
            }, 0, 30);
        }
    }
}
