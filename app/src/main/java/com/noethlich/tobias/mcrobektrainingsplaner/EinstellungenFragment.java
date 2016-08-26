package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Tobias on 05.08.2016.
 */
public class EinstellungenFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    public EinstellungenFragment() {
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

        View v = inflater.inflate(R.layout.fragment_einstellungen, container, false);

        Button nc = (Button) v.findViewById(R.id.namen_ändern);
        nc.setOnClickListener(this);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("STARTUP_ACTIVITY", Activity.MODE_PRIVATE);
        int pos = sharedPreferences.getInt("position", 0);

        Spinner spinner = (Spinner) v.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.namen_ändern:
                Intent i = new Intent(getContext(), ChangeNameActivity.class);
                startActivity(i);
                break;


        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

        SharedPreferences sp = getContext().getSharedPreferences("STARTUP_ACTIVITY", Activity.MODE_PRIVATE);
        String activity = sp.getString("activity", "Standard");
        activity = parent.getItemAtPosition(pos).toString();


        SharedPreferences.Editor editor = sp.edit();
        editor.putString("activity",activity);
        editor.apply();

    }

    public void onNothingSelected(AdapterView<?> parent){

    }
}
