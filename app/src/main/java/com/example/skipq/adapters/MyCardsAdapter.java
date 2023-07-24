package com.example.skipq.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skipq.R;

import java.util.ArrayList;
import java.util.List;

public class MyCardsAdapter extends BaseAdapter {

    List<String> cardName = new ArrayList<>();
    List<String> cardNumber = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public MyCardsAdapter(List<String> cardName, List<String> cardNumber, Context context) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cardName.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.my_cards_item, null);
        TextView cardNameText = convertView.findViewById(R.id.cardName);
        TextView cardNumberText = convertView.findViewById(R.id.cardNumber);

        cardNameText.setText(cardName.get(position));
        cardNumberText.setText(cardNumber.get(position));

        return convertView;

    }
}
