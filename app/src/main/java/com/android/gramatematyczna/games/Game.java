package com.android.gramatematyczna.games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by B.A.Wędrychowicz on 23.11.2020
 */
class Game {
    int newNumber;
    ArrayList<Integer> numbers = new ArrayList<>();
    ArrayList<Integer> game = new ArrayList<>();
//    ArrayList<Integer> numbers = new ArrayList<>();

    Random r=new Random();

    public Game(int number){
        newNumber=number;
        for(int i=1; i<number; i++){
            numbers.add(i);
        }
        if(number>5){
            game.add(number);
            for(int i=0; i<4; i++){
                int n=r.nextInt(numbers.size());
                game.add(numbers.get(n));
                numbers.remove(n);
            }
        }else {
            for(int i=0; i<numbers.size(); i++){
                game.add(numbers.get(i));
            }
            game.add(number);
            for (int i = 0; i < 5 - number; i++) {
                int n = r.nextInt(numbers.size());
                game.add(numbers.get(n));
                numbers.remove(n);
            }
        }
    }

    public ArrayList<Integer> generateButtons(int pos){
        ArrayList<Integer> buttons = new ArrayList<Integer>();
        if(newNumber>4) {
            int m = game.get(pos);
            numbers.clear();
            for (int i = 1; i < newNumber; i++) {
                if (i != m) numbers.add(i);
            }
            buttons.add(m); //dodaje aktualnie wyswietlaną liczbę
            //losuje resztę
            for (int i = 0; i < 4; i++) {
                int n = r.nextInt(numbers.size());
                buttons.add(numbers.get(n));
                numbers.remove(n);
            }
            Collections.sort(buttons);
        }else {
            for (int i = 1; i <= newNumber; i++) {
                buttons.add(i);
            }
        }
        return buttons;
    }
    public ArrayList<Integer> getGame(){
        return game;
    }


}
