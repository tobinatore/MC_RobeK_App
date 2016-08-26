package com.noethlich.tobias.mcrobektrainingsplaner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetNameActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MRTP_PREFS";
    public static final String PREFS_KEY = "MRTP_PREFS_String";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        final Button button = (Button) findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveName();

            }
        });

    }

    public void saveName(){

        EditText editText = (EditText) findViewById(R.id.codeField);
        String name = editText.getText().toString();

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(PREFS_KEY, name);
        editor.commit();

        Intent getToMain = new Intent(this, MainActivity.class);
        startActivity(getToMain);
    }
}
