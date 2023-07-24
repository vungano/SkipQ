package com.example.skipq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.skipq.db.DBHelper;
import com.example.skipq.model.ScannedProduct;
import com.example.skipq.retrofit.RetrofitService;
import com.example.skipq.retrofit.GetDataApi;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "MLKit Barcode";
    private static final int PERMISSION_CODE = 1001;
    private static final String CAMERA_PERMISSION = Manifest.permission.CAMERA;
    private PreviewView previewView;
    private CameraSelector cameraSelector;
    private ProcessCameraProvider cameraProvider;
    private Preview previewUseCase;
    private ImageAnalysis analysisUseCase;
    private DBHelper db = new DBHelper(this);
    private static int qnty = 1;
    String cartTotal, cartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        previewView = findViewById(R.id.previewView);

        Intent in = getIntent();
        cartTotal = in.getStringExtra("cartTotal");
        cartCount = in.getStringExtra("cartCount");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startCamera();
    }

    public void startCamera() {
        if (ContextCompat.checkSelfPermission(this, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
            setupCamera();
        }
         else {
            getPermissions();
        }
    }

    private void getPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA_PERMISSION}, PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        for (int r : grantResults) {
            if (r == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (requestCode == PERMISSION_CODE) {
            setupCamera();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void setupCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        int lensFacing = CameraSelector.LENS_FACING_BACK;
        cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindAllCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "cameraProviderFuture.addListener Error", e);
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindAllCameraUseCases() {
        if (cameraProvider != null) {
            cameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase() {
       /*
        if (cameraProvider == null) {
            return;
        }
*/
        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        builder.setTargetRotation(getRotation());

        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());

        try {
            cameraProvider.bindToLifecycle(this, cameraSelector, previewUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind preview", e);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return;
        }

        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }

        Executor cameraExecutor = Executors.newSingleThreadExecutor();

        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
        builder.setTargetRotation(getRotation());

        analysisUseCase = builder.build();
        analysisUseCase.setAnalyzer(cameraExecutor, this::analyze);

        try {
            cameraProvider.bindToLifecycle(this, cameraSelector, analysisUseCase);
        } catch (Exception e) {
            Log.e(TAG, "Error when bind analysis", e);
        }

    }

    protected int getRotation() throws NullPointerException {
        try {
            return previewView.getDisplay().getRotation();
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private void analyze(@NonNull ImageProxy image) {
        if (image.getImage() == null) return;

        InputImage inputImage = InputImage.fromMediaImage(
                image.getImage(),
                image.getImageInfo().getRotationDegrees()
        );

        BarcodeScanner barcodeScanner = BarcodeScanning.getClient();

        try {
            barcodeScanner.process(inputImage)
                    .addOnSuccessListener(this::onSuccessListener)
                    .addOnFailureListener(e -> Log.e(TAG, "Barcode process failure", e))
                    .addOnCompleteListener(task -> image.close());
        }
        catch (Exception e){
            Toast.makeText(this, "Error scanning + "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }

    private void onSuccessListener(List<Barcode> barcodes) {
        if (barcodes.size()>0) {
            cameraProvider.unbindAll();
            RetrofitService retrofitService = new RetrofitService();
            GetDataApi getDataApi = retrofitService.getRetrofit().create(GetDataApi.class);
            getDataApi.getProductByCode(Long.valueOf(barcodes.get(0).getDisplayValue()))
                    .enqueue(new Callback<ScannedProduct>() {
                        @Override
                        public void onResponse(Call<ScannedProduct> call, Response<ScannedProduct> response) {
                            if (response.body().getProductCode() != null) {
                                ScannedProduct scannedProduct = response.body();
                                //Toast.makeText(ScanActivity.this,"Scan succes: "+response.body().getProductName(),Toast.LENGTH_SHORT).show();
                                    Boolean isInserted = db.insertData(
                                            scannedProduct.getProductCode(),
                                            scannedProduct.getProductName(),
                                            scannedProduct.getProductPrice(),
                                            qnty,
                                            scannedProduct.getProductionDate(),
                                            scannedProduct.getExpiryDate(),
                                            scannedProduct.getManufacturer(),
                                            scannedProduct.getProductionCountry(),
                                            scannedProduct.getPromotion()
                                    );

                                if (cartTotal != null) {
                                    cartTotal = String.valueOf(Double.parseDouble(cartTotal) + scannedProduct.getProductPrice());
                                }

                                else{
                                    cartTotal = String.valueOf(scannedProduct.getProductPrice());
                                }

                                Intent intent = new Intent(ScanActivity.this,ShoppingBasketActivity.class);
                                intent.putExtra("cart_total",cartTotal);
                                intent.putExtra("cart_count",cartCount);
                                intent.putExtra("product_price",String.valueOf(scannedProduct.getProductPrice()));
                                startActivity(intent);
                                finish();
                            }
                            else {
                                bindAllCameraUseCases();
                            }
                        }
                            @Override
                            public void onFailure (Call < ScannedProduct > call, Throwable t){
                                Toast.makeText(ScanActivity.this,"Scan failed with error "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                    });
        }
    }
}