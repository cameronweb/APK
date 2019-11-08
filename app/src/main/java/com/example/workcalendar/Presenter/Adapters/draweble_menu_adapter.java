package com.example.workcalendar.Presenter.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.workcalendar.R;
import com.example.workcalendar.draweble_menu_item_model;

import java.util.ArrayList;

public class draweble_menu_adapter extends BaseAdapter {
    private ArrayList<draweble_menu_item_model> item_models;
    private LayoutInflater layoutInflater;
    public draweble_menu_adapter(Context context, ArrayList<draweble_menu_item_model> item_models) {
        this.item_models= item_models;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return init(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return item_models.size();
    }

    @Override
    public Object getItem(int position) {
        return item_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return init(position,convertView,parent);
    }
    public View init(int position, View convertView, ViewGroup parent)
    {
        View view =convertView;
        if(view==null)
        {
            view=layoutInflater.inflate(R.layout.draweble_menu_listview_item,parent,false);
        }
        ImageView img=view.findViewById(R.id.draweble_menu_image_item);
        TextView txt=view.findViewById(R.id.draweble_menu_text_view);
        img.setImageResource(item_models.get(position).getIcon_code());
        txt.setText(item_models.get(position).getItem_text());
        return view;
    }
}
