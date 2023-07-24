package com.example.skipq.adapters;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.skipq.R;

import java.util.List;

public class WifiAdapter extends BaseAdapter {

    Context context;
    List<ScanResult> wifiList;
    LayoutInflater inflater;

    public WifiAdapter(Context context, List<ScanResult> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return wifiList.size();
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

        Holder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.fragment_wifi,null);
            holder = new Holder();
            holder.wifiItem = convertView.findViewById(R.id.wifi_item);
            convertView.setTag(holder);
        }

        else{
            holder = (Holder) convertView.getTag();
        }
        return null;
    }

    class Holder{
        TextView wifiItem;
    }
}
