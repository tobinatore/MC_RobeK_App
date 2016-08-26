package com.noethlich.tobias.mcrobektrainingsplaner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tobias on 09.08.2016.
 */
public class CustomOnLongClickListener implements View.OnLongClickListener {
    private final int position;
    Context context;
    Button button;

    public CustomOnLongClickListener(int position, Context cont, Button btn) {
        this.position = position;
        this.context = cont;
        this.button = btn;
    }

    public boolean onLongClick(View v)
    {


        if(button.getTag(R.id.status)=="not_activated") {
            button.setBackgroundResource(R.drawable.button_border_activated);
            button.setTag(R.id.status,"activated");

        }else{
            button.setBackgroundResource(R.drawable.button_border);
            button.setTag(R.id.status,"not_activated");
        }

        return true;
    }

}


