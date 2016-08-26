package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Tobias on 05.08.2016.
 */
public class TrainingFragment extends Fragment implements View.OnClickListener {

   private Interface listener;

    public TrainingFragment() {
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

        View v = inflater.inflate(R.layout.fragment_training, container, false);

        Button jl = (Button) v.findViewById(R.id.jab_l);
        jl.setOnClickListener(this);

        Button jr = (Button) v.findViewById(R.id.jab_r);
        jr.setOnClickListener(this);

        Button hl = (Button) v.findViewById(R.id.haken_l);
        hl.setOnClickListener(this);

        Button hr = (Button) v.findViewById(R.id.haken_r);
        hr.setOnClickListener(this);

        Button al = (Button) v.findViewById(R.id.aufwärtshaken_l);
        al.setOnClickListener(this);

        Button ar = (Button) v.findViewById(R.id.aufwärtshaken_r);
        ar.setOnClickListener(this);

        Button b = (Button) v.findViewById(R.id.block);
        b.setOnClickListener(this);

        Button cr = (Button) v.findViewById(R.id.cross_r);
        cr.setOnClickListener(this);

        Button cl = (Button) v.findViewById(R.id.cross_l);
        cl.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interface) {
            listener = (Interface) context;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jab_l: listener.getCommands(1);
                break;
            case R.id.jab_r: listener.getCommands(2);
                break;
            case R.id.haken_l: listener.getCommands(3);
                break;
            case R.id.haken_r: listener.getCommands(4);
                break;
            case R.id.aufwärtshaken_l: listener.getCommands(5);
                break;
            case R.id.aufwärtshaken_r: listener.getCommands(6);
                break;
            case R.id.block: listener.getCommands(7);
                break;
            case R.id.cross_l: listener.getCommands(8);
                break;
            case R.id.cross_r: listener.getCommands(9);
                break;
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }
}
