package com.example.skipq.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skipq.R;
import com.example.skipq.db.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketBaseAdapter extends BaseAdapter {

    Context context;
    List<String> productNames = new ArrayList<>();
    List<String> productPrices = new ArrayList<>();
    List<String> quantities = new ArrayList<>();
    List<String> productCodes = new ArrayList<>();
    String product_price;
    LayoutInflater layoutInflater;
    TextView cartTotal, cartCount;
    DBHelper db;

    public ShoppingBasketBaseAdapter(Context context, List<String> productNames, List<String> productPrices, List<String> quantities, List<String> productCodes, TextView cartTotal, TextView cartCount, String product_price, DBHelper db) {
        this.context = context;
        this.productNames = productNames;
        this.productPrices = productPrices;
        this.quantities = quantities;
        this.cartTotal = cartTotal;
        this.cartCount = cartCount;
        this.productCodes = productCodes;
        this.product_price = product_price;
        this.db = db;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {return productNames.size();}

    @Override
    public Object getItem(int position) {return null;}

    @Override
    public long getItemId(int position) {return 0;}

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.scan_list_item, null);
        TextView productName = convertView.findViewById(R.id.scanned_product_description);
        TextView productPrice = convertView.findViewById(R.id.scanned_product_price);
        TextView addQty = convertView.findViewById(R.id.addQty);
        TextView subtractQty = convertView.findViewById(R.id.removeQty);
        TextView qty = convertView.findViewById(R.id.qty);

        addQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty.setText(String.valueOf(Integer.parseInt(String.valueOf(qty.getText()))+1));
                double d1 = Double.parseDouble(String.valueOf(cartTotal.getText()));
                double d2 = Double.parseDouble(product_price);

                //productPrice.setText(String.valueOf(Integer.parseInt(String.valueOf(qty.getText()))*Integer.parseInt(String.valueOf(productPrice.getText()))));
                db.updateCart(Long.valueOf(productCodes.get(position)),Integer.parseInt(String.valueOf(qty.getText())));
                cartCount.setText(String.valueOf(Integer.parseInt((String) cartCount.getText())+1));
                cartTotal.setText(String.valueOf(d1+d2));
            }
        });

        subtractQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(String.valueOf(qty.getText()))>1) {
                    int qntyInt = Integer.parseInt(String.valueOf(qty.getText()));
                    double d1 = Double.parseDouble(String.valueOf(cartTotal.getText()));
                    double d2 = Double.parseDouble(product_price);

                    qty.setText(String.valueOf(qntyInt - 1));
                    //productPrice.setText(String.valueOf(Integer.parseInt(String.valueOf(qty.getText()))*Integer.parseInt(String.valueOf(productPrice.getText()))));
                    db.updateCart(Long.valueOf(productCodes.get(position)),Integer.parseInt(String.valueOf(qty.getText())));
                    cartCount.setText(String.valueOf(Integer.parseInt((String) cartCount.getText())-1));
                    cartTotal.setText(String.valueOf(d1-d2));
                }
            }
        });

        productName.setText(productNames.get(position));
        productPrice.setText(productPrices.get(position));
        qty.setText(quantities.get(position));
        return convertView;
    }
}
