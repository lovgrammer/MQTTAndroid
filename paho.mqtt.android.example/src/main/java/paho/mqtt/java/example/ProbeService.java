package paho.mqtt.java.example;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class ProbeService extends Service {

    public static final String TAG = "ProbeService";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        startDozeModeTracking();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    BroadcastReceiver mDozeModeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e(TAG, intent.toString());
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                if (Build.VERSION.SDK_INT >= (Build.VERSION_CODES.M)) {
                    if (pm.isDeviceIdleMode()) {
                        Log.e(TAG, "Device on Doze Mode");
                    } else {
                        Log.e(TAG, "Device on Active Mode");
                    }
                }
            }
        };

    public void startDozeModeTracking() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PowerManager.ACTION_DEVICE_IDLE_MODE_CHANGED);
        getApplicationContext().registerReceiver(mDozeModeReceiver, filter);
    }

}
