package com.zarembin.epamjsp.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class Order extends Entity {

    private int orderId;
    private String userName;
    private LocalDateTime dateOfReceiving;
    private boolean isCashPayment;
    private BigDecimal orderCost;
    private Map<Dish,Integer> dishes;

    public Order(int orderId, String userName, LocalDateTime dateOfReceiving, boolean isCashPayment, BigDecimal orderCost, Map<Dish, Integer> dishes) {
        this.orderId = orderId;
        this.userName = userName;
        this.dateOfReceiving = dateOfReceiving;
        this.isCashPayment = isCashPayment;
        this.orderCost = orderCost;
        this.dishes = dishes;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getDateOfReceiving() {
        return dateOfReceiving;
    }

    public void setDateOfReceiving(LocalDateTime dateOfReceiving) {
        this.dateOfReceiving = dateOfReceiving;
    }

    public boolean isCashPayment() {
        return isCashPayment;
    }

    public void setCashPayment(boolean cashPayment) {
        isCashPayment = cashPayment;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (isCashPayment != order.isCashPayment) return false;
        if (userName != null ? !userName.equals(order.userName) : order.userName != null) return false;
        if (dateOfReceiving != null ? !dateOfReceiving.equals(order.dateOfReceiving) : order.dateOfReceiving != null)
            return false;
        if (orderCost != null ? !orderCost.equals(order.orderCost) : order.orderCost != null) return false;
        return dishes != null ? dishes.equals(order.dishes) : order.dishes == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (dateOfReceiving != null ? dateOfReceiving.hashCode() : 0);
        result = 31 * result + (isCashPayment ? 1 : 0);
        result = 31 * result + (orderCost != null ? orderCost.hashCode() : 0);
        result = 31 * result + (dishes != null ? dishes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", dateOfReceiving=" + dateOfReceiving +
                ", isCashPayment=" + isCashPayment +
                ", orderCost=" + orderCost +
                ", dishes=" + dishes +
                '}';
    }
}


