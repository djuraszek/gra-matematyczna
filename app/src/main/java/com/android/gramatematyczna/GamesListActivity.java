package com.android.gramatematyczna;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GamesListActivity extends AppCompatActivity {

    PreferencesManagement preferencesManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreenMode();
        setContentView(R.layout.activity_games_list);

        preferencesManagement = new PreferencesManagement(GamesListActivity.this);
        preferencesManagement.manage();
    }

    public void setFullScreenMode(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }
}
