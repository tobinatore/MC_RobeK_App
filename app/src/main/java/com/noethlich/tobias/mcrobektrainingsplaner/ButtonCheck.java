package com.noethlich.tobias.mcrobektrainingsplaner;

import android.content.Context;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Tobias on 12.08.2016.
 */
public class ButtonCheck {



    public ButtonCheck(){
    }

    public ArrayList<Boolean> checkAllButtonsForActive(GridView gridView){

        ArrayList<Boolean> activeBtns = new ArrayList<>();


        for(int i = 0; i < gridView.getChildCount(); i++){
            Button btn =(Button) gridView.getChildAt(i);

            if(btn.getTag(R.id.status) == "activated"){
                activeBtns.add(i, true);
            }
            else{
                activeBtns.add(i, false);
            }
        }

        return activeBtns;

    }

    public ArrayList<Integer> getAllIDs(GridView gridView) {
        ArrayList<Integer> allIds = new ArrayList<>();

        for (int i = 0; i < gridView.getChildCount(); i++) {
            Button btn = (Button) gridView.getChildAt(i);
            allIds.add((Integer) btn.getTag(R.id.training_id));

        }

        return allIds;
    }
}
