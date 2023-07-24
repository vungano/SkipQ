package com.example.skipq;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.skipq.adapters.DiscountsBaseAdapter;
import com.example.skipq.model.DiscountedItem;
import com.example.skipq.retrofit.GetDataApi;
import com.example.skipq.retrofit.RetrofitService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;  
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1"   ;
    private static final String ARG_PARAM2 = "param2";
    String [] discountItemName = {"Lays","Coke","Burger","Heineken","Nivea","Nutella","Salad","Chips","Lemons","Donuts"};
    int [] images = {R.drawable.lays,R.drawable.coke,R.drawable.burger,R.drawable.heineken,R.drawable.nivea,R.drawable.nutella,R.drawable.salad,R.drawable.chips,R.drawable.lemons,R.drawable.donut};
    double [] discountedItemOldPrice = {2.55,0.47,2.52,1.75,3.42,2.52,3.48,1.35,0.65,0.35};
    double [] discountItemNewPrice = {2.35,0.41, 2.11,1.51,3.02,2.41,3.25,1.05,0.55,0.31};
    List<DiscountedItem> discountedItems;
    List<DiscountedItem> discountedItems2;
    Response<List<DiscountedItem>> discountResponse;
    RetrofitService retrofitService;
    GetDataApi getDataApi;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    GridView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        retrofitService = new RetrofitService();
        getDataApi = retrofitService.getRetrofit().create(GetDataApi.class);

        try {
            discountResponse = getDataApi.getDiscountedItems().execute();
            discountedItems = discountResponse.body();
            Log.e("TAGGEST STARTER", String.valueOf(discountedItems.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (discountedItems != null){
            for (int i = 0; i < discountedItems.size(); i++) {
                discountItemName.add(discountedItems.get(i).getProductName());
                discountImageUrl.add(discountedItems.get(i).getImageUrl());
                discountItemOldPrice.add(discountedItems.get(i).getOldPrice());
                discountItemNewPrice.add(discountedItems.get(i).getNewPrice());
            }
        }
        */
        /*
        getDataApi.getDiscountedItems().enqueue(new Callback<List<DiscountedItem>>() {
            @Override
            public void onResponse(Call<List<DiscountedItem>> call, Response<List<DiscountedItem>> response) {
                discountedItems = response.body();
                Log.e("TAGGEST TAG BITCH", String.valueOf(discountedItems.size()));
                for (int i = 0; i < discountedItems.size(); i++) {
                    discountItemName.add(discountedItems.get(i).getProductName());
                    discountImageUrl.add(discountedItems.get(i).getImageUrl());
                    discountItemOldPrice.add(discountedItems.get(i).getOldPrice());
                    discountItemNewPrice.add(discountedItems.get(i).getNewPrice());
                }

                //Log.e("TAGGEST", String.valueOf(discountItemName.size()));
                //Log.e("TAGGEST",discountItemName.get(0));
            }

            @Override
            public void onFailure(Call<List<DiscountedItem>> call, Throwable t) {
                    Log.e("TAGGEST TAG BITCH", t.getMessage());
            }
        });


         */
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_home, container, false);

        listView = contentView.findViewById(R.id.discount_list_view);
        DiscountsBaseAdapter baseAdapter = new DiscountsBaseAdapter(contentView.getContext(), discountItemName, images, discountedItemOldPrice, discountItemNewPrice);
        listView.setAdapter(baseAdapter);

        return contentView;
    }
}