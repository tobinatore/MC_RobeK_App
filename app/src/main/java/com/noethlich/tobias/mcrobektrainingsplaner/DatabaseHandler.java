package com.noethlich.tobias.mcrobektrainingsplaner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.noethlich.tobias.mcrobektrainingsplaner.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 06.08.2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "trainingManager";

    // Contacts table name
    private static final String TABLE_TRAININGS = "trainings";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SK = "schlagkombo";

    Context con;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TRAININGS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SK + " LONG" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAININGS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Training hinzufügen
    void addTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, training.getID());
        values.put(KEY_NAME, training.getName()); // Name des Trainings
        values.put(KEY_SK, training.getKombo()); // Die gespeicherten Schläge

        //neue Reihe anlegen
        db.insert(TABLE_TRAININGS, null, values);
        db.close(); // Verbindung schließen
    }

    // Training abrufen
    Training getTraining(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Training training;

        Cursor cursor = db.query(TABLE_TRAININGS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SK}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){

         training = new Training(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getLong(2));
            cursor.close();
        }
        else{
            training = null;
            //Toast.makeText(con,"Training " + id + " nicht gefunden", Toast.LENGTH_SHORT).show();
        }

        // return training
        return training;
    }

    // all Trainings abrufen
    public List<Training> getAllTrainings() {
        List<Training> trainingList = new ArrayList<Training>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRAININGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // alle Reihen zur Liste hinzufügen
        if (cursor.moveToFirst()) {
            do {
                Training training = new Training();
                training.setID(Integer.parseInt(cursor.getString(0)));
                training.setName(cursor.getString(1));
                training.setKombo(cursor.getLong(2));
                // training zur Liste hinzufügen
                trainingList.add(training);
            } while (cursor.moveToNext());
        }


        return trainingList;
    }

    // einzelnes Training bearbeiten
    public int updateTraining(Training training) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, training.getName());
        values.put(KEY_SK, training.getKombo());

        // Reihe aktualisieren
        return db.update(TABLE_TRAININGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(training.getID()) });
    }

    // Training löschen
    public void deleteTraining(Integer id) {
       // int id = training.getID();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TRAININGS, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    // Gesamtzahl der gespeicherten Trainings
    public int getTrainingCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TRAININGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

}


