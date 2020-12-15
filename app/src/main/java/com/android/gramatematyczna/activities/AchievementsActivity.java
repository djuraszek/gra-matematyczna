package com.android.gramatematyczna.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.gramatematyczna.adapters.DrawingsGridAdapter;
import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;

public class AchievementsActivity extends AppCompatActivity {

    int[] drawingList = {R.drawable.rys_1,R.drawable.rys_2,R.drawable.rys_3,R.drawable.rys_4,R.drawable.rys_5,R.drawable.rys_6,R.drawable.rys_7,R.drawable.rys_8,R.drawable.rys_9,R.drawable.rys_10};
    int hearts = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_achievements);
        PreferencesManagement preferencesManagement = new PreferencesManagement(AchievementsActivity.this);
        preferencesManagement.manage();
        hearts = preferencesManagement.getHearts();

        DrawingsGridAdapter adapter = new DrawingsGridAdapter(AchievementsActivity.this, drawingList);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        gridView.setAdapter(adapter);
        setupHeartsBar();
    }

    public void onBackPressed(View view) {
        super.onBackPressed();
    }

    int[] coinsIV = {R.id.bar_heart1,R.id.bar_heart2,R.id.bar_heart3,R.id.bar_heart4,R.id.bar_heart5};
    public void setupHeartsBar(){
        for(int i=0; i<hearts;i++){
            ((ImageView)findViewById(coinsIV[i])).setBackground(getResources().getDrawable(R.drawable.heart));
            if(i==4) i=hearts;
        }
        if(hearts==5){
            Toast.makeText(this,"Możesz odblokowac kolorowanke!",Toast.LENGTH_SHORT).show();
        }
    }
}
