package sse.tongji.localization;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.xutils.x;

import com.google.gson.Gson;

import org.xutils.http.RequestParams;

import java.util.Timer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Button btnGPSShowLocation;
    Button btnNWShowLocation;
    Button uploadButton;
    Button startApp;
    boolean isWifiConn;
    static public RealmConfiguration realmConfig;
    private AppLocationService appLocationService;

    public boolean isWifiConn() {
        isWifiConn=isWifiConnected(this);
        return isWifiConn;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults){
    }

    public void requetPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        0);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        4);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        4);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        5);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        5);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        1);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE},
                        1);

            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        2);
            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        2);

            }
        }

    }


    private void initLocation(){
        //Register a broadcast type
        registerReceiver(new BatteryBroadcastReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        sse.tongji.localization.LocationManager.setTelephonyManager(telephonyManager);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            public void onCallForwardingIndicatorChanged(boolean cfi) {}
            public void onCallStateChanged(int state, String incomingNumber) {}
            public void onCellLocationChanged(CellLocation location) {}
            public void onDataActivity(int direction) {}
            public void onDataConnectionStateChanged(int state) {}
            public void onMessageWaitingIndicatorChanged(boolean mwi) {}
            public void onServiceStateChanged(ServiceState serviceState) {}

            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                super.onSignalStrengthsChanged(signalStrength);
                int signal = signalStrength.getGsmSignalStrength();
                signal = (2 * signal) - 113; // -> dBm
                System.out.println("signal" +signal);
                sse.tongji.localization.LocationManager.setSignalStrength(String.valueOf(signal));
            }
        };

        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
                        PhoneStateListener.LISTEN_CALL_STATE |
                        PhoneStateListener.LISTEN_CELL_LOCATION |
                        PhoneStateListener.LISTEN_DATA_ACTIVITY |
                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
                        PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
                        PhoneStateListener.LISTEN_SERVICE_STATE |
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);

        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        sse.tongji.localization.LocationManager.setCellLocation(cellLocation);

        Long tsLong = System.currentTimeMillis()/1000;
        String timeStamp = tsLong.toString();
        System.out.println("timeStamp " +timeStamp);


        String deviceId =telephonyManager.getDeviceId();
        System.out.println("device_id " +deviceId);

        String  IMSI;
        IMSI = telephonyManager.getSubscriberId();
        System.out.println("IMSI :" +IMSI);

        // Device model
        String phoneModel = android.os.Build.MODEL;
        // Android version
        String androidVersion = android.os.Build.VERSION.RELEASE;
        //info
        System.out.println("PhoneModel :" +phoneModel);

        System.out.println("AndroidVersion :" +androidVersion);

//        int cid = cellLocation.getCid();
//        int lac = cellLocation.getLac();
//        System.out.println("cid " +cid);
//        System.out.println("lac " +lac);
        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) == false) {
            int mcc = Integer.parseInt(networkOperator.substring(0, 3));
            int mnc = Integer.parseInt(networkOperator.substring(3));
            System.out.println("mcc " +mcc);
            System.out.println("mnc " +mnc);
        }



        appLocationService = new AppLocationService(
                MainActivity.this);

        sse.tongji.localization.LocationManager.setAppLocationService(appLocationService);

        Location GpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (GpsLocation != null) {

            double latitude = GpsLocation.getLatitude();
            double longitude = GpsLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
        }


        Location netLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (netLocation != null) {
            double latitude = netLocation.getLatitude();
            double longitude = netLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
        }

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        realmConfig = new RealmConfiguration.Builder(MainActivity.this).build();

