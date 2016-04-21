package sse.tongji.localization;

/**
 * Created by 13987 on 2016/4/21.
 */
public class Device {
    public static String DeviceId;
    public static String mcc;
    public static String mnc;
    public static String PhoneModel;
    public static String AndroidVersion;

    public Device() {
    }

    public static String getDeviceId() {
        return DeviceId;
    }

    public static void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public static String getMcc() {
        return mcc;
    }

    public static void setMcc(String mcc) {
        Device.mcc = mcc;
    }

    public static String getMnc() {
        return mnc;
    }

    public static void setMnc(String mnc) {
        Device.mnc = mnc;
    }

    public static String getPhoneModel() {
        return PhoneModel;
    }

    public static void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public static String getAndroidVersion() {
        return AndroidVersion;
    }

    public static void setAndroidVersion(String androidVersion) {
        AndroidVersion = androidVersion;
    }
}
