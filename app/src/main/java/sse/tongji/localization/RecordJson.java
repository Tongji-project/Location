package sse.tongji.localization;

/**
 * Created by 13987 on 2016/4/23.
 */
public class RecordJson {
    private String timeStamp;
    private String deviceId;
    private String lac;
    private String bass;
    private String GLatitude;
    private String GLongitude;
    private String NLatitude;
    private String NLongitude;
    private String batteryUseage;

    public RecordJson() {
    }

    public RecordJson(String timeStamp, String deviceId, String lac, String bass, String GLatitude, String GLongitude, String NLatitude, String NLongitude, String batteryUseage) {
        this.timeStamp = timeStamp;
        this.deviceId = deviceId;
        this.lac = lac;
        this.bass = bass;
        this.GLatitude = GLatitude;
        this.GLongitude = GLongitude;
        this.NLatitude = NLatitude;
        this.NLongitude = NLongitude;
        this.batteryUseage = batteryUseage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getBass() {
        return bass;
    }

    public void setBass(String bass) {
        this.bass = bass;
    }

    public String getGLatitude() {
        return GLatitude;
    }

    public void setGLatitude(String GLatitude) {
        this.GLatitude = GLatitude;
    }

    public String getGLongitude() {
        return GLongitude;
    }

    public void setGLongitude(String GLongitude) {
        this.GLongitude = GLongitude;
    }

    public String getNLatitude() {
        return NLatitude;
    }

    public void setNLatitude(String NLatitude) {
        this.NLatitude = NLatitude;
    }

    public String getNLongitude() {
        return NLongitude;
    }

    public void setNLongitude(String NLongitude) {
        this.NLongitude = NLongitude;
    }

    public String getBatteryUseage() {
        return batteryUseage;
    }

    public void setBatteryUseage(String batteryUseage) {
        this.batteryUseage = batteryUseage;
    }
}
