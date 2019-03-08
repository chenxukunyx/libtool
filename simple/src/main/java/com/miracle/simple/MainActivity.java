package com.miracle.simple;

import android.Manifest;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.vanniktech.rxpermission.RealRxPermission;
import com.vanniktech.rxpermission.RxPermission;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealRxPermission.getInstance(getApplicationContext())
                        .requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<com.vanniktech.rxpermission.Permission>() {
                            @Override
                            public void accept(com.vanniktech.rxpermission.Permission permission) throws Exception {
                                Log.i(TAG, "accept: " + permission.name() + " " + permission.state());
                            }
                        });
            }
        });
    }
}
