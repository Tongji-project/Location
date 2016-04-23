package sse.tongji.localization;

/**
 * Created by 13987 on 2016/4/23.
 */
public class DeviceJson {
    private String DeviceId;
    private String mcc;
    private String mnc;
    private String PhoneModel;
    private String AndroidVersion;

    public DeviceJson() {
    }
    public DeviceJson(String DeviceId,String mcc,String mnc,String phoneModel,String AndroidVersion){
        this.DeviceId=DeviceId;
        this.mcc=mcc;
        this.mnc=mnc;
        this.PhoneModel=phoneModel;
        this.AndroidVersion=AndroidVersion;
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
