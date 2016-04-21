package sse.tongji.localization;

/**
 * Created by 13987 on 2016/4/21.
 */
public class Record {
    public static String timeStamp;
    public static String deviceId;
    public static String lac;
    public static String bass;
    public static String GLatitude;
    public static String GLongitude;
    public static String NLatitude;
    public static String NLongitude;
    public static String batteryUseage;

    public static String getTimeStamp() {
        return timeStamp;
    }

    public static void setTimeStamp(String timeStamp) {
        Record.timeStamp = timeStamp;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static void setDeviceId(String deviceId) {
        Record.deviceId = deviceId;
    }

    public static String getLac() {
        return lac;
    }

    public static void setLac(String lac) {
        Record.lac = lac;
    }

    public static String getBass() {
        return bass;
    }

    public static void setBass(String bass) {
        Record.bass = bass;
    }

    public static String getGLatitude() {
        return GLatitude;
    }

    public static void setGLatitude(String GLatitude) {
        Record.GLatitude = GLatitude;
    }

    public static String getGLongitude() {
        return GLongitude;
    }

    public static void setGLongitude(String GLongitude) {
        Record.GLongitude = GLongitude;
    }

    public static String getNLatitude() {
        return NLatitude;
    }

    public static void setNLatitude(String NLatitude) {
        Record.NLatitude = NLatitude;
    }

    public static String getNLongitude() {
        return NLongitude;
    }

    public static void setNLongitude(String NLongitude) {
        Record.NLongitude = NLongitude;
    }

    public static String getBatteryUseage() {
        return batteryUseage;
    }

    public static void setBatteryUseage(String batteryUseage) {
        Record.batteryUseage = batteryUseage;
    }

    public Record() {
    }
}
