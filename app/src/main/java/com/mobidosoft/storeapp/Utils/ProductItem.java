package com.mobidosoft.storeapp.Utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

/**
 * Created by RP on 4/12/2015.
 */
public class ProductItem {
    private int id;
    private String name;
    private String desc;
    private String price;
    private Bitmap img1;
    private String img1String;

    private int imgDefaultId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bitmap getImg1() {
        return img1;
    }


    public void setImg1(Bitmap img1) {
        this.img1 = img1;
    }

    public int getImgDefaultId() {
        return imgDefaultId;
    }

    public void setImgDefaultId(int imgDefaultId) {
        this.imgDefaultId = imgDefaultId;
    }

    public String getImg1String() {
        return img1String;
    }

    public void setImg1String(String img1String) {
        this.img1String = img1String;
    }

    /*
            public void setImg1(String image){

                try {
                    this.img1 = loadFromUrl(new URL(image));
                } catch (Exception e) {
                    try {
                        //this.image = loadFromUrl(new URL("http://a1.twimg.com/profile_images/82885809/mdw_hr_reasonably_small.png"));
                        this.img1 = loadFromUrl(new URL("http://www.mobidosoft.com/api/images/94/94_imagen_galeria10_thu.png"));
                    } catch (MalformedURLException e1) {}
                }
            }
        */
    private Bitmap loadFromUrl(URL link) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = link.openConnection().getInputStream();
            bitmap = BitmapFactory.decodeStream(in, null, null);
            in.close();
        } catch (IOException e) {}
        return bitmap;
    }


    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", price='" + price + '\'' +
                ", img1=" + img1 +
                ", imgDefaultId=" + imgDefaultId +
                '}';
    }
}
