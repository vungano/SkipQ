package com.example.skipq;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skipq.adapters.DiscountsBaseAdapter;
import com.example.skipq.adapters.ShoppingBasketBaseAdapter;
import com.example.skipq.db.DBHelper;
import com.example.skipq.model.ScannedProduct;
import com.example.skipq.model.ShoppingItem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketActivity extends AppCompatActivity implements com.example.skipq.ShoppingBasketBaseAdapter {

    private DBHelper db = new DBHelper(this);
    ListView shoppingList;
    RelativeLayout scanAgainButton;
    ArrayList<ShoppingItem> products;
    List<ShoppingItem> scannedProducts;
    List<String> productNames = new ArrayList<>();
    List<String> productPrices = new ArrayList<>();
    List<String> quantities = new ArrayList<>();
    List<String> productCodes = new ArrayList<>();
    TextView cartTotal, cartCount;
    String cart_total, cart_count, product_price;
    Button checkOutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);

        Intent in = getIntent();
        cart_total = in.getStringExtra("cart_total");
        cart_count = in.getStringExtra("cart_count");
        product_price = in.getStringExtra("product_price");

        shoppingList = findViewById(R.id.shoppingList);
        cartTotal = findViewById(R.id.totalCartPrice);
        cartCount = findViewById(R.id.cartCount);
        checkOutBtn = findViewById(R.id.checkoutBtn);

        if (cart_count != null){
            int x = Integer.parseInt(cart_count)+1;
            cartCount.setText(String.valueOf(x));
        }

        cartTotal.setText(String.valueOf(cart_total));
        scannedProducts = new ArrayList<>();
        try {
            products = db.getAllProducts();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i = 0; i<products.size(); i++){
            productNames.add(products.get(i).getProductName());
            productPrices.add(String.valueOf(products.get(i).getProductPrice()));
            quantities.add(String.valueOf(products.get(i).getQuantity()));
            productCodes.add(String.valueOf(products.get(i).getProductCode()));
        }

        ShoppingBasketBaseAdapter basketBaseAdapter = new ShoppingBasketBaseAdapter(this,productNames,productPrices,quantities,productCodes,cartTotal,cartCount,product_price,db);
        shoppingList.setAdapter(basketBaseAdapter);

        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ShoppingBasketActivity.this, productNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        shoppingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                products.remove(position);
                for(int i = 0; i<products.size(); i++){
                    productNames.add(products.get(i).getProductName());
                    productPrices.add(String.valueOf(products.get(i).getProductPrice()));
                    quantities.add(String.valueOf(products.get(i).getQuantity()));
                    productCodes.add(String.valueOf(products.get(i).getProductCode()));
                }
                basketBaseAdapter.notifyDataSetChanged();
                return true;
            }
        });

        scanAgainButton = findViewById(R.id.scanAgainButton);

        try {
            scanAgainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ShoppingBasketActivity.this, ScanActivity.class);
                    intent.putExtra("cartTotal",cartTotal.getText());
                    intent.putExtra("cartCount",cartCount.getText());
                    startActivity(intent);
                }
            });
        }
        catch (Exception e){
            Log.e("ML KIT BARCODE",e.getMessage());
        }

        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingBasketActivity.this, CheckoutActivity.class);
                intent.putExtra("cartTotal", cartTotal.getText());
                startActivity(intent);
            }
        });
    }
}