package com.example.skipq.retrofit;

import com.example.skipq.model.DetailedTransaction;
import com.example.skipq.model.DiscountedItem;
import com.example.skipq.model.ScannedProduct;
import com.example.skipq.model.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface GetDataApi {

    @GET("/product/{productCode}")
    Call<ScannedProduct> getProductByCode(@Path("productCode") Long productCode);

    @GET("/discountItems")
    Call<List<DiscountedItem>> getDiscountedItems();

    @POST("/setTransaction")
    Call<Integer> addTransaction(@Body Transaction transaction);

    @POST("/postTransaction")
    Call<List<DetailedTransaction>> postTransaction(@Body List<DetailedTransaction> detailedTransactions);

}