//        isWifiConn();
//        if(isWifiConn){
//            System.out.println("connection: "+isWifiConn);
//        }
//        else{
//            System.out.println("unconnection: "+isWifiConn);
//        }
//
//
//
//        //Register a broadcast type
//        registerReceiver(new BatteryBroadcastReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//
//        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//        sse.tongji.localization.LocationManager.setTelephonyManager(telephonyManager);
//
//        PhoneStateListener phoneStateListener = new PhoneStateListener() {
//            public void onCallForwardingIndicatorChanged(boolean cfi) {}
//            public void onCallStateChanged(int state, String incomingNumber) {}
//            public void onCellLocationChanged(CellLocation location) {}
//            public void onDataActivity(int direction) {}
//            public void onDataConnectionStateChanged(int state) {}
//            public void onMessageWaitingIndicatorChanged(boolean mwi) {}
//            public void onServiceStateChanged(ServiceState serviceState) {}
//
//            @Override
//            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
//                super.onSignalStrengthsChanged(signalStrength);
//                int signal = signalStrength.getGsmSignalStrength();
//                signal = (2 * signal) - 113; // -> dBm
//                System.out.println("signal" +signal);
//                sse.tongji.localization.LocationManager.setSignalStrength(String.valueOf(signal));
//            }
//        };
//
//        telephonyManager.listen(phoneStateListener,
//                PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR |
//                        PhoneStateListener.LISTEN_CALL_STATE |
//                        PhoneStateListener.LISTEN_CELL_LOCATION |
//                        PhoneStateListener.LISTEN_DATA_ACTIVITY |
//                        PhoneStateListener.LISTEN_DATA_CONNECTION_STATE |
//                        PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR |
//                        PhoneStateListener.LISTEN_SERVICE_STATE |
//                        PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
//
//        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
//        sse.tongji.localization.LocationManager.setCellLocation(cellLocation);
//
//        Long tsLong = System.currentTimeMillis()/1000;
//        String timeStamp = tsLong.toString();
//        System.out.println("timeStamp " +timeStamp);
//
//
//        String deviceId =telephonyManager.getDeviceId();
//        System.out.println("device_id " +deviceId);
//
//        String  IMSI;
//        IMSI = telephonyManager.getSubscriberId();
//        System.out.println("IMSI :" +IMSI);
//
//        // Device model
//        String phoneModel = android.os.Build.MODEL;
//        // Android version
//        String androidVersion = android.os.Build.VERSION.RELEASE;
//        //info
//        System.out.println("PhoneModel :" +phoneModel);
//
//        System.out.println("AndroidVersion :" +androidVersion);
//
////        int cid = cellLocation.getCid();
////        int lac = cellLocation.getLac();
////        System.out.println("cid " +cid);
////        System.out.println("lac " +lac);
//        String networkOperator = telephonyManager.getNetworkOperator();
//        if (TextUtils.isEmpty(networkOperator) == false) {
//            int mcc = Integer.parseInt(networkOperator.substring(0, 3));
//            int mnc = Integer.parseInt(networkOperator.substring(3));
//            System.out.println("mcc " +mcc);
//            System.out.println("mnc " +mnc);
//        }
//
//
//
//        appLocationService = new AppLocationService(
//                MainActivity.this);
//
//        sse.tongji.localization.LocationManager.setAppLocationService(appLocationService);
//
//        Location GpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
//        if (GpsLocation != null) {
//
//            double latitude = GpsLocation.getLatitude();
//            double longitude = GpsLocation.getLongitude();
//            System.out.println("latitude " +latitude);
//            System.out.println("longitude " +longitude);
//        }
//
//
//        Location netLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);
//
//        if (netLocation != null) {
//            double latitude = netLocation.getLatitude();
//            double longitude = netLocation.getLongitude();
//            System.out.println("latitude " +latitude);
//            System.out.println("longitude " +longitude);
//        }
//
//
//
        btnGPSShowLocation = (Button) findViewById(R.id.btnGPSShowLocation);

        btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location gpsLocation = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                if (gpsLocation != null) {
                    double latitude = gpsLocation.getLatitude();
                    double longitude = gpsLocation.getLongitude();
                    Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (GPS): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    showSettingsAlert("GPS");
                }

            }
        });

        btnNWShowLocation = (Button) findViewById(R.id.btnNWShowLocation);
        btnNWShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location nwLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

                if (nwLocation != null) {
                    double latitude = nwLocation.getLatitude();
                    double longitude = nwLocation.getLongitude();
                    Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (NW): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude,
                            Toast.LENGTH_LONG).show();
                } else {
                    showSettingsAlert("NETWORK");
                }

            }
        });

        uploadButton = (Button) findViewById(R.id.upload);
        uploadButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                upload();
            }
        });

        startApp = (Button) findViewById(R.id.start);
        startApp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.INTERNET)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.READ_PHONE_STATE)
                                == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_NETWORK_STATE)
                                == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED )
                {Log.d("permission", "ok");
                    initLocation();
                    Timer timer = new Timer();
                    timer.schedule(new RecordTask(), 0, 1000);
                }

                else requetPermission();
            }
        });
//
//        RequestParams requestParams = new RequestParams("http://tztztztztz.org:3000/device");
//        requestParams.addBodyParameter("", "{\"hello\":\"heheda\"}");
//        //requestParams.setHeader("Content-Type", "application/json");
//        HttpUtil.post(requestParams, handler, 0, 1);

    }

    private void upload(){
        Realm realm = Realm.getInstance(realmConfig);
        RealmQuery<Record> query = realm.where(Record.class);
        RealmResults<Record> records = query.findAll();
        Gson gson = new Gson();
        String string = gson.toJson(records);
        Log.d("Alldata", string);
//        RequestParams requestParams = new RequestParams("http://tztztztztz.org:3000/records");
//        requestParams.addBodyParameter("", string);
//        //requestParams.setHeader("Content-Type", "application/json");
//        HttpUtil.post(requestParams, handler, 0, 1);
    }



    public static boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }

        return false ;
    }
    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        MainActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**accept Power change broadcast*/
    class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){

                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                int curPower = (level * 100 / scale);
                System.out.println("curPower :"+curPower);
                sse.tongji.localization.LocationManager.setBatteryUsage(String.valueOf(curPower));
            }
        }
    }

    private final Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 0:
                    handleData(msg);
                    System.out.println("success");
                    break;
                case 1:
                    System.out.println("1");
                    break;
                default:

                    break;
            }
        }
    };

    private void handleData(Message message){
        Bundle b = message.getData();
        String result =  b.getString("response");
        Log.d("result", result);
        Realm realm = Realm.getInstance(realmConfig);
        RealmQuery<Record> query = realm.where(Record.class);
        RealmResults<Record> records = query.findAll();
        realm.beginTransaction();
        records.clear();
        realm.commitTransaction();
    }


}

