package com.noethlich.tobias.mcrobektrainingsplaner;

/**
 * Created by Tobias on 06.08.2016.
 */

public class Training {

    //private variables
    int _id;
    String _name;
    Long _combo;

    // Empty constructor
    public Training(){

    }

    // constructor
    public Training(int id, String name, Long punchingCombo){
        this._id = id;
        this._name = name;
        this._combo = punchingCombo;
    }

    // constructor
    public Training(String name, Long punchingCombo){
        this._name = name;
        this._combo = punchingCombo;
    }

    // getting id
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting combo
    public Long getKombo(){
        return this._combo;
    }

    // setting combo
    public void setKombo(Long schlagkombo){
        this._combo = schlagkombo;
    }
}