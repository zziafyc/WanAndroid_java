package com.zziafyc.wanandroid.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zziafyc.wanandroid.R;


/**
 * Created by rick
 *
 * @author chenbo
 */
public class ToastUtil {
    private Context context;
    private Resources resources;
    private static Toast mToast;
    private static TextView text;
    private static ImageView img;

    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public ToastUtil(Context context) {
        this.context = context;
        this.resources = context.getResources();
        View v = LayoutInflater.from(context).inflate(R.layout.toast_warning, null);
        text = v.findViewById(R.id.txt_toast_warning);
        img = v.findViewById(R.id.img_toast_warning);
        mToast = new Toast(context);
        mToast.setView(v);
    }

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

}
