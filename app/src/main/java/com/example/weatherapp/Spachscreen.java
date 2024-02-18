package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class Spachscreen extends AppCompatActivity {
    ProgressBar prog;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_spachscreen);
        prog = findViewById(R.id.prog);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startup();
            }
        });

        thread.start();
    }

    private void startup() {
        Intent intent;
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void doWork() {

        for (progress= 0; progress<=100; progress = progress + 20)
        {
            try {
                Thread.sleep(1000);
                prog.setProgress(progress);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}