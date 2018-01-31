package com.zarembin.epampjsp.service;

import com.zarembin.epampjsp.dao.*;
import com.zarembin.epampjsp.encryptor.Encryption;
import com.zarembin.epampjsp.entity.Order;
import com.zarembin.epampjsp.entity.Review;
import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.DAOException;
import com.zarembin.epampjsp.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final String REG_EX_JSP = "/jsp.+";

    public boolean isSuchUserExist(String login) throws ServiceException {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.isSuchUserExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public boolean isSuchCardExist(String cardNumber , String cardPassword) throws ServiceException {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.isSuchCardExist(cardNumber,cardPassword);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }




    public User findUserByEncryption(String login, String password) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        try {
            return authenticationDAO.findUser(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void singUpUserByEncryption(String userName, String password, String name, String lastname, String email, String cardNumber) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            registrationDAO.insertNewUser(userName, password, name, lastname, email, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Order> findOrdersByName(String userName) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findOrdersByName(userName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public List<Review> findReviews() throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            return reviewsDAO.findReviews();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void insertReview(String userName, int mark, String textReview) throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            reviewsDAO.insertNewReview(userName, mark, textReview);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public void deleteReview(int reviewId) throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            reviewsDAO.deleteReview(reviewId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public BigDecimal findMoneyBank(String loginCard, String passwordCard) throws ServiceException {
        MoneyDAO moneyDAO = new MoneyDAO();
        try {
            return moneyDAO.findMoneyFromCard(loginCard, passwordCard);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public User topUpMoney(BigDecimal bankMoney, BigDecimal moneyForTopUp, User user) throws ServiceException {

        BigDecimal newUserMoney = user.getMoney().add(moneyForTopUp);
        BigDecimal newBankMoney = bankMoney.subtract(moneyForTopUp);
        user.setMoney(newUserMoney);
        MoneyDAO moneyDAO = new MoneyDAO();
        try {
            moneyDAO.topUpUserMoney(newBankMoney, newUserMoney, user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
        return user;
    }

    public boolean checkDateTimeOrder(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }
        if (!(LocalDateTime.now().until(localDateTime, ChronoUnit.HOURS) > 0) || !(LocalDateTime.now().until(localDateTime, ChronoUnit.DAYS) < 8)) {
            return false;
        }
        switch (localDateTime.getDayOfWeek()) {
            case SUNDAY:
                return false;
            case SATURDAY:
                if (!(localDateTime.getHour() >= 10) || !(localDateTime.getHour() <= 23)) {
                    return false;
                }
                break;
            default:
                if (!(localDateTime.getHour() >= 10) || !(localDateTime.getHour() <= 21)) {
                    return false;
                }
                break;
        }
        return true;
    }

    public void makeOrder(Order order, User user) throws ServiceException {

        OrderingDAO orderingDAO = new OrderingDAO();
        int newLoyaltyPoints = user.getLoyaltyPoints();
        long untilDay = LocalDateTime.now().until(order.getDateOfReceiving(), ChronoUnit.DAYS);
        if (untilDay >= 1 && untilDay <= 3) {
            newLoyaltyPoints++;
        } else if (untilDay >= 4) {
            newLoyaltyPoints += 3;
        }
        BigDecimal newUserMoney = (order.isCashPayment()) ? user.getMoney() : user.getMoney().subtract(order.getOrderCost());
        int newOrdersAmount = user.getNumberOfOrders();
        newOrdersAmount++;

        try {
            user.setLoyaltyPoints(newLoyaltyPoints);
            user.setMoney(newUserMoney);
            user.setNumberOfOrders(newOrdersAmount);
            orderingDAO.makeOrder(newUserMoney, newLoyaltyPoints, newOrdersAmount, order);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    public String changeLanguage(String locale){
        if (locale == null){
            return "en_US";
        }
        return ("en_US").equals(locale) ? "ru_RU" : "en_US";
    }

    public String returnSamePage(String pagePath){

        String page = null;
        Pattern p = Pattern.compile(REG_EX_JSP);
        Matcher m = p.matcher(pagePath);
        if(m.find()){
            page = m.group();
        }
        return page;
    }
}