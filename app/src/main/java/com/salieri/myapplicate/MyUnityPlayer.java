package com.salieri.myapplicate;

import android.content.Context;

import com.unity3d.player.UnityPlayer;

import java.lang.reflect.Field;

public class MyUnityPlayer extends UnityPlayer {
    public MyUnityPlayer(Context context) {
        super(context);
        Class<?> unityClass = null;
        try {
            unityClass = Class.forName("com.unity3d.player.UnityPlayer");
            Field field = unityClass.getDeclaredField("m_PersistentUnitySurface");
            field.setAccessible(true);
            field.set(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isUaaLUseCase() {
        return true;
    }

    @Override
    protected void kill() {

    }
}
