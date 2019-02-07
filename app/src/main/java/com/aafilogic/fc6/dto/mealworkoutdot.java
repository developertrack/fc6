package com.aafilogic.fc6.dto;

public class mealworkoutdot {
    int image;
    String mealtext;

    public mealworkoutdot(int image, String mealtext) {
        this.image = image;
        this.mealtext = mealtext;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getMealtext() {
        return mealtext;
    }

    public void setMealtext(String mealtext) {
        this.mealtext = mealtext;
    }
}
