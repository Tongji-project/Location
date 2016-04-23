package sse.tongji.localization;

import android.location.Location;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

/**
 * Created by tanjingru on 4/22/16.
 */
public class LocationManager {

    static private TelephonyManager telephonyManager;

    static private GsmCellLocation cellLocation;

    static private AppLocationService appLocationService;

    public static void setAppLocationService(AppLocationService appLocationService) {
        LocationManager.appLocationService = appLocationService;
    }

    public static void setCellLocation(GsmCellLocation cellLocation) {
        LocationManager.cellLocation = cellLocation;
    }

    public static void setTelephonyManager(TelephonyManager telephonyManager) {
        LocationManager.telephonyManager = telephonyManager;
    }


    static private String batteryUsage = "";

    static private String mcc="";
    static private String deviceId="";
    static private String mnc="";

    static private String signalStrength = "";

    public static String getSignalStrength() {
        return signalStrength;
    }

    public static void setSignalStrength(String signalStrength) {
        LocationManager.signalStrength = signalStrength;
    }

    public static void setBatteryUsage(String batteryUsage) {
        LocationManager.batteryUsage = batteryUsage;
    }




    static public String getDeviceId(){
        return telephonyManager.getDeviceId();
    }

    static public void setDeviceId(String deviceId){
        LocationManager.deviceId=deviceId;
    }

    static public void setMcc(String mcc){
        LocationManager.mcc=mcc;
    }

    static public void setMnc(String mnc){
        LocationManager.mnc=mnc;
    }


    static public String getLac(){
        return String.valueOf(cellLocation.getLac());
    }
    static public String getMnc(){
        String networkOperator = telephonyManager.getNetworkOperator();
        return String.valueOf(Integer.parseInt(networkOperator.substring(3)));}
    static public String getMcc(){
        String networkOperator = telephonyManager.getNetworkOperator();
        return String.valueOf(Integer.parseInt(networkOperator.substring(0, 3)));
    }
    static public String getBass(){
        return String.valueOf(cellLocation.getCid());
    }




    static public sse.tongji.localization.Location getGLocation(){
        String  latitude = "";
        String  longitude = "";
        Location GpsLocation = appLocationService.getLocation(android.location.LocationManager.GPS_PROVIDER);
        if (GpsLocation != null) {

            latitude = String.valueOf(GpsLocation.getLatitude());
            longitude = String.valueOf(GpsLocation.getLongitude());
        }
        return new sse.tongji.localization.Location(longitude, latitude);
    }

    static  public sse.tongji.localization.Location getNLocation(){

        Location netLocation = appLocationService.getLocation(android.location.LocationManager.NETWORK_PROVIDER);
        String  latitude = "";
        String  longitude = "";
        if (netLocation != null) {
             latitude = String.valueOf(netLocation.getLatitude());
             longitude = String.valueOf(netLocation.getLongitude());

        }
        return new sse.tongji.localization.Location(longitude, latitude);
    }

    static public String getBatteryUseage(){
        return batteryUsage;
    }


}
