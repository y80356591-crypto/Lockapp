package com.example.lock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LockActivity extends Activity {

    private static final String CORRECT_PASSWORD = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_lock);

        EditText passwordInput = findViewById(R.id.passwordInput);
        Button unlockBtn = findViewById(R.id.unlockBtn);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = passwordInput.getText().toString();
                if (input.equals(CORRECT_PASSWORD)) {
                    stopService(new Intent(LockActivity.this, GuardService.class));
                    finish();
                } else {
                    Toast.makeText(LockActivity.this, "Password မှားနေပါတယ်", Toast.LENGTH_SHORT).show();
                    passwordInput.setText("");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // disabled
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME ||
            keyCode == KeyEvent.KEYCODE_APP_SWITCH ||
            keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
