package com.zarembin.epampjsp.entity;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Dish extends Entity{
    private String dishName;
    private TypeOfDish typeOfDish;
    private BigDecimal price;
    private String imagePath;

    public Dish(String dishName, TypeOfDish typeOfDish, BigDecimal price, String imagePath) {
        this.dishName = dishName;
        this.typeOfDish = typeOfDish;
        this.price = price;
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

        if (dishName != null ? !dishName.equals(dish.dishName) : dish.dishName != null) return false;
        if (typeOfDish != dish.typeOfDish) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        return imagePath != null ? imagePath.equals(dish.imagePath) : dish.imagePath == null;
    }

    @Override
    public int hashCode() {
        int result = dishName != null ? dishName.hashCode() : 0;
        result = 31 * result + (typeOfDish != null ? typeOfDish.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishName='" + dishName + '\'' +
                ", typeOfDish=" + typeOfDish +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}

