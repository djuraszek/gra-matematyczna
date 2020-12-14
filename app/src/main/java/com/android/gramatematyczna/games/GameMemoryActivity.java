package com.android.gramatematyczna.games;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gramatematyczna.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameMemoryActivity extends AppCompatActivity {
    int newNumber;
    int points=0;
    ArrayList<Integer> game = new ArrayList<>();
    Game g;
    public Integer[] elementsImages;
    public Integer[] elementsBacks;
    public List<Integer> elemImages = new ArrayList<>();
    public List<Integer> elemBacks = new ArrayList<>();
    public List<MemoryCard> elements = new ArrayList<>();
    int numOfOpen=0;
    MemoryCard mem1 = new MemoryCard();
    MemoryCard mem2 = new MemoryCard();
    int position1;
    ImageView firstImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_memory);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        newNumber = extras.getInt("NEW_NUMBER", 3);
        g = new Game(newNumber);
        game=g.game;

        createGame();

    }

    public void createGame(){
        generateElementList2(3);
    }

    private void generateElementList(int numberOfElements){

        String imgName1="";
        String imgName2="";
        elementsImages = new Integer[10];
        elementsBacks = new Integer[10];
       for(int i=0; i<9; i=i+2){
            imgName1="img_memory"+game.get(i/2);
            imgName2="img_number_"+game.get(i/2);
            int imgID = getDrawableByName(imgName1);
            elementsImages[i]=imgID;
           int imgID2 = getDrawableByName(imgName2);
            elementsImages[i+1]=imgID2;
            elementsBacks[i]=getDrawableByName("board_square");
           elementsBacks[i+1]=getDrawableByName("board_square");
        }
        GridView gridview = (GridView) findViewById(R.id.GridViewMemory);
        gridview.setAdapter(new GridViewImageAdapter(this, elementsBacks));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id){
                // TODO
                ImageView imageView = (ImageView) v;
                if(numOfOpen<2 && imageView.getTag()!="front") {
                    imageView.setImageResource(elementsImages[position]);
                    numOfOpen++;
                    imageView.setTag("front");
                }
                else if(imageView.getTag()=="front") {
                    imageView.setImageResource(getDrawableByName("board_square"));
                    imageView.setTag(null);;
                    numOfOpen--;
                }
            }
        });
    }

    private void generateElementList2(int numberOfElements){
        MemoryCard mem = new MemoryCard();
        String imgName1="";
        String imgName2="";
        int back = getDrawableByName("board_square");
        elementsImages = new Integer[10];
        elementsBacks = new Integer[10];
        for(int i=0; i<9; i=i+2){
            imgName1="img_memory"+game.get(i/2);
            imgName2="img_number_"+game.get(i/2);
            int imgID = getDrawableByName(imgName1);
            mem = new MemoryCard(game.get(i/2), imgName1, imgID, back);
            elements.add(mem);
            int imgID2 = getDrawableByName(imgName2);
            mem = new MemoryCard(game.get(i/2), imgName2, imgID2, back);
            elements.add(mem);
        }
        Collections.shuffle(elements);

        GridView gridview = (GridView) findViewById(R.id.GridViewMemory);
        gridview.setAdapter(new GridViewMemoryAdapter(this, elements));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, final int position, long id){
                // TODO
                final ImageView imageView = (ImageView) v;
                if(!elements.get(position).isOpen && numOfOpen==0){
                    elements.get(position).open();
                    numOfOpen++;
                    mem1=elements.get(position);
                    position1=position;
                    firstImageView = (ImageView) v;
                    imageView.setImageResource(elements.get(position).getPictureID());
                }else if (!elements.get(position).isOpen && numOfOpen==1){
                    numOfOpen++;
                    mem2=elements.get(position);
                    imageView.setImageResource(elements.get(position).getPictureID());
                    if(mem1.getNumber()==mem2.getNumber()){
                        mem1.leftOpen();
                        mem2.leftOpen();
                        numOfOpen=0;
                    }
                    else{
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                firstImageView.setImageResource(elements.get(position1).getPictureBack());
                                imageView.setImageResource(elements.get(position).getPictureBack());
                                mem1.close();
                                mem2.close();
                                numOfOpen=0;
                            }
                        }, 1000);
                    }
                }



//                if(numOfOpen<2 && imageView.getTag()!="front") {
//                    imageView.setImageResource(elements.get(position).getPictureID());
//                    numOfOpen++;
//                    imageView.setTag("front");
//
//                }
////                else if(imageView.getTag()=="front") {
////                    imageView.setImageResource(getDrawableByName("board_square"));
////                    imageView.setTag(null);;
////                    numOfOpen--;
////                }
//                else if(imageView.getTag()=="front") {
//                    imageView.setImageResource(getDrawableByName("board_square"));
//                    imageView.setTag(null);;
//                    numOfOpen--;
//                }
            }
        });
    }

    private void check(MemoryCard mem1, MemoryCard mem2, ImageView imageView, int position){
        if(mem1.getNumber()==mem2.getNumber()){
            mem1.leftOpen();
            mem2.leftOpen();
        }
        else{
            imageView.setImageResource(elements.get(position1).getPictureBack());
            imageView.setImageResource(elements.get(position).getPictureBack());
            mem1.close();
            mem2.close();
        }
    }

    private int getDrawableByName(String name){
        int resID = getResources().getIdentifier(name , "drawable", getPackageName());
        return resID;
    }
}