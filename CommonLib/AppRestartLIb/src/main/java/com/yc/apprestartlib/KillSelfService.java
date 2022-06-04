package com.yc.apprestartlib;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.yc.toolutils.AppLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     GitHub : https://github.com/yangchong211/YCCommonLib
 *     email : yangchong211@163.com
 *     time  : 2018/11/9
 *     desc  : 重启APP接口，使用service方式重启实现
 *     revise:
 * </pre>
 */
public class KillSelfService extends Service {


    private String packageName;
    private Handler handler;

    public KillSelfService() {
        handler = new Handler();
        AppLogUtils.d("KillSelfService: ", "new handler");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogUtils.d("KillSelfService: ", "onCreate");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        AppLogUtils.d("KillSelfService: ", "onStartCommand "+startId);
        long stopDelayed = intent.getIntExtra("Delayed", 2000);
        packageName = intent.getStringExtra("PackageName");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AppLogUtils.d("KillSelfService: ", "post delayed restart app : "+packageName);
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(launchIntent);

                //销毁自己
                KillSelfService.this.stopSelf();
            }
        },stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
