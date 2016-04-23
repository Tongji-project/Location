package sse.tongji.localization;

/**
 * Created by tanjingru on 4/22/16.
 */

public class RecordFactory {



    public RecordFactory() {

    }

    public Record collectRecord(){
        Record record = new Record();
        record.setBass(LocationManager.getBass());
        record.setBatteryUseage(LocationManager.getBatteryUseage());
        record.setDeviceId(LocationManager.getDeviceId());
        record.setGLatitude(LocationManager.getGLocation().getLatitude());
        record.setGLongitude(LocationManager.getGLocation().getLongitude());
        record.setLac(LocationManager.getLac());
        record.setNLatitude(LocationManager.getNLocation().getLatitude());
        record.setNLongitude(LocationManager.getNLocation().getLongitude());
        Long tsLong = System.currentTimeMillis()/1000;
        String timeStamp = tsLong.toString();
        record.setTimeStamp(timeStamp);
        return record;
    }





}
