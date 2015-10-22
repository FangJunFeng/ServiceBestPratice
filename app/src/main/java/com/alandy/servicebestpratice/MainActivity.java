package com.alandy.servicebestpratice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.alandy.servicebestpratice.servicetest.MyAIDLService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button startService;
    private Button stopService;
    private Button bindService;
    private Button unbindService;
    //private MyService.MyBinder myBinder;
    private MyAIDLService myAIDLService;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            myBinder = (MyService.MyBinder) service;
//            myBinder.startDownload();

            myAIDLService = MyAIDLService.Stub.asInterface(service);
            int result = 0;
            try {
                result = myAIDLService.plus(3, 5);
                String upperStr = myAIDLService.toUpperCase("hello world");
                Log.d(TAG, "result is " + result);
                Log.d(TAG, "upperStr is " + upperStr);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Log.d(TAG, "process id is " + android.os.Process.myPid());


    }

    private void initView() {
        startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(this);
        stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(this);
        bindService = (Button) findViewById(R.id.bind_service);
        bindService.setOnClickListener(this);
        unbindService = (Button) findViewById(R.id.unbind_service);
        unbindService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(MainActivity.this, MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Log.d("MyService", "click Stop Service button");
                Intent stopIntent = new Intent(MainActivity.this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(MainActivity.this, MyService.class);
                bindService(bindIntent,connection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                Log.d("MyService", "click Unbind Service button");
                if (connection != null) {
                    unbindService(connection);
                }
                break;
            default:
                break;
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
