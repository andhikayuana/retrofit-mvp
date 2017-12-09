package yuana.kodemetro.com.cargallery;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.qiscus.sdk.Qiscus;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 2/13/17
 */

public class CarApp extends Application {

    private static Context context;

    public static synchronized Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        MultiDex.install(context);

        initQiscusChatSdk();
    }

    private void initQiscusChatSdk() {
        Qiscus.init(this, "hello-bot-stag");
    }

    // TODO: 2/13/17
}
