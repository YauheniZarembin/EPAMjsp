package com.zarembin.epampjsp.entity;

import java.time.LocalDateTime;

public class Order extends Entity {

    private int orderId;
    private String userName;
    private LocalDateTime dateOfReceiving;
    private boolean isCashPayment;

    public Order(int orderId, String userName, LocalDateTime dateOfReceiving, boolean isCashPayment) {
        this.orderId = orderId;
        this.userName = userName;
        this.dateOfReceiving = dateOfReceiving;
        this.isCashPayment = isCashPayment;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;
        if (isCashPayment != order.isCashPayment) return false;
        if (userName != null ? !userName.equals(order.userName) : order.userName != null) return false;
        return dateOfReceiving != null ? dateOfReceiving.equals(order.dateOfReceiving) : order.dateOfReceiving == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (dateOfReceiving != null ? dateOfReceiving.hashCode() : 0);
        result = 31 * result + (isCashPayment ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userName='" + userName + '\'' +
                ", dateOfReceiving=" + dateOfReceiving +
                ", isCashPayment=" + isCashPayment +
                '}';
    }
}
