package com.java.yangjianke.ui.channel;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureDetectorActivity extends Activity {

    /**
     * 手势监听
     */
    GestureDetector mGestureDetector;
    /**
     * 是否需要监听手势关闭功能
     */
    private boolean mNeedBackGesture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGestureDetector();
    }

    private void initGestureDetector() {
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetector(getApplicationContext(),
                    new BackGestureListener(this));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mNeedBackGesture) {
            return mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /*
     * 设置是否进行手势监听
     */
    public void setNeedBackGesture(boolean mNeedBackGesture) {
        this.mNeedBackGesture = mNeedBackGesture;
    }

    /*
     * 返回
     */
    public void doBack(View view) {
        onBackPressed();
    }

}
