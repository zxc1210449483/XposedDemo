package lyh.cn.xposedhooktest;

import android.app.Application;
import android.content.Context;

/**
 * Created by lyh on 2018/9/7.
 */

public class App extends Application {

    public static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationContext = getApplicationContext();
    }
}
