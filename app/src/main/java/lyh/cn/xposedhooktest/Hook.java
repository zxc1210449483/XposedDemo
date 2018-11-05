package lyh.cn.xposedhooktest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.sax.Element;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class Hook implements IXposedHookLoadPackage {

    String PackageName="com.greenpoint.android.mc10086.activity";

    String ClassPath="com.secneo.apkwrapper.Helper";

    String MoethodName="azbycx";

    Class<?> clazz=null;

    Class<?> bin=null;

    Class<?> binEle=null;

   // String AppClassPath="com.miui.permcenter.b";
    private volatile ClassLoader mLoader;





    void HookMain(){
        Log.e("lyh222222222参数 1","HookMain");
        XposedHelpers.findAndHookMethod(bin, MoethodName,
                String.class,

                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        //Log.e("lyh222222222", "Hook到方法 Parcel       111111111");


                        Log.e("lyh222222222参数 1",(String) param.args[0]);



//                        Log.e("lyh222222222参数 2",(String) param.args[1]);
//                        Log.e("lyh222222222参数 3",(String) param.args[2]);
//                        Log.e("lyh222222222参数 4",(String) param.args[3]);
//                        Log.e("lyh222222222参数 5",(String) param.args[4]);
//                        Log.e("lyh222222222参数 6",(String) param.args[5]);
//                        Log.e("lyh222222222参数 7",(String) param.args[6]);
//                        Log.e("lyh222222222参数 8",(String) param.args[7]);
//                        Log.e("lyh222222222参数 9",(String) param.args[8]);

                    }


                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.e("lyh222222222", "返回结果  "+(String)param.getResult());

//                                String toString = (String)bin.getMethod("toString").invoke(bin);


                    }
                });

    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        try {
            if(lpparam.packageName.equals(PackageName)) {
                Log.e("lyh222222222", "找到包名 ");


                XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);

                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.e("lyh222222222", "走了 attachBaseContext方法 ");

                        mLoader = ((Context) param.args[0]).getClassLoader();
                        Log.e("lyh222222222", "拿到classloader ");

                        try {
                            bin = Class.forName(ClassPath,true,mLoader);
                            Log.e("lyh222222222", "成功拿到 bin  6666666666666");

                            HookMain();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            Log.e("lyh222222222", "777777777");
                        }
                        Log.e("lyh222222222", "9999999999");
                    }
                });
                //反射可以拿到 已经 加载的类 而不是 新New出来的
                //if(mLoader!=null) {
                if(bin!=null) {
                    bin = Class.forName(ClassPath);

                    if (bin != null) {
                        Log.e("lyh222222222", "成功拿到 bin  ");
                    } else {
                        Log.e("lyh222222222", "没有拿到");
                    }
                }
               // }





            }


        }catch (Exception e){
            Log.e("lyh222222222", "异常");
            Log.e("lyh222222222", "6666666666");
            Log.e("lyh222222222",e.getMessage());
            e.printStackTrace();
        }
    }
}
