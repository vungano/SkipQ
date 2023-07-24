package com.example.skipq;

import android.os.AsyncTask;

import zw.co.paynow.constants.MobileMoneyMethod;
import zw.co.paynow.core.Payment;
import zw.co.paynow.core.Paynow;
import zw.co.paynow.responses.MobileInitResponse;
import zw.co.paynow.responses.StatusResponse;

public class PayNowConfig extends AsyncTask<String, Void, String> {

    Paynow paynow;
    Payment payment;
    MobileInitResponse response;

    @Override
    protected String doInBackground(String... strings) {
        paynow = new Paynow("15686","63e58367-3874-4674-a643-5eb2ed219373");
        paynow.setResultUrl("http://example.com/gateways/paynow/update");
        paynow.setReturnUrl("http://example.com/return?gateway=paynow&merchantReference=1234");

        payment = paynow.createPayment("Invoice 32","manuelvungano@gmail.com");
        payment.add("cartTotal",2500);
        response = paynow.sendMobile(payment,"0776776869", MobileMoneyMethod.ECOCASH);

        if(response.success()){
            String instructions = response.instructions();
            String pollUrl = response.pollUrl();

            StatusResponse status = paynow.pollTransaction(pollUrl);

            if(status.paid()){

            }
            else{

            }
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
