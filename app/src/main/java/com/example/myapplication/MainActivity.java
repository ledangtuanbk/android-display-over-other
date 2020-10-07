package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle icicle){
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0);
            }
        }

        Bundle bund = getIntent().getExtras();

        if(bund != null && bund.getString("LAUNCH").equals("YES")){
            startService(new Intent(MainActivity.this, MainService.class));
        }

        Button start = (Button)findViewById(R.id.btnStart);
        start.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                startService(new Intent(MainActivity.this, MainService.class));
            }
        });

        Button stop = (Button)findViewById(R.id.btnStop);
        stop.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                stopService(new Intent(MainActivity.this, MainService.class));
            }
        });
    }

    @Override
    protected void onResume(){
        Bundle bund = getIntent().getExtras();

        if(bund != null && bund.getString("LAUNCH").equals("YES")){
            startService(new Intent(MainActivity.this, MainService.class));
        }
        super.onResume();
    }
}