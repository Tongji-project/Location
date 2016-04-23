package sse.tongji.localization;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by 13987 on 2016/4/21.
 */
public class Device extends RealmObject{
    private String DeviceId;
    private String mcc;
    private String mnc;
    private String PhoneModel;
    private String AndroidVersion;

    public Device() {
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public String getAndroidVersion() {
        return AndroidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        AndroidVersion = androidVersion;
    }
}
