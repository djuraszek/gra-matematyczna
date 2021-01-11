package com.android.gramatematyczna.customdialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.GamesListActivity;
import com.android.gramatematyczna.activities.MainActivity;

import java.util.List;

public class DialogShowCode extends Dialog implements View.OnClickListener {
    public Activity activity;
    Button backBtn;
    String code = "";

    public DialogShowCode(Activity activity, String code) {
        super(activity);
        this.activity = activity;
        this.code = code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_show_code);

        TextView code = (TextView)findViewById(R.id.drawing_code);
        code.setText(this.code);

        backBtn = (Button) findViewById(R.id.close_btn);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_btn:
                this.dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
