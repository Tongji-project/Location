package sse.tongji.localization;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * @Package tj.sse.wow.Util
 * @Description http请求
 * @author 黄日辉
 */
public class HttpUtil {

    public static void post(final RequestParams requestParams,final Handler handler,final int suc,final int error) {

        TDUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    x.http().post(requestParams, new Callback.CacheCallback<String>() {

                        @Override
                        public void onSuccess(String s) {

                            Message message = handler.obtainMessage();
                            Bundle b = new Bundle();
                            b.putString("response", s);
                            message.what = suc;
                            message.setData(b);
                            message.sendToTarget();


                        }

                        @Override
                        public void onError(Throwable throwable, boolean b) {
                            handler.sendEmptyMessage(error);
                        }

                        @Override
                        public void onCancelled(CancelledException e) {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public boolean onCache(String s) {

                            return false;
                        }
                    });


                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        });
    }


    public static void get(final RequestParams requestParams,final Handler handler,final int suc,final int error){

        TDUtils.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Message message = handler.obtainMessage();
                            message.what = suc;
                            Bundle b = new Bundle();
                            b.putString("response", s);
                            message.setData(b);
                            message.sendToTarget();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            handler.sendEmptyMessage(error);
                        }

                        @Override
                        public void onCancelled(CancelledException cex) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

    }





}
