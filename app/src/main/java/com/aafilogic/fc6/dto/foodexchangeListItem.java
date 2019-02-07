package com.aafilogic.fc6.dto;

import java.util.ArrayList;

public class foodexchangeListItem {

    private String food;
    private ArrayList<String> foddlist;
    private String img_num;

    public foodexchangeListItem(String img_num, String food, ArrayList<String> foddlist) {
        this.img_num = img_num;
        this.food = food;
        this.foddlist = foddlist;
    }

    public String getImg_num() {
        return img_num;
    }

    public void setImg_num(String img_num) {
        this.img_num = img_num;
    }


    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public ArrayList<String> getFoddlist() {
        return foddlist;
    }

    public void setFoddlist(ArrayList<String> foddlist) {
        this.foddlist = foddlist;
    }
}
