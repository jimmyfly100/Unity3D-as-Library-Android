package com.salieri.myapplicate;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtil {
    public static void addView(ViewGroup viewGroup, View childView) {
        if (viewGroup == null) return;
        if (childView == null) return;
        if (childView.getParent() == viewGroup) return;
        if (childView.getParent() != null) {
            ViewGroup parentView = (ViewGroup) childView.getParent();
            if (parentView != viewGroup) {
                parentView.removeView(childView);
                viewGroup.addView(childView, 0);
            } else {
                //已经添加
            }
        } else {
            viewGroup.addView(childView, 0);
        }
    }
}
