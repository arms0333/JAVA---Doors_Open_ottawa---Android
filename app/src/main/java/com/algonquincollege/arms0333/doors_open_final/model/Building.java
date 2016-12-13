package com.algonquincollege.arms0333.doors_open_final.model;

/**
 * Created by codyarmstrong on 2016-12-06.
 */
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;


public class Building {

    private String description;
    private int buildingId;
    private String name;
    private String address;
    private String image;
    private ArrayList<String> open_doors;
    private Bitmap bitmap;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOpen_doors() {
        return open_doors;
    }

    public void setOpen_doors(ArrayList<String> open_doors) {
        this.open_doors = open_doors;
    }

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address + " Ottawa, Ontario";
    }

    public Bitmap getBitmap(){ return bitmap; }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
