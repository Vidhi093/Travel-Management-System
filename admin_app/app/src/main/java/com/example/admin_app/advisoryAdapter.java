package com.example.admin_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class advisoryAdapter extends BaseAdapter {

    private final ArrayList<advisoryData> dataList;
    private final Context context;
    LayoutInflater layoutInflater;

    public advisoryAdapter(ArrayList<advisoryData> dataList, Context context) {
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
        //ViewHolder viewHolder;
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
        gridtextview.setText(dataList.get(position).getPlace());
       return convertView;

    }

    /*private static class ViewHolder {

        private TextView place, state, descp, wtt, ttd;
        private ImageView imageView;
        private advisoryData data;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.item_img);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(),last_advisory.class);
                    intent.putExtra("resId", data.getImage());
                    v.getContext().startActivity(intent);
                }
            });
        }

        void bindData(advisoryData data) {
            this.data = data;
            Picasso.get().load(data.getImage()).into(imageView);
        }
    }*/
}
