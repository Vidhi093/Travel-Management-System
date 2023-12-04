package com.example.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class searchAdapter extends ArrayAdapter<advisoryData> {

    private final Context mcontext;
    List<advisoryData> itemList;

    public searchAdapter(Context context, List<advisoryData> items) {
        super(context, 0, items);
        mcontext = context;
        itemList = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.advisory_item, parent,false);

        }
        advisoryData item = itemList.get(position);
        TextView itemName = convertView.findViewById(R.id.item_name);
        ImageView itemImage = convertView.findViewById(R.id.item_img);

        itemName.setText(item.getPlace());
        Glide.with(mcontext).load(item.getImage()).into(itemImage);

        return convertView;

    }
}
