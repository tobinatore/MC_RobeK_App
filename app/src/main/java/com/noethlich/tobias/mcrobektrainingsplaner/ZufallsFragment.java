package com.noethlich.tobias.mcrobektrainingsplaner;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Tobias on 05.08.2016.
 */
public class ZufallsFragment extends Fragment implements View.OnClickListener {

    ArrayList<Integer> excluded = new ArrayList<>();
    ArrayList<Integer> exercise = new ArrayList<>();

    NumberPicker punches;
    CheckBox check_jab, check_haken, check_aufwärtshaken, check_cross, check_block;

    Random randomizer = new Random();

    DatabaseHandler db;

    public ZufallsFragment() {
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

        View v = inflater.inflate(R.layout.fragment_zufalls, container, false);

        check_jab =(CheckBox) v.findViewById(R.id.checkBox_jab);
        check_jab.setOnClickListener(this);

        check_haken =(CheckBox) v.findViewById(R.id.checkBox_haken);
        check_haken.setOnClickListener(this);

        check_aufwärtshaken =(CheckBox) v.findViewById(R.id.checkBox_aufwärtshaken);
        check_aufwärtshaken.setOnClickListener(this);

        check_cross =(CheckBox) v.findViewById(R.id.checkBox_cross);
        check_cross.setOnClickListener(this);

        check_block =(CheckBox) v.findViewById(R.id.checkBox_block);
        check_block.setOnClickListener(this);

        Button lg = (Button) v.findViewById(R.id.button_ok_random);
        lg.setOnClickListener(this);

        punches = (NumberPicker) v.findViewById(R.id.numberPicker);
        punches.setMinValue(1);
        punches.setMaxValue(19);
        punches.setWrapSelectorWheel(false);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok_random:

                getCheckedBoxes();

                int[] excl = new int[excluded.size()];
               for(int k = 0; k < excluded.size(); k++){
                   excl[k] = excluded.get(k);

               }

                excluded.clear();

                for (int i = 0; i < punches.getValue(); i++ ) {
                    exercise.add(generateList(randomizer, 1, 9, excl));
                }

                saveExercise();
                break;
        }
    }

    public int generateList(Random rnd, int start, int ende, int... ausschließen){
        int random = start + rnd.nextInt(ende - start + 1 - ausschließen.length);
        for (int ex : ausschließen) {
            if (random < ex) {
                break;
            }
            random++;
        }
        return random;
    }

    public void getCheckedBoxes(){
        if(!check_jab.isChecked()){

            excluded.add(1);
            excluded.add(2);
        }


        if(!check_cross.isChecked()){
            excluded.add(8);
            excluded.add(9);
        }


        if(!check_haken.isChecked()){
            excluded.add(3);
            excluded.add(4);
        }


        if(!check_aufwärtshaken.isChecked()){
            excluded.add(5);
            excluded.add(6);
        }


        if(!check_block.isChecked()){
            excluded.add(7);
        }
    }

    public void saveExercise(){
        SharedPreferences sp = getContext().getSharedPreferences("Training_ID", Activity.MODE_PRIVATE);
        int id = sp.getInt("id", 0);

        if(this.isAdded()){
            db = new DatabaseHandler(getActivity());
        }
        else{
            Snackbar.make(getView(),"Keinen Context gefunden...",Snackbar.LENGTH_LONG);
        }

        long combo = exercise.get(0);
        for(int i = 1; i < exercise.size(); i++) {
            combo *= 10;
            combo += exercise.get(i);

        }

        db.addTraining(new Training(id, "Zufallstraining",combo));
        exercise.clear();


        id += 1;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", id);
        editor.apply();
    }


}



