package com.example.myapplication;


import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainService extends Service {

    private WindowManager windowManager;
//    private ImageView floatIcon;
    private TextView textView ;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("MainService.onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("MainService.onCreate");
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

//        floatIcon = new ImageView(this);
        textView = new TextView(this);
        textView.setText(R.string.my_daughter);
        textView.setTextColor(Color.RED);


//        floatIcon.setImageResource(R.drawable.ic_launcher_background);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = 0;
        params.y = 100;

//        windowManager.addView(floatIcon, params);
        windowManager.addView(textView, params);
        try {
            textView.setOnTouchListener(new View.OnTouchListener() {
                private WindowManager.LayoutParams paramsF = params;
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // Get current time in nano seconds.
                            initialX = paramsF.x;
                            initialY = paramsF.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            paramsF.x = initialX + (int) (event.getRawX() - initialTouchX);
                            paramsF.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(textView, paramsF);
                            break;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textView != null) windowManager.removeView(textView);
    }
}