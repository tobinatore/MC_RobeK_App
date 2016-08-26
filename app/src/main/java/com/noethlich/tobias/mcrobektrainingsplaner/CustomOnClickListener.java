package com.noethlich.tobias.mcrobektrainingsplaner;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Tobias on 09.08.2016.
 */
public class CustomOnClickListener implements View.OnClickListener {

    private final int position;
    Context context;
    DatabaseHandler db;
    Button button;

    public CustomOnClickListener(int position, Context cont, Button btn) {
        this.position = position;
        this.db = new DatabaseHandler(cont);
        this.context = cont;
        this.button = btn;
    }

    public void onClick(View v)
    {
        Toast.makeText(context, db.getTraining((Integer) button.getTag(R.id.training_id))._combo.toString(), Toast.LENGTH_SHORT).show();
    }
}

