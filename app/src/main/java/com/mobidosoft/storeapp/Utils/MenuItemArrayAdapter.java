package com.mobidosoft.storeapp.Utils;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidosoft.storeapp.R;

import java.util.LinkedList;

/**
 * Created by RP on 4/5/2015.
 */
public class MenuItemArrayAdapter extends ArrayAdapter<MenuItem> {
    LayoutInflater inf;
    LinkedList<MenuItem> objects;

    public MenuItemArrayAdapter(Context context, int resource, int textViewResourceId, LinkedList<MenuItem> objects)
    {
        super(context, resource, textViewResourceId,objects);
        this.inf= LayoutInflater.from(context);
        this.objects = objects;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        MenuItem currentMenuItem = (MenuItem)objects.get(position);

        if(row ==  null)
        {
            row = inf.inflate(R.layout.menu_item_row,null);
        }

        ImageView iv = (ImageView) row.findViewById(R.id.imgMenuItem);
        iv.setImageResource(currentMenuItem.getImageId());
        iv.setScaleType(ImageView.ScaleType.FIT_XY);


        TextView tv = (TextView)row.findViewById(R.id.nameMenuItem);
        tv.setText(currentMenuItem.getName());

        return row;
    }
}
