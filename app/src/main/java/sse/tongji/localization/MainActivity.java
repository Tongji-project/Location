package sse.tongji.localization;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    Button btnGPSShowLocation;
    Button btnNWShowLocation;
    boolean isWifiConn;
    AppLocationService appLocationService;

    public boolean isWifiConn() {
        isWifiConn=isWifiConnected(this);
        return isWifiConn;
    }

    String csv = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();

    /////////////////////////////////////////////
    @Override
    public File getFilesDir() {
        return super.getFilesDir();
    }


    ///////
    FileWriter writer;

    File root = Environment.getExternalStorageDirectory();
    File gpxfile = new File(root, "mydata.csv");



//        ///////////////////////
//CSVFile csvFile = new CSVFile(inputStream);
//    List scoreList = csvFile.read();

    private void writeCsvHeader(String h1, String h2, String h3) throws IOException {
        String line = String.format("%s,%s,%s\n", h1,h2,h3);
        writer.write(line);
    }

    private void writeCsvData(float d, float e, float f) throws IOException {
        String line = String.format("%f,%f,%f\n", d, e, f);
        writer.write(line);
    }


    ///////////////////////



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new MyThread()).start();
        System.out.println("successfully???????????????????/");
        isWifiConn();
        if(isWifiConn){
            System.out.println("connection: "+isWifiConn);
        }
        else{
            System.out.println("unconnection: "+isWifiConn);
        }




/////////////////////

        try {
            writer = new FileWriter(gpxfile);
            writeCsvHeader("FirstParam","SecondParam","ThirdParam");
            writeCsvData(0.31f, 5.2f, 7.0f);
            writeCsvData(0.31f,5.2f,7.1f);
            writeCsvData(0.31f,5.2f,7.2f);
            writer.close();
            System.out.println("write successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }



////////////////////////

        //Register a broadcast type
        registerReceiver(new BatteryBroadcastReceiver(), new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //InfomationManager.setTelephonyManager(telephonyManager);

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
                //Record.setBass(String.valueOf(signal));
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

        Long tsLong = System.currentTimeMillis()/1000;
        String timeStamp = tsLong.toString();
        System.out.println("timeStamp " +timeStamp);


        String deviceId =telephonyManager.getDeviceId();
        System.out.println("device_id " +deviceId);
        Device.setDeviceId(deviceId);
        //Record.setDeviceId(deviceId);
        String  IMSI;
        IMSI = telephonyManager.getSubscriberId();
        System.out.println("IMSI :" +IMSI);
        // Device model
        String phoneModel = android.os.Build.MODEL;
        Device.setPhoneModel(phoneModel);
        // Android version
        String androidVersion = android.os.Build.VERSION.RELEASE;
        //info
        System.out.println("PhoneModel :" +phoneModel);

        System.out.println("AndroidVersion :" +androidVersion);
        Device.setAndroidVersion(androidVersion);

        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();
        System.out.println("cid " +cid);
        System.out.println("lac " +lac);
        //Record.setBass(String.valueOf(cid));
        //Record.setLac(String.valueOf(lac));
        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) == false) {
            int mcc = Integer.parseInt(networkOperator.substring(0, 3));
            int mnc = Integer.parseInt(networkOperator.substring(3));
            System.out.println("mcc " +mcc);
            System.out.println("mnc " +mnc);
            Device.setMcc(String.valueOf(mcc));
            Device.setMnc(String.valueOf(mnc));
        }



        appLocationService = new AppLocationService(
                MainActivity.this);

       // InfomationManager.setAppLocationService(appLocationService);

        /////////////////////


        Location GpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (GpsLocation != null) {

            double latitude = GpsLocation.getLatitude();
            double longitude = GpsLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
           // Record.setGLatitude(String .valueOf(latitude));
           // Record.setGLongitude(String .valueOf(longitude));
        }


        Location netLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (netLocation != null) {
            double latitude = netLocation.getLatitude();
            double longitude = netLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
           // Record.setNLatitude(String .valueOf(latitude));
            //Record.setNLongitude(String .valueOf(longitude));
        }

        ///////////////////

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

//        Timer timer = new Timer();
//        timer.schedule(new RecordTask(), 1000, 1000);
//        File path = getExternalFilesDir(null);
//        File file = new File(path, "asd.txt");
//        FileOutputStream stream;
//
//        try {
//            stream = new FileOutputStream(file);
//            stream.write("text-to-write".getBytes());
//            stream.close();
//        }
//        catch (IOException e){
//        }
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
              //  Record.setBatteryUseage(String .valueOf(curPower));
            }
        }
    }

    //Define a Handler ，Used for processing accepted Message
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            // to do in loop
//

            String text="mydata";//write some thing into csv file.
            appendTextToFile(text);//write to csv file
           // String line = String.format("%f,%f,%f\n", d, e, f);




//            File root2 = Environment.getExternalStorageDirectory();
//            File gpxfile2 = new File(root2, "mydata.csv");
//            try {
//
//                // writer = new FileWriter(gpxfile);
//                File file = new File("mydata.csv");
//                FileWriter filewriter = new FileWriter(file, true);
//                FileOutputStream   fos   =   new FileOutputStream(gpxfile2,true);
//                writeCsvData(0.99f, 9.2f, 7.0f);
//                writeCsvData(9.31f,9.2f,7.1f);
//
//                writeCsvData(9.31f,9.2f,7.2f);
//                writer.close();
//                System.out.println("write successfully");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }



            super.handleMessage(msg);
        }
    };

    //新建一个实现Runnable接口的线程类
    public class MyThread implements Runnable {
        @Override
        public void run() {
            // TO DO Auto-generated method stub
            while (true) {
                try {
                    Thread.sleep(10000);// Thread suspended for 10 seconds, unit millisecond
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);// send message
                } catch (InterruptedException e) {
                    // TO DO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }



    ///////////////////////////////////


    void appendTextToFile( String text){

        File f = new File(root, "mydata.csv");

        if(!f.exists()){
            createFile(f);
        }
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));
            out.println(text);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void createFile(File f){
        File parentDir = f.getParentFile();
        try{
            parentDir.mkdirs();
            f.createNewFile();
        }catch(Exception e){
            e.printStackTrace();
        }
    }




}

