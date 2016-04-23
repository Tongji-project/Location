package sse.tongji.localization;

import android.app.Application;
import android.app.Application;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
/**
 * Created by tanjingru on 4/22/16.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {

        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

    }
}
