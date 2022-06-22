package com.salieri.myapplicate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.unity3d.player.UnityPlayer;

public class UnityFirstActivity extends AppCompatActivity {
    private UnityPlayer mUnityPlayer;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnityPlayer = PlayerHolder.getInstance().getPlayer(this);
        frameLayout = new FrameLayout(this);
        setContentView(frameLayout);
        ViewUtil.addView(frameLayout, mUnityPlayer);
        Button button = new Button(this);
        button.setX(500);
        button.setY(500);
        button.setBackgroundColor(0x44ff0000);
        button.setText("Go to native");
        frameLayout.addView(button, 200, 200);
        setUnityToBottom();

        button.setOnClickListener(v -> {
            Intent intent = new Intent(this, NativeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void finish() {
//        PlayerHolder.getInstance().adjustContext(MyApplication.getInstance());
        super.finish();
    }

    @Override
    protected void onResume() {
        PlayerHolder.getInstance().adjustContext(this);
        super.onResume();
        ViewUtil.addView(frameLayout, mUnityPlayer);
        mUnityPlayer.resume();
        mUnityPlayer.requestFocus();
    }

    @Override
    protected void onPause() {
        PlayerHolder.getInstance().adjustContext(MyApplication.getInstance());
        super.onPause();
        mUnityPlayer.pause();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.mUnityPlayer.windowFocusChanged(hasFocus);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mUnityPlayer.configurationChanged(newConfig);
    }

    protected void setUnityToBottom() {
        View view = mUnityPlayer.getChildAt(0);
        if(view instanceof SurfaceView) {
            ((SurfaceView) view).getHolder().setFormat(-1);
            ((SurfaceView) view).setZOrderMediaOverlay(false);
        }
    }
}
