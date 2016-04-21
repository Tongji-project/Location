package sse.tongji.localization;

import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

/**
 * Created by 13987 on 2016/4/21.
 */
public class InfomationManager {
    static private TelephonyManager telephonyManager;

    static private GsmCellLocation cellLocation;

    static private AppLocationService appLocationService;

    public static void setAppLocationService(AppLocationService appLocationService) {
        InfomationManager.appLocationService = appLocationService;
    }

    static void setRecord(){

        Long tsLong = System.currentTimeMillis()/1000;
        String timeStamp = tsLong.toString();
        Record.setTimeStamp(timeStamp);

        String deviceId =telephonyManager.getDeviceId();
        Record.setDeviceId(deviceId);

        String phoneModel = android.os.Build.MODEL;
        Device.setPhoneModel(phoneModel);

        int cid = cellLocation.getCid();
        int lac = cellLocation.getLac();
        Record.setBass(String.valueOf(cid));
        Record.setLac(String.valueOf(lac));

        String networkOperator = telephonyManager.getNetworkOperator();
        if (TextUtils.isEmpty(networkOperator) == false) {
            int mcc = Integer.parseInt(networkOperator.substring(0, 3));
            int mnc = Integer.parseInt(networkOperator.substring(3));
            System.out.println("mcc " +mcc);
            System.out.println("mnc " +mnc);
            Device.setMcc(String.valueOf(mcc));
            Device.setMnc(String.valueOf(mnc));
        }

        Location GpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (GpsLocation != null) {

            double latitude = GpsLocation.getLatitude();
            double longitude = GpsLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
            Record.setGLatitude(String .valueOf(latitude));
            Record.setGLongitude(String .valueOf(longitude));
        }

        Location netLocation = appLocationService.getLocation(LocationManager.NETWORK_PROVIDER);

        if (netLocation != null) {
            double latitude = netLocation.getLatitude();
            double longitude = netLocation.getLongitude();
            System.out.println("latitude " +latitude);
            System.out.println("longitude " +longitude);
            Record.setNLatitude(String .valueOf(latitude));
            Record.setNLongitude(String .valueOf(longitude));
        }
    }

    public static TelephonyManager getTelephonyManager() {
        return telephonyManager;
    }

    public static void setTelephonyManager(TelephonyManager telephonyManager) {
        InfomationManager.telephonyManager = telephonyManager;
        cellLocation =  (GsmCellLocation) telephonyManager.getCellLocation();

    }
}
