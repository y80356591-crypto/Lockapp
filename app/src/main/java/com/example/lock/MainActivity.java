package com.example.lock;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button startBtn = new Button(this);
        startBtn.setText("Lock စတင်မည်");
        startBtn.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(this, GuardService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
            finish();
        });

        setContentView(startBtn);
    }
}
