package com.example.skipq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.skipq.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DiscountsBaseAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    String [] discountItemName;
    int [] images;
    double [] discountedItemOldPrice;
    double [] discountItemNewPrice;

    public DiscountsBaseAdapter(Context context, String [] discountItemName, int [] images, double [] discountedItemOldPrice, double [] discountItemNewPrice) {
        this.context = context;
        this.discountItemName = discountItemName;
        this.images = images;
        this.discountedItemOldPrice = discountedItemOldPrice;
        this.discountItemNewPrice = discountItemNewPrice;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return discountItemNewPrice.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.home_list_item, null);
        TextView textView = view.findViewById(R.id.discount_description);
        ImageView imgView = view.findViewById(R.id.promoImg);
        TextView oldPriceView = view.findViewById(R.id.old_price);
        TextView newPriceView = view.findViewById(R.id.new_price);
        TextView discountBadge = view.findViewById(R.id.discountPct);

        textView.setText(discountItemName[i]);
        oldPriceView.setText(String.valueOf(discountedItemOldPrice[i]));
        newPriceView.setText(String.valueOf(discountItemNewPrice[i]));
        imgView.setImageResource(images[i]);

        return view;
    }
}
