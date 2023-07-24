package com.example.skipq;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skipq.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<String> items;
    private ListAdapter listAdapter;
    private ListView listView;
    private Button button;
    private EditText editText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_list, container, false);

        listView = contentView.findViewById(R.id.toDoListView);
        button = contentView.findViewById(R.id.toDoButton);
        editText = contentView.findViewById(R.id.toDoTextView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        items = new ArrayList<>();
        listAdapter = new ListAdapter(items,contentView.getContext());
        listView.setAdapter(listAdapter);
        setUpListViewListener();

        return contentView;
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getContext();
                Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();

                items.remove(position);
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void addItem(View v) {

        String itemText = editText.getText().toString();

        if (!(itemText.equals(""))){
            items.add(itemText);
            editText.setText("");
        }
        else{
            Toast.makeText(getContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }

    }
}