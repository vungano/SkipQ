package com.example.skipq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skipq.db.DBHelper;
import com.example.skipq.model.DetailedTransaction;
import com.example.skipq.model.ShoppingItem;
import com.example.skipq.model.Transaction;
import com.example.skipq.retrofit.GetDataApi;
import com.example.skipq.retrofit.RetrofitService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zw.co.paynow.constants.MobileMoneyMethod;
import zw.co.paynow.core.Payment;
import zw.co.paynow.core.Paynow;
import zw.co.paynow.responses.MobileInitResponse;
import zw.co.paynow.responses.StatusResponse;

public class CheckoutActivity extends AppCompatActivity {

    Button payNowButton;
    Paynow paynow;
    Payment payment;
    MobileInitResponse response;
    String cartTotal;
    private DBHelper db = new DBHelper(this);
    TextView cartTotalCheckout;
    int transactionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();

        cartTotal = intent.getStringExtra("cartTotal");
        payNowButton = findViewById(R.id.payNow);

        cartTotalCheckout = findViewById(R.id.cartTotalCheckout);
        cartTotalCheckout.setText(cartTotal);

        paynow = new Paynow("15686","63e58367-3874-4674-a643-5eb2ed219373");
        paynow.setResultUrl("http://example.com/gateways/paynow/update");
        paynow.setReturnUrl("http://example.com/return?gateway=paynow&merchantReference=1234");

        payNowButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                /*
                payment = paynow.createPayment("Invoice 32","manuelvungano@gmail.com");
                payment.add("cartTotal",2500);
                response = paynow.sendMobile(payment,"0776776869", MobileMoneyMethod.ECOCASH);

                if(response.success()) {
                    String instructions = response.instructions();
                    String pollUrl = response.pollUrl();

                    StatusResponse status = paynow.pollTransaction(pollUrl);

                    if (status.paid()) {

                    } else {
                    }
                }*/

                Transaction transaction = new Transaction(false,"",Double.valueOf(cartTotal));
                postNewTransaction(transaction);

                // postAllTransactions();
            }
        });
    }

    private void postAllTransactions() {

        }

    private void postNewTransaction(Transaction transaction) {

        RetrofitService retrofitService = new RetrofitService();
        GetDataApi getDataApi = retrofitService.getRetrofit().create(GetDataApi.class);
        getDataApi.addTransaction(transaction).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                //Toast.makeText(CheckoutActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                transactionId = Integer.parseInt(response.body().toString());

                List<DetailedTransaction> detailedTransactions = new ArrayList<>();

                try {
                    ArrayList<ShoppingItem> products = db.getAllProducts();

                    for(int i =0; i<products.size(); i++){

                        double totalPrices = products.get(i).getProductPrice() * (double) products.get(i).getQuantity();

                        detailedTransactions.add(new DetailedTransaction(transactionId,
                                products.get(i).getProductName(),
                                products.get(i).getProductPrice(),
                                products.get(i).getQuantity(),
                                totalPrices));
                    }

                    RetrofitService retrofitService = new RetrofitService();
                    GetDataApi getDataApi = retrofitService.getRetrofit().create(GetDataApi.class);
                    getDataApi.postTransaction(detailedTransactions).enqueue(new Callback<List<DetailedTransaction>>() {
                        @Override
                        public void onResponse(Call<List<DetailedTransaction>> call, Response<List<DetailedTransaction>> response) {
                            //Toast.makeText(CheckoutActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<DetailedTransaction>> call, Throwable t) {
                            //Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (ParseException e) {
                    e.printStackTrace();
                }


                    try {
                        Toast.makeText(CheckoutActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckoutActivity.this, RecieptActivity.class);
                        intent.putExtra("transactionId", response.body().toString());
                        db.clearDatabase();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent);
                                finish();
                            }
                        },1500);

                    }
                    catch (Exception e){
                        //Toast.makeText(CheckoutActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                //Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}