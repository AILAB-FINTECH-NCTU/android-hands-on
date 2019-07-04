package com.johnny12150.becaon;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private Button BT_Search;
    private TextView TV1;
    private BluetoothAdapter mBluetoothAdapter;
    private List<String> bluetoothdeviceslist = new ArrayList<String>();
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private String ID_target = "BR522983";
    double dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BT_Search = (Button) findViewById(R.id.button);
        TV1 = (TextView) findViewById(R.id.textView);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBluetoothPermission();
        SearchBluetooth();
        BT_Search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(mBluetoothAdapter.isDiscovering()){
                    mBluetoothAdapter.cancelDiscovery();
                }
                mBluetoothAdapter.startDiscovery();
            }
        });

    }

    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M) {
            // Android M Permission check
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    public void SearchBluetooth(){
        if(mBluetoothAdapter == null){
            Toast.makeText(this,"not find the bluetooth",Toast.LENGTH_SHORT).show();
            finish();
        }
        if(!mBluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,1);
            Set<BluetoothDevice> myDevices = mBluetoothAdapter.getBondedDevices();
            if(myDevices.size() > 0) {
                for(BluetoothDevice device : myDevices)
                    bluetoothdeviceslist.add(device.getName()+":"+device.getAddress()+"\n");
            }
        }
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(myreceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(myreceiver, filter);
    }

    private final BroadcastReceiver myreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到的廣播類型
            String action = intent.getAction();
            //發現設備的廣播
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //從intent中獲取設備
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                double txPower = -59;
                double ratio = rssi*1.0/txPower;
                if (ratio < 1.0) {
                    dis = Math.pow(ratio,10);
                }
                else {
                    dis =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
                }

                try{
                    if (device.getName().equals(ID_target)){
                        TV1.setText(device.getName().toString()+"   "+Double.toString(dis));
                    }
                }
                catch(Exception e){
                }
            }
        }
    };

}
