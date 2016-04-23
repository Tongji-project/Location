package sse.tongji.localization;

/**
 * Created by 13987 on 2016/4/23.
 */
public class DeviceFactory {
    public DeviceFactory() {

    }
    public Device collectRecord(){
        Device device=new Device();

        device.setDeviceId(LocationManager.getDeviceId());
        device.setAndroidVersion(android.os.Build.VERSION.RELEASE);
        device.setMnc(LocationManager.getMnc());
        device.setMcc(LocationManager.getMcc());
        device.setPhoneModel(android.os.Build.MODEL);

        return device;
    }
}
