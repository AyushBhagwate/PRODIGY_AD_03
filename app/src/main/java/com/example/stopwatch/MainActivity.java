package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton, pauseButton, resetButton;
    private long startTime = 0;
    private boolean isRunning = false;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateTimer();
            handler.postDelayed(this, 10); // Update every 10 milliseconds
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            handler.postDelayed(runnable, 10);
            isRunning = true;

            startButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            isRunning = false;

            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
        }
    }

    private void resetTimer() {
        if (!isRunning) {
            timerTextView.setText("00:00:000");
            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.GONE);
        }
    }

    private void updateTimer() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int minutes = (int) (elapsedTime / 60000);
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int milliseconds = (int) (elapsedTime % 1000);

        timerTextView.setText(String.format("%02d:%02d:%03d", minutes, seconds, milliseconds));
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
