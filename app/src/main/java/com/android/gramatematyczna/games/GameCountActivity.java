package com.android.gramatematyczna.games;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.R;

import java.util.ArrayList;
import java.util.Random;

public class GameCountActivity extends AppCompatActivity {
    int newNumber;
    int level=0;
    int points=0;
    Context ctx;
    ArrayList<Integer> game = new ArrayList<>();
    ArrayList<Integer> buttons = new ArrayList<>();
    Game g;
    public Integer[] elementsImages;
    public Integer[] buttonsImages;

    public String[] imageNames = {
            "img_cat",
            "img_dog",
            "img_star"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_count);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        newNumber = extras.getInt("NEW_NUMBER", 3);
        g = new Game(newNumber);
        game=g.game;
        ctx = getApplicationContext();
        createLevel(level);
    }


    public void createLevel(int level){
        generateElementList(game.get(level));
        generateButtons();
    }


    private void generateElementList(int numberOfElements){
        Random r = new Random();
        String imgName=imageNames[r.nextInt(imageNames.length)];
        int imgID = getDrawableByName(imgName);
//        Toast.makeText(this, ""+imgName+"  -  "+imgID, Toast.LENGTH_SHORT).show();
        elementsImages = new Integer[numberOfElements];
        for(int i=0; i<numberOfElements; i++){
            elementsImages[i]=imgID;
        }
        GridView gridview = (GridView) findViewById(R.id.GridViewImages);
        gridview.setAdapter(new GridViewImageAdapter(this, elementsImages));
    }
    private void generateButtons(){
        buttons.clear();
        buttons=g.generateButtons(level);
        buttonsImages = new Integer[buttons.size()];
        String buttonName="";
        for(int i=0; i<buttons.size(); i++){
            buttonName="button_"+buttons.get(i);
            buttonsImages[i]=getDrawableByName(buttonName);
        }
        GridView gridview = (GridView) findViewById(R.id.GridViewButtons);
        gridview.setAdapter(new GridViewButtonAdapter(this, buttonsImages));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // Send intent to SingleViewActivity
//                Toast.makeText(GameCountActivity.this, "clicked!"+buttons.get(position), Toast.LENGTH_SHORT).show();
            checkAnswer(buttons.get(position));
            }
        });
    }
    private int getDrawableByName(String name){
        int resID = getResources().getIdentifier(name , "drawable", getPackageName());
        return resID;
    }

    private void checkAnswer(int answer){
        if(answer==game.get(level)){
            points++;
//            Toast.makeText(this, "correct answer!", Toast.LENGTH_SHORT).show();
            if(level==4)
                showSummaryDialog();
            else
                showCorrectDialog();
        }else{
//            Toast.makeText(this, "wrong answer!", Toast.LENGTH_SHORT).show();
            showWrongDialog();
        }
    }

    private void nextlevel(){
        level++;
        if(level<5) createLevel(level);
    }

    private void showCorrectDialog(){
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_correct_answer, viewGroup, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            final AlertDialog alertDialog = builder.create();
        ImageView btn = dialogView.findViewById(R.id.button_corect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlevel();
                alertDialog.cancel();
            }
        });

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.getWindow().setLayout(100, 100);
            alertDialog.show();
    }
    private void showWrongDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_wrong_answer, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button btn = dialogView.findViewById(R.id.button_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextlevel();
                alertDialog.cancel();
            }
        });
        Button btn2= dialogView.findViewById(R.id.button_again);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
    private void showSummaryDialog(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_summary, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button btn = dialogView.findViewById(R.id.button_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                alertDialog.cancel();
            }
        });
        Button btn2= dialogView.findViewById(R.id.button_again);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                level=0;
//                points=0;
                finish();
                Intent intent = new Intent(ctx, GameCountActivity.class);
                intent.putExtra("NEW_NUMBER", newNumber);
                startActivity(intent);
                alertDialog.cancel();
            }
        });
        TextView summaryPoints = dialogView.findViewById(R.id.summary_points);
        summaryPoints.setText(points+"/5");

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }
}