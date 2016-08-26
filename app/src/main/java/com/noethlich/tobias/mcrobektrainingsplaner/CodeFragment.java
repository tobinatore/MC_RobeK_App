package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CodeFragment extends Fragment implements View.OnClickListener {

    public CodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_code, container, false);

        Button sc = (Button) v.findViewById(R.id.button_save_code);
        sc.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save_code:
                EditText feld = (EditText) v.findViewById(R.id.codeField);
                String code = feld.getText().toString();

                SharedPreferences settings;
                SharedPreferences.Editor editor;
                settings = getContext().getSharedPreferences("MR_CODE", Context.MODE_PRIVATE);
                editor = settings.edit();
                editor.putString("code", code);
                editor.commit();
                break;


        }
    }


}
