package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Tobias on 05.08.2016.
 */
public class WahlFragment extends Fragment {

    GridView gridView;
    Context context;
    ArrayList<Boolean> activatedButtons;
    ButtonCheck bc = new ButtonCheck();

    public WahlFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_wahl, container, false);

        gridView = (GridView) v.findViewById(R.id.gridView1);

        gridView.setAdapter(new GridAdapter(context));


        return v;
    }



}
