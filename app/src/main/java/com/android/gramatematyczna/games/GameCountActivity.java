package com.android.gramatematyczna.games;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.customdialogs.EndGameDialogClass;
import com.android.gramatematyczna.customdialogs.PauseDialogClass;

import java.util.ArrayList;
import java.util.Random;

public class GameCountActivity extends AppCompatActivity {
    int newNumber;
    int level = 0;
    int points = 0;
    Context ctx;
    ArrayList<Integer> game = new ArrayList<>();
    ArrayList<Integer> buttons = new ArrayList<>();
    LinearLayout answersLayout;
    TextView command;
    Game g;
    PreferencesManagement preferencesManagement;
    public Integer[] elementsImages;
    public Integer[] buttonsImages;
    public Integer[] answers = {0, 0, 0, 0, 0}; // bedziemy zapisywac 0-1 czy poprawnie odpowiedzieli, czy nie

    public String[] imageNames = {
            "img_cat",
            "img_dog",
            "img_star"
    };
    public int[] imageNamesCommand = {
            R.string.command_count_cat,
            R.string.command_count_dog,
            R.string.command_count_star
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_game_count);
        preferencesManagement = new PreferencesManagement(GameCountActivity.this);
        preferencesManagement.manage();
        command = findViewById(R.id.count_game_command);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        newNumber = extras.getInt("NEW_NUMBER", 3);
        g = new Game(newNumber);
        game = g.game;
        ctx = getApplicationContext();
        createLevel(level);
        generateButtons();

        answersLayout = findViewById(R.id.answers_layout);
    }


    public void createLevel(int level) {
        generateElementList(game.get(level));
        //nie ma sensu za kazdym razem tworzyc nowy grid z przyciskami
//        generateButtons();
    }


    private void generateElementList(int numberOfElements) {
        Random r = new Random();
        int n=r.nextInt(imageNames.length);
        String imgName = imageNames[n];
        command.setText(imageNamesCommand[n]);
        int imgID = getDrawableByName(imgName);
//        Toast.makeText(this, ""+imgName+"  -  "+imgID, Toast.LENGTH_SHORT).show();
        elementsImages = new Integer[numberOfElements];
        for (int i = 0; i < numberOfElements; i++) {
            elementsImages[i] = imgID;
        }
        GridView gridview = (GridView) findViewById(R.id.GridViewImages);
        gridview.setAdapter(new GridViewImageAdapter(this, elementsImages));

        int columnNum = 1;
        if (numberOfElements > 1 && numberOfElements < 5) columnNum = 2;
        else if (numberOfElements >= 5) columnNum = 3;
        gridview.setNumColumns(columnNum);
    }


    private void generateButtons() {
        buttons.clear();
        buttons = g.generateButtons(level);

        buttonsImages = new Integer[buttons.size()];
        String buttonName = "";
        for (int i = 0; i < buttons.size(); i++) {
            buttonName = "button_" + buttons.get(i);
            buttonsImages[i] = getDrawableByName(buttonName);
        }
        GridView gridview = (GridView) findViewById(R.id.GridViewButtons);
        if (newNumber < 6) gridview.setNumColumns(newNumber);
        else gridview.setNumColumns((int) Math.round(newNumber / 2));

        gridview.setAdapter(new GridViewButtonAdapter(this, buttonsImages));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                // Send intent to SingleViewActivity
//                Toast.makeText(GameCountActivity.this, "clicked!"+buttons.get(position), Toast.LENGTH_SHORT).show();
                checkAnswer(buttons.get(position));
            }
        });

    }

    private int getDrawableByName(String name) {
        int resID = getResources().getIdentifier(name, "drawable", getPackageName());
        return resID;
    }

    private void checkAnswer(int answer) {
        if (answer == game.get(level)) {
            points++;
//            Toast.makeText(this, "correct answer!", Toast.LENGTH_SHORT).show();
            answers[level] = 1; //odpowiedzial poprawnie wiec zapisujemy
            updateAnswersGridColor();
            showCorrectDialog();
        } else {
//            Toast.makeText(this, "wrong answer!", Toast.LENGTH_SHORT).show();
            updateAnswersGridColor();
            showWrongDialog();
        }
    }

    private void nextlevel() {
        level++;
        if (level < 5) createLevel(level);
    }

    //todo
    public void updateAnswersGridColor() {
        //tutaj bierzemy grid i dla konkretnego lvl ustawiamy
        if (answers[level] == 0) {
            //niepoprawnie odpowiedzieli - zmianiamy na czerwony
            int color = getResources().getColor(R.color.red);
            answersLayout.getChildAt(level).setBackgroundColor(color);
        } else {
            //poprawnie odpowiedzieli - zmianiamy na zielony
            int color = getResources().getColor(R.color.green_bright);
            answersLayout.getChildAt(level).setBackgroundColor(color);
        }
    }

    private void showCorrectDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_correct_answer, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        ImageView btn = dialogView.findViewById(R.id.button_corect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level == 4) {
                    alertDialog.cancel();
                    showSummaryDialog();
                }
                else
                    nextlevel();
                alertDialog.cancel();

            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().setLayout(100, 100);
        alertDialog.show();
    }

    private void showWrongDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_wrong_answer, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        Button btn = dialogView.findViewById(R.id.button_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (level == 4){
                    alertDialog.cancel();
                    showSummaryDialog();
                }
                else
                    nextlevel();
                alertDialog.cancel();
            }
        });
        Button btn2 = dialogView.findViewById(R.id.button_again);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.show();
    }

//    private void showSummaryDialog() {
//        ViewGroup viewGroup = findViewById(android.R.id.content);
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_summary, viewGroup, false);
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(dialogView);
//        final AlertDialog alertDialog = builder.create();
//        Button btn = dialogView.findViewById(R.id.button_next);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                alertDialog.cancel();
//            }
//        });
//        Button btn2 = dialogView.findViewById(R.id.button_again);
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                level=0;
////                points=0;
//                finish();
//                Intent intent = new Intent(ctx, GameCountActivity.class);
//                intent.putExtra("NEW_NUMBER", newNumber);
//                startActivity(intent);
//                alertDialog.cancel();
//            }
//        });
//        TextView summaryPoints = dialogView.findViewById(R.id.summary_points);
//        summaryPoints.setText(points + "/5");
//
//        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        alertDialog.show();
//    }

    private void showSummaryDialog() {
        int correctAnswers = points;
        int coins = 0;
        if (correctAnswers > 0 && correctAnswers < 3) coins = 1;
        else if (correctAnswers > 2 && correctAnswers < 5) coins = 2;
        else if (correctAnswers == 5) coins = 3;
        preferencesManagement.addCoins(coins);

        EndGameDialogClass dialog = new EndGameDialogClass(GameCountActivity.this, correctAnswers) {
            @Override
            public void playAgain() {
                super.playAgain();
                activity.finish();
                Intent intent = new Intent(activity, GameCountActivity.class);
                intent.putExtra("NEW_NUMBER", newNumber);
                activity.startActivity(intent);
            }
        };
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    public void pauseGame(View view) {
        //todo zrobic
        PauseDialogClass dialog = new PauseDialogClass(GameCountActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }
}