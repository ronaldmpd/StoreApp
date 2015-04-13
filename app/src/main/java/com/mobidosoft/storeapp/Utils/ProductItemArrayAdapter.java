package com.mobidosoft.storeapp.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobidosoft.storeapp.R;

import java.util.LinkedList;

/**
 * Created by RP on 4/12/2015.
 */

public class ProductItemArrayAdapter extends ArrayAdapter<ProductItem> {
    LayoutInflater inf;
    LinkedList<ProductItem> objects;

    public ProductItemArrayAdapter(Context context, int resource, int textViewResourceId, LinkedList<ProductItem> objects)
    {
        super(context, resource, textViewResourceId,objects);
        this.inf= LayoutInflater.from(context);
        this.objects = objects;
    }

    public View getView (int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        ProductItem currentProductItem = (ProductItem)objects.get(position);

        if(row ==  null)
        {
            row = inf.inflate(R.layout.product_item_row,null);
        }

        ImageView iv = (ImageView) row.findViewById(R.id.img1ProductItem);
        iv.setImageResource(currentProductItem.getImgDefaultId());
        //iv.setImageBitmap(currentProductItem.getImg1());
        iv.setScaleType(ImageView.ScaleType.FIT_XY);


        TextView tv = (TextView)row.findViewById(R.id.nameProductItem);
        tv.setText(currentProductItem.getName());

        TextView tvDesc = (TextView)row.findViewById(R.id.descProductItem);
        tvDesc.setText("Desc: " + currentProductItem.getDesc() + " img: " + currentProductItem.getImg1String());

        return row;
    }
}
