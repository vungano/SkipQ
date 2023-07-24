package com.example.skipq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.skipq.adapters.MyCardsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyCards extends AppCompatActivity {

    ListView listView;
    List<String> cardNames = Arrays.asList("Econet","NMB Bank");
    List<String> cardNumbers = Arrays.asList("0776 776 869","4562 0922 1232 7541");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        listView = findViewById(R.id.my_cards_list);
        MyCardsAdapter adapter = new MyCardsAdapter(cardNames,cardNumbers,this);
        listView.setAdapter(adapter);
    }
}