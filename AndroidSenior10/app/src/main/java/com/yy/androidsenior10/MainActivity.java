package com.yy.androidsenior10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0x1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void floatPlay(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (Settings.canDrawOverlays(this)){
                Intent intent = new Intent(this, FloatPropertyService.class);
                startService(intent);
            }else{
                Toast.makeText(this, "无权限，请授权", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,REQUEST_CODE);
            }
        }else{
            Toast.makeText(this, "版本太低", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (Settings.canDrawOverlays(this)){
                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, FloatPropertyService.class);
                    startService(intent);
                }else{
                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

