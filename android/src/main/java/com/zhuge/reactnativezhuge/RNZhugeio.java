package com.zhuge.reactnativezhuge;

import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.zhuge.analysis.stat.ZhugeSDK;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

/**
 * 将Zhuge SDK 方法暴露至React native
 * Created by Jiaokang on 2018/2/27.
 */

public class RNZhugeio extends ReactContextBaseJavaModule {

    private static final String TAG ="RNZhugeio";

    public RNZhugeio(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @ReactMethod
    public void init(String appkey,String channel){
        ZhugeSDK.getInstance().init(getReactApplicationContext(),appkey,channel);
    }

    @ReactMethod
    public void openLog(){
        ZhugeSDK.getInstance().openLog();
    }

    @ReactMethod
    public void openDebug(){
        ZhugeSDK.getInstance().openDebug();
    }

    @ReactMethod
    public void setUploadURL(String url,String backupUrl){
        ZhugeSDK.getInstance().setUploadURL(url,backupUrl);
    }

    @ReactMethod
    public void identify(String uid , ReadableMap pro){
        debug("identify "+uid);
        HashMap<String, Object> properties = new HashMap<>();
        if (pro != null){
            ReadableMapKeySetIterator readableMapKeySetIterator = pro.keySetIterator();
            while (readableMapKeySetIterator.hasNextKey()){
                String key = readableMapKeySetIterator.nextKey();
                String value = pro.getString(key);
                properties.put(key,value);
            }
        }
        ZhugeSDK.getInstance().identify(null,uid,properties);
    }

    @ReactMethod
    public void track(String name, ReadableMap pro){
        debug("track "+name);
        HashMap<String, Object> properties = new HashMap<>();
        if (pro != null){
            ReadableMapKeySetIterator readableMapKeySetIterator = pro.keySetIterator();
            while (readableMapKeySetIterator.hasNextKey()){
                String key = readableMapKeySetIterator.nextKey();
                String value = pro.getString(key);
                properties.put(key,value);
            }
        }
        debug("pro size is "+properties.size());
        ZhugeSDK.getInstance().track(null,name,properties);
    }

    @ReactMethod
    public void startTrack(String name){
        debug("startTrack "+name);
        ZhugeSDK.getInstance().startTrack(name);
    }

    @ReactMethod
    public void endTrack(String name, ReadableMap pro){
        debug("endTrack : "+name);
        JSONObject properties = null;
        if (pro != null){
            properties = new JSONObject();
            ReadableMapKeySetIterator readableMapKeySetIterator = pro.keySetIterator();
            try {
                while (readableMapKeySetIterator.hasNextKey()){
                    String key = readableMapKeySetIterator.nextKey();
                    String value = pro.getString(key);
                    properties.put(key,value);
                }
            }catch (Exception e){
                Log.e(TAG,"convert map to json error",e);
            }
        }
        ZhugeSDK.getInstance().endTrack(name,properties);
    }

    @Override
    public String getName() {
        return "Zhugeio";
    }

    private void debug(String info){
        if (BuildConfig.DEBUG){
            Log.d(TAG,info);
        }
    }
}