package com.aafilogic.fc6.dto;

public class MealWeekDetail {

    String day_date, day_name,calories, size,breakfast,morning_snacks,lunch,afternoon_snack,dinner;


    public String getDay_date() {
        return day_date;
    }

    public void setDay_date(String day_date) {
        this.day_date = day_date;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
    }

    public String getMorning_snacks() {
        return morning_snacks;
    }

    public void setMorning_snacks(String morning_snacks) {
        this.morning_snacks = morning_snacks;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getAfternoon_snack() {
        return afternoon_snack;
    }

    public void setAfternoon_snack(String afternoon_snack) {
        this.afternoon_snack = afternoon_snack;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public MealWeekDetail(String day_date, String day_name, String calories, String size, String breakfast, String morning_snacks, String lunch, String afternoon_snack, String dinner) {

        this.day_date=day_date;
        this.day_name=day_name;
        this.calories=calories;
        this.size=size;

        this.breakfast=breakfast;
        this.morning_snacks=morning_snacks;
        this.lunch=lunch;
        this.afternoon_snack=afternoon_snack;
        this.dinner=dinner;


    }
}
