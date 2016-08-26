package com.noethlich.tobias.mcrobektrainingsplaner;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by Tobias on 09.08.2016.
 */
public class CustomOnClickListener implements View.OnClickListener {

    private final int REQUEST_ENABLE_BT = 1;
    private final int position;
    Context context;
    DatabaseHandler db;
    Button button;
    ArrayAdapter mArrayAdapter;

    public CustomOnClickListener(int position, Context cont, Button btn) {
        this.position = position;
        this.db = new DatabaseHandler(cont);
        this.context = cont;
        this.button = btn;
    }

    public void onClick(View v) {
        //Toast.makeText(context, db.getTraining((Integer) button.getTag(R.id.training_id))._combo.toString(), Toast.LENGTH_SHORT).show();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(context, "Ihr Gerät unterstützt leider kein Bluetooth,", Toast.LENGTH_LONG);
        } else if (!mBluetoothAdapter.isEnabled()) {
            Intent discoverableIntent = new
                    Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            context.startActivity(discoverableIntent);


        }
        else if(mBluetoothAdapter.isEnabled()){
            mArrayAdapter = new ArrayAdapter(context,android.R.layout.select_dialog_singlechoice);
            mBluetoothAdapter.startDiscovery();
            // Create a BroadcastReceiver for ACTION_FOUND
             final BroadcastReceiver mReceiver = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    // When discovery finds a device
                    if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                        // Get the BluetoothDevice object from the Intent
                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                        // Add the name and address to an array adapter to show in a ListView
                        mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                        showDialog();
                    }
                }
            };
            // Register the BroadcastReceiver
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            context.registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        }
    }

    public void showDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle("Wähle bitte MC RobeK aus:");

        builderSingle.setNegativeButton(
                "cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mArrayAdapter.clear();
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                mArrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = mArrayAdapter.getItem(which).toString();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(
                                context);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        mArrayAdapter.clear();
                                        dialog.dismiss();
                                    }
                                });
                        builderInner.show();
                    }
                });
        builderSingle.show();
    }
}

