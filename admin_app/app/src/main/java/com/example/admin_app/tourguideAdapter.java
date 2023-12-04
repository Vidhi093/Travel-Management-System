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

public class tourguideAdapter extends BaseAdapter {

    public final ArrayList<tourguideData> datalist;

    private final Context context;
    LayoutInflater layoutInflater;

    public tourguideAdapter(ArrayList<tourguideData> datalist, Context context) {
        this.datalist = datalist;
        this.context = context;
    }


    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.tourguide_item,null);
        } /*else {
            viewHolder = (ViewHolder) convertView.getTag();
        }*/


        ImageView gridimage = convertView.findViewById(R.id.item_img);
        TextView gridtextview = convertView.findViewById(R.id.item_name);

        Glide.with(context).load(datalist.get(position).getImage()).into(gridimage);
        gridtextview.setText(datalist.get(position).getName());
        return convertView;
    }
}
