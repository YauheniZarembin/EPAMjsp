package com.zarembin.epampjsp.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Dish extends Entity{
    private String dishName;
    private TypeOfDish typeOfDish;
    private BigDecimal price;
    private LocalTime cookingTime;
    private int maxNumberOfServings;
    private String imagePath;

    public Dish(String dishName, TypeOfDish typeOfDish, BigDecimal price, LocalTime cookingTime, int maxNumberOfServings, String imagePath) {
        this.dishName = dishName;
        this.typeOfDish = typeOfDish;
        this.price = price;
        this.cookingTime = cookingTime;
        this.maxNumberOfServings = maxNumberOfServings;
        this.imagePath = imagePath;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public TypeOfDish getTypeOfDish() {
        return typeOfDish;
    }

    public void setTypeOfDish(TypeOfDish typeOfDish) {
        this.typeOfDish = typeOfDish;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalTime getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(LocalTime cookingTime) {
        this.cookingTime = cookingTime;
    }

    public int getMaxNumberOfServings() {
        return maxNumberOfServings;
    }

    public void setMaxNumberOfServings(int maxNumberOfServings) {
        this.maxNumberOfServings = maxNumberOfServings;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (maxNumberOfServings != dish.maxNumberOfServings) return false;
        if (dishName != null ? !dishName.equals(dish.dishName) : dish.dishName != null) return false;
        if (typeOfDish != dish.typeOfDish) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        if (cookingTime != null ? !cookingTime.equals(dish.cookingTime) : dish.cookingTime != null) return false;
        return imagePath != null ? imagePath.equals(dish.imagePath) : dish.imagePath == null;
    }

    @Override
    public int hashCode() {
        int result = dishName != null ? dishName.hashCode() : 0;
        result = 31 * result + (typeOfDish != null ? typeOfDish.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (cookingTime != null ? cookingTime.hashCode() : 0);
        result = 31 * result + maxNumberOfServings;
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishName='" + dishName + '\'' +
                ", typeOfDish=" + typeOfDish +
                ", price=" + price +
                ", cookingTime=" + cookingTime +
                ", maxNumberOfServings=" + maxNumberOfServings +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

