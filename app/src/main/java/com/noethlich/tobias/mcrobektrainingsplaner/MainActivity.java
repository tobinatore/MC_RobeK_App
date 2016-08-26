package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Interface {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";

    ArrayList<Integer> orders = new ArrayList<>();
    ArrayList<Integer> deleted = new ArrayList<>();
    ArrayList<Integer> ids = new ArrayList<>();
    ArrayList<Boolean> activatedButtons = new ArrayList<>();

    String name;
    MenuItem finished, delete;
    DatabaseHandler db = new DatabaseHandler(this);

    ButtonCheck bc = new ButtonCheck();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkFirstRun();
        name = setName();

        if(name == null){
            Intent i = new Intent(this, GetNameActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Fragment frag = fetchStartupFragment();
        if(frag != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainFrame, frag).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates the menu; this adds items to the action bar if it is present.
        TextView name_text = (TextView) findViewById(R.id.name_text);
        if(name != null) {
            name_text.setText("Hallo " + name + "!");
        }
        getMenuInflater().inflate(R.menu.main, menu);
        finished = menu.findItem(R.id.action_finish);
        delete = menu.findItem(R.id.action_delete);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handles action bar item clicks here
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_finish) {
            final EditText txtUrl = new EditText(this);

            // Sets the hint to a randomly generated example
            txtUrl.setHint(generateExample());


            new AlertDialog.Builder(this)
                    .setTitle("Fertig?")
                    .setMessage("Gib dem Training jetzt noch einen Namen:")
                    .setView(txtUrl)
                    .setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String name = txtUrl.getText().toString();
                            trainingSpeichern(name);
                        }
                    })
                    .setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
        }
        else if (id == R.id.action_delete){
            tryToDelete();
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handles navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_training:

                fragment = new TrainingFragment();
                finished.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
                delete.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
            case R.id.nav_wahl:

                fragment = new WahlFragment();
                finished.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                delete.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

                break;
            case R.id.nav_random:

                fragment = new ZufallsFragment();
                finished.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                delete.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
            case R.id.nav_settings:

                fragment = new EinstellungenFragment();
                finished.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);

                break;
            case R.id.nav_share:

                fragment = new CodeFragment();

                finished.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                delete.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.mainFrame, fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getCommands(Integer command){
        orders.add(command);
    }

    private void checkFirstRun() {

        final String PREFS_NAME = "MyPrefsFile";
        final String PREF_VERSION_CODE_KEY = "version_code";
        final int DOESNT_EXIST = -1;


        // Gets current version code
        int currentVersionCode = 0;
        try {
            currentVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            // handles exception
            e.printStackTrace();
            return;
        }

        // Gets saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Checks for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {

            Intent intent = new Intent(this, GetNameActivity.class);
            startActivity(intent);

        } else if (currentVersionCode > savedVersionCode) {

            // TODO Message showing new features

        }

        // Updates the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).commit();

    }

    public String setName(){
        SharedPreferences settings;
        String text;
        settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;


    }

    public void trainingSpeichern(final String name){


        SharedPreferences sp = getSharedPreferences("Training_ID", Activity.MODE_PRIVATE);
        int id = sp.getInt("id", 0);



        long combo = orders.get(0);
        for(int i = 1; i < orders.size(); i++) {
            combo *= 10;
            combo += orders.get(i);

        }

        db.addTraining(new Training(id, name,combo));
        orders.clear();


        id += 1;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", id);
        editor.apply();
        }

    public String generateExample(){
        String[] punches = {"Jab", "Haken", "Aufwärtshaken", "Block", "Cross"};
        String[] description = {"Hartes", "Lohnendes", "Starkes", "Anstrengendes", "Spannendes"};
        String[] activity = {"Training", "Workout", "Programm", "Trimmen", "Üben"};

        return description[new Random().nextInt(description.length)] + " " +punches[new Random().nextInt(punches.length)]
                + "-" + activity[new Random().nextInt(activity.length)];

    }

    public void tryToDelete(){

        final GridView grid = (GridView) findViewById(R.id.gridView1);
        activatedButtons = bc.checkAllButtonsForActive((GridView) findViewById(R.id.gridView1));
        ids = bc.getAllIDs(grid);

        if(activatedButtons.size() > 0){


        new AlertDialog.Builder(this)
                .setTitle("Bestätigen")
                .setMessage("Ausgewählte Trainings werden unwiderruflich gelöscht!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SharedPreferences sp = getSharedPreferences("Training_ID", Activity.MODE_PRIVATE);
                        int id = sp.getInt("id", 0);

                        for(int i = 0; i < activatedButtons.size(); i++){

                            if(activatedButtons.get(i)){
                                Training training = db.getTraining(i);
                                db.deleteTraining(ids.get(i));
                                id -= 1;
                                deleted.add(i);
                            }
                       }

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("id", id);
                        editor.apply();


                        Fragment frag = new WahlFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.mainFrame, frag).commit();

                        GridAdapter adapter = new GridAdapter(MainActivity.this);
                        adapter.updateGrid();
                        grid.invalidateViews();


                    }
                })
                .setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
        }
        else{
            new AlertDialog.Builder(this)
                    .setTitle("Fehler")
                    .setMessage("Keine Trainings ausgewählt!")
                    .setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
        }

    }

    public Fragment fetchStartupFragment(){
        Fragment fragment = null;
        SharedPreferences sharedPreferences = getSharedPreferences("STARTUP_ACTIVITY", Activity.MODE_PRIVATE);
        String activity = sharedPreferences.getString("activity", "WelcomeActivity");
        int pos = 0;

        switch(activity){
            case "Standard": fragment = new WelcomeFragment();
                break;
            case "Training erstellen": fragment = new TrainingFragment();
                pos = 1;
                break;
            case "Training auswählen": fragment = new WahlFragment();
                pos = 2;
                break;
            case "Zufallstraining": fragment = new ZufallsFragment();
                pos = 3;
                break;
            case "Einstellungen": fragment = new EinstellungenFragment();
                pos = 4;
                break;
            case "Code eingeben": fragment = new CodeFragment();
                pos = 5;
                break;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("position", pos);
        editor.apply();


        return fragment;
    }
}
