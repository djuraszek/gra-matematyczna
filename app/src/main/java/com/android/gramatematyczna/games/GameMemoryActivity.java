package com.android.gramatematyczna.games;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.PreferencesManagement;
import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.GameActivity;
import com.android.gramatematyczna.customdialogs.EndGameDialogClass;
import com.android.gramatematyczna.customdialogs.PauseDialogClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMemoryActivity extends AppCompatActivity {
    int newNumber;
    int points = 0;
    int totalOpenCards = 0;
    int totalMoves = 0;

    TextView movesNumberTV;

    ArrayList<Integer> game = new ArrayList<>();
    Game g;
    public Integer[] elementsImages;
    public Integer[] elementsBacks;
    public List<Integer> elemImages = new ArrayList<>();
    public List<Integer> elemBacks = new ArrayList<>();
    public List<MemoryCard> elements = new ArrayList<>();
    int numOfOpen = 0;
    MemoryCard mem1 = new MemoryCard();
    MemoryCard mem2 = new MemoryCard();
    int position1;
    ImageView firstImageView;


    PreferencesManagement preferencesManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_game_memory);
        preferencesManagement = new PreferencesManagement(GameMemoryActivity.this);
        preferencesManagement.manage();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        newNumber = extras.getInt("NEW_NUMBER", 3);
        g = new Game(newNumber);
        game = g.game;

        movesNumberTV = findViewById(R.id.moves_number);
        movesNumberTV.setText("0");

        createGame();

    }

    public void createGame() {
        generateElementList2(3);
    }

    private void generateElementList(int numberOfElements) {
        String imgName1 = "";
        String imgName2 = "";
        elementsImages = new Integer[10];
        elementsBacks = new Integer[10];
        for (int i = 0; i < 9; i = i + 2) {
            imgName1 = "img_memory" + game.get(i / 2);
            imgName2 = "img_number_" + game.get(i / 2);
            int imgID = getDrawableByName(imgName1);
            elementsImages[i] = imgID;
            int imgID2 = getDrawableByName(imgName2);
            elementsImages[i + 1] = imgID2;
            elementsBacks[i] = getDrawableByName("board_square");
            elementsBacks[i + 1] = getDrawableByName("board_square");
        }

        GridView gridview = (GridView) findViewById(R.id.GridViewMemory);
        gridview.setAdapter(new GridViewImageAdapter(this, elementsBacks));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                // TODO
                ImageView imageView = (ImageView) v;
                if (numOfOpen < 2 && imageView.getTag() != "front") {
                    imageView.setImageResource(elementsImages[position]);
                    numOfOpen++;
                    totalMoves++;
                    changeMovesTextView();
                    imageView.setTag("front");
                } else if (imageView.getTag() == "front") {
                    imageView.setImageResource(getDrawableByName("board_square"));
                    imageView.setTag(null);
                    ;
                    numOfOpen--;
                }
            }
        });
    }

    private void generateElementList2(int numberOfElements) {
        MemoryCard mem = new MemoryCard();
        String imgName1 = "";
        String imgName2 = "";
        int back = getDrawableByName("board_square");
        elementsImages = new Integer[10];
        elementsBacks = new Integer[10];
        for (int i = 0; i < 9; i = i + 2) {
            imgName1 = "img_memory" + game.get(i / 2);
            imgName2 = "img_number_" + game.get(i / 2);
            int imgID = getDrawableByName(imgName1);
            mem = new MemoryCard(game.get(i / 2), imgName1, imgID, back);
            elements.add(mem);
            int imgID2 = getDrawableByName(imgName2);
            mem = new MemoryCard(game.get(i / 2), imgName2, imgID2, back);
            elements.add(mem);
        }
        Collections.shuffle(elements);

        GridView gridview = (GridView) findViewById(R.id.GridViewMemory);
        gridview.setAdapter(new GridViewMemoryAdapter(this, elements));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, final int position, long id) {

                final ImageView imageView = (ImageView) v;
                if (!elements.get(position).isOpen && numOfOpen == 0) {
                    elements.get(position).open();
                    numOfOpen++;
                    totalMoves++;
                    changeMovesTextView();
                    mem1 = elements.get(position);
                    position1 = position;
                    firstImageView = (ImageView) v;
                    imageView.setImageResource(elements.get(position).getPictureID());
                } else if (!elements.get(position).isOpen && numOfOpen == 1) {
                    numOfOpen++;
                    totalMoves++;
                    changeMovesTextView();
                    mem2 = elements.get(position);
                    imageView.setImageResource(elements.get(position).getPictureID());
                    if (mem1.getNumber() == mem2.getNumber()) {
                        mem1.leftOpen();
                        mem2.leftOpen();
                        numOfOpen = 0;
                        addTotalOpenCards();
                    } else {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                firstImageView.setImageResource(elements.get(position1).getPictureBack());
                                imageView.setImageResource(elements.get(position).getPictureBack());
                                mem1.close();
                                mem2.close();
                                numOfOpen = 0;
                            }
                        }, 1000);
                    }
                }
                System.out.println("GameMemoryActivity totalMoves=" + totalMoves);
                System.out.println("GameMemoryActivity totalOpenC=" + totalOpenCards);
            }
        });
    }

    private void check(MemoryCard mem1, MemoryCard mem2, ImageView imageView, int position) {
        if (mem1.getNumber() == mem2.getNumber()) {
            mem1.leftOpen();
            mem2.leftOpen();
            addTotalOpenCards();
        } else {
            imageView.setImageResource(elements.get(position1).getPictureBack());
            imageView.setImageResource(elements.get(position).getPictureBack());
            mem1.close();
            mem2.close();
        }
    }

    private void addTotalOpenCards() {
        totalOpenCards += 2;
        if (totalOpenCards == elements.size()) {
            Log.e("GameMemoryActivity", "GAME OVER: total moves: " + totalMoves);
            showSummaryDialog();
        }
    }

    private int getDrawableByName(String name) {
        int resID = getResources().getIdentifier(name, "drawable", getPackageName());
        return resID;
    }

    private void changeMovesTextView() {
        String text = "" + totalMoves;
        movesNumberTV.setText(text);
    }

    private void showSummaryDialog() {
        int correctAnswers = 0;
        int coins = 0;
        if (totalMoves < 15) {
            coins = 3;
            correctAnswers = 5;
        } else if (totalMoves < 20) {
            coins = 2;
            correctAnswers = 4;
        } else if (totalMoves < 25) {
            coins = 1;
            correctAnswers = 1;
        }
        preferencesManagement.addCoins(coins);

        EndGameDialogClass dialog = new EndGameDialogClass(GameMemoryActivity.this, correctAnswers) {
            @Override
            public void playAgain() {
                super.playAgain();
                activity.finish();
                Intent intent = new Intent(activity, GameCountActivity.class);
                intent.putExtra("NEW_NUMBER", newNumber);
                activity.startActivity(intent);
                finish();
            }
        };
        dialog.setupPrefManagement(preferencesManagement);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    public void pauseGame(View view) {
        //todo zrobic
        PauseDialogClass dialog = new PauseDialogClass(GameMemoryActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setupPrefManagement(preferencesManagement);
        dialog.show();
    }
}