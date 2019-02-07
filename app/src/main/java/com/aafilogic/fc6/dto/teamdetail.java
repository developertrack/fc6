package com.aafilogic.fc6.dto;

import java.io.Serializable;

public class teamdetail implements Serializable {
    String name;
    String image;
    int workout;
    int meal;


    public teamdetail(String name, String image, int workout, int meal) {
        this.name = name;
        this.image = image;
        this.workout = workout;
        this.meal = meal;
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

    public int getWorkout() {
        return workout;
    }

    public void setWorkout(int workout) {
        this.workout = workout;
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }


}
