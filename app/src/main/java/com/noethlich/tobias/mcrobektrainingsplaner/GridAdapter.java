package com.noethlich.tobias.mcrobektrainingsplaner;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tobias on 08.08.2016.
 */
public class GridAdapter extends BaseAdapter {

    Context context;
    LayoutInflater mLayoutInflater;
    DatabaseHandler db;
    Button btn;
    int i, prevPosition, prevSize = 0;
    List<Training> trainings;



    public GridAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        db = new DatabaseHandler(context);
        trainings =  db.getAllTrainings();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SharedPreferences sp = context.getSharedPreferences("Training_ID", Activity.MODE_PRIVATE);
        int prevSize = sp.getInt("prevSize", 0);

        if (prevSize > trainings.size()){
            i = 0;
        }

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            btn = new Button(context);
            btn.setLayoutParams(new GridView.LayoutParams(370, 350));
            btn.setPadding(2,100,2,100);
            btn.setOnClickListener(new CustomOnClickListener(position, context, btn));
            btn.setOnLongClickListener(new CustomOnLongClickListener(position, context, btn));

        }
        else {
            btn = (Button) convertView;
        }

        if(i + 1 < trainings.size() && prevPosition != position && prevPosition < position) {
            i++;
        }

        if(i == position) {
            btn.setText(trainings.get(i)._name);


            btn.setTextColor(Color.WHITE);
            btn.setBackgroundResource(R.drawable.button_border);
            btn.setTag(R.id.status, "not_activated");
            btn.setTag(R.id.training_id, trainings.get(i)._id);
            btn.setId(position);
            prevPosition = position;
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("prevSize", trainings.size());
        editor.apply();

        return btn;
}

    @Override
    public int getCount() {
        return db.getTrainingCount();
    }

    @Override
    public Object getItem(int position) {
        return db.getTraining((Integer) btn.getTag(R.id.training_id));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateGrid(){
        i = 0;
    }
}
