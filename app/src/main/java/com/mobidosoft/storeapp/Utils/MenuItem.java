package com.mobidosoft.storeapp.Utils;

/**
 * Created by RP on 4/5/2015.
 */
public class MenuItem {

    private int id;
    private int imageId;
    private String name;
    private String categoryId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", imageId=" + imageId +
                ", name='" + name + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
