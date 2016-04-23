package sse.tongji.localization;

import android.util.Log;

import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tanjingru on 4/23/16.
 */
public class RecordTask extends TimerTask {
    private RecordFactory factory = new RecordFactory();

    @Override
    public void run() {
        Log.d("time", "3123");
        Realm realm = Realm.getInstance(MainActivity.realmConfig);
//        Record record = factory.collectRecord();
        Record record = new Record();
        record.setTimeStamp("31321");
        realm.beginTransaction();
        Record recordRealm = realm.copyToRealm(record);
        realm.commitTransaction();
    }
}
