package com.example.skipq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skipq.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    List<String> itemName = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public ListAdapter(List<String> itemName, Context context){
        this.itemName = itemName;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemName.size();
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

        convertView = layoutInflater.inflate(R.layout.to_do_list_item, null);
        TextView singleListItem = convertView.findViewById(R.id.singleListItem);

        singleListItem.setText(itemName.get(position));

        return convertView;

    }
}
