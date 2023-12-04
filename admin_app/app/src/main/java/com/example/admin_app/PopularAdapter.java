package com.example.admin_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PopularAdapter extends BaseAdapter {
    private final ArrayList<PopulardestData> dataList;
    private final Context context;
    LayoutInflater layoutInflater;

    public PopularAdapter(ArrayList<PopulardestData> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.advisory_item,null);
        } /*else {
            viewHolder = (ViewHolder) convertView.getTag();
        }*/


        ImageView gridimage = convertView.findViewById(R.id.item_img);
        TextView gridtextview = convertView.findViewById(R.id.item_name);

        Glide.with(context).load(dataList.get(position).getImage()).into(gridimage);
        gridtextview.setText(dataList.get(position).getTitle());
        return convertView;
    }
}
