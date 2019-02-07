package com.aafilogic.fc6.dto;

public class mealplan {


    private int image;
    private String weakNumber;
    public String meal_plan_id;
    public String meal_week;

    public mealplan(int image, String weakNumber,String meal_plan_id) {
        this.image = image;
        this.weakNumber = weakNumber;
        this.meal_plan_id=meal_plan_id;
    }

    public String getMeal_week() {
        return meal_week;
    }

    public void setMeal_week(String meal_week) {
        this.meal_week = meal_week;
    }



    public String getMeal_plan_id() {
        return meal_plan_id;
    }

    public void setMeal_plan_id(String meal_plan_id) {
        this.meal_plan_id = meal_plan_id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getWeakNumber() {
        return weakNumber;
    }

    public void setWeakNumber(String weakNumber) {
        this.weakNumber = weakNumber;
    }
}
