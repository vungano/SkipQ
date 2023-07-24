package com.example.skipq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MLKit Barcode";
    private static final int PERMISSION_CODE = 1001;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private PreviewView previewView;
    private CameraSelector cameraSelector;
    private ProcessCameraProvider cameraProvider;
    private Preview previewUseCase;
    private ImageAnalysis analysisUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton scanButton = findViewById(R.id.fab);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        bottomNav.setOnItemSelectedListener(navListener);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ScanActivity.class));
            }
        });
    }

   private BottomNavigationView.OnNavigationItemSelectedListener navListener =
           new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   Fragment selectedFragment = null;

                   switch (item.getItemId()){
                       case R.id.home:
                       case R.id.placeholder:
                           selectedFragment = new HomeFragment();
                           break;
                       case R.id.wifi:
                           selectedFragment = new WifiFragment();
                           break;
                       case R.id.list:
                           selectedFragment = new ListFragment();
                           break;
                       case R.id.profile:
                           selectedFragment = new ProfileFragment();
                           break;
                   }
               getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                       selectedFragment).commit();
               return true;
               }
           };
}