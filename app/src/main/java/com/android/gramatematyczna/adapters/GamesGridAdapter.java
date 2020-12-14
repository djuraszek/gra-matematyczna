package com.android.gramatematyczna.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.android.gramatematyczna.R;
import com.android.gramatematyczna.activities.GameActivity;
import com.android.gramatematyczna.games.GameCountActivity;
import com.android.gramatematyczna.games.GameListItem;
import com.android.gramatematyczna.games.GameMemoryActivity;

public class GamesGridAdapter extends BaseAdapter {
    Context context;
    int colors[];
    LayoutInflater inflter;
    boolean isTMP=false;

    public GamesGridAdapter(Context applicationContext, int[] colors) {
        this.context = applicationContext;
        this.colors = colors;
        inflter = (LayoutInflater.from(applicationContext));
    }

    GameListItem gamesListTMP[];
    public GamesGridAdapter(Context applicationContext, GameListItem[] gamesListTMP) {
        this.context = applicationContext;
        this.gamesListTMP = gamesListTMP;
        inflter = (LayoutInflater.from(applicationContext));
        isTMP=true;
    }

    @Override
    public int getCount() {
         if(isTMP)
             return gamesListTMP.length;
         else return  colors.length;
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_grid_drawings, null); // inflate the layout
        CardView card = (CardView) view.findViewById(R.id.cardview); // get the reference of ImageView
        int color;
        if(isTMP){
            color = ResourcesCompat.getColor(context.getResources(), gamesListTMP[i].getColor(), null);
        }else{
            color = ResourcesCompat.getColor(context.getResources(), colors[i], null);
        }

        ImageView background = (ImageView)view.findViewById(R.id.card_background);
        ImageView lock = (ImageView)view.findViewById(R.id.item_lock);
        ImageView download = (ImageView)view.findViewById(R.id.item_unlock);
        ImageView gameIcon = view.findViewById(R.id.game_icon);
        background.setBackgroundColor(color); // set logo images
//        card.setCardBackgroundColor(drawing); // set logo images

        if(i==0 || i==1) {
            lock.setVisibility(View.INVISIBLE);
            if(isTMP) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(gamesListTMP[i].getGameType()==0) {
                            Intent intent = new Intent(context, GameCountActivity.class);
                            intent.putExtra("NEW_NUMBER", gamesListTMP[i].getNumber());
                            context.startActivity(intent);
                        }else if(gamesListTMP[i].getGameType()==1){
                            Intent intent = new Intent(context, GameMemoryActivity.class);
                            intent.putExtra("NEW_NUMBER", gamesListTMP[i].getNumber());
                            context.startActivity(intent);
                        }
                    }
                });
            }else{
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GameCountActivity.class);
                        intent.putExtra("NEW_NUMBER", 7);
                        context.startActivity(intent);
                    }
                });
            }
        }
        return view;
    }


}