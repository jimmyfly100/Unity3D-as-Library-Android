package com.salieri.myapplicate;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.Button;

import com.unity3d.player.UnityPlayer;

import java.lang.reflect.Field;

public class PlayerHolder {
    private UnityPlayer mUnityPlayer;
    private PlayerHolder() {

    }
    private static PlayerHolder instance;

    private boolean hasLaunched = false;

    public static PlayerHolder getInstance() {
        if (instance == null) instance = new PlayerHolder();
        return instance;
    }

    public UnityPlayer getPlayer(final Context context) {
        if (mUnityPlayer == null)  {
            WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
            int flag = params.flags;
            mUnityPlayer = new MyUnityPlayer(context);
            params.flags = flag;
            ((Activity) context).getWindow().setAttributes(params);
            Button button = new Button(context);
            button.setText("pause");
            mUnityPlayer.addView(button, 200, 200);
            button.setOnClickListener(v -> {
                mUnityPlayer.pause();
                mUnityPlayer.onWindowFocusChanged(false);
            });

            Button button2 = new Button(context);
            button2.setText("resume");
            button2.setY(300);
            mUnityPlayer.addView(button2, 200, 200);
            button2.setOnClickListener(v -> {
                mUnityPlayer.resume();
                mUnityPlayer.requestFocus();
                mUnityPlayer.onWindowFocusChanged(true);
            });
        }
        adjustContext(context);
        return mUnityPlayer;
    }

    public  void finishInvoked( Context context){
        adjustContext(context);
    }

    public void adjustContext(Context context) {
        if (mUnityPlayer == null) return;
        if (context instanceof Activity) {
            UnityPlayer.currentActivity = (Activity) context;
        } else {
            UnityPlayer.currentActivity = null;
        }
        Class<?> unityClass = null;
        try {
            unityClass = Class.forName("com.unity3d.player.UnityPlayer");
            Field field = unityClass.getDeclaredField("mContext");
            field.setAccessible(true);
            field.set(mUnityPlayer, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void unityLaunch(boolean launched) {
        hasLaunched = launched;
    }

    public  boolean isLaunched(){
        return hasLaunched;
    }

    public boolean initialized() {
        return mUnityPlayer != null;
    }

}
