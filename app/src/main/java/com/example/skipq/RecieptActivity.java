package com.example.skipq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class RecieptActivity extends AppCompatActivity {

    MultiFormatWriter multiFormatWriter;
    BitMatrix bitMatrix;
    BarcodeEncoder encoder;
    Bitmap bitmap;
    ImageView qrImage;
    String transactionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        Intent in = getIntent();
        transactionId = in.getStringExtra("transactionId");

        //Toast.makeText(this, "Id est "+transactionId, Toast.LENGTH_SHORT).show();

        qrImage = findViewById(R.id.qr_code_image);
        multiFormatWriter = new MultiFormatWriter();

        try {

            bitMatrix  = multiFormatWriter.encode(transactionId, BarcodeFormat.QR_CODE,350,350);
            encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(bitMatrix);
            qrImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(RecieptActivity.this, MainActivity.class));
                finish();
            }
        },2500);

    }
}