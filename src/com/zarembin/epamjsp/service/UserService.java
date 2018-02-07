package com.zarembin.epamjsp.service;

import com.zarembin.epamjsp.dao.*;
import com.zarembin.epamjsp.encryptor.Encryption;
import com.zarembin.epamjsp.entity.Order;
import com.zarembin.epamjsp.entity.Review;
import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.DAOException;
import com.zarembin.epamjsp.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final String REG_EX_JSP = "/jsp.+";
    private static final String ENGLISH_LOCALE = "en_US";
    private static final String RUSSIAN_LOCALE = "ru_RU";
    private static final int LOWER_BOUND_TIME_LIMIT = 0;
    private static final int UPPER_BOUND_TIME_LIMIT = 8;
    private static final int LOWER_BOUND_WORK_TIME = 10;
    private static final int UPPER_BOUND_WORK_TIME_WEEK_DAY = 21;
    private static final int UPPER_BOUND_WORK_TIME_SATURDAY = 23;
    private static final int MIN_DAY_FOR_MIN_BONUS = 1;
    private static final int MAX_DAY_FOR_MIN_BONUS = 3;
    private static final int MIN_DAY_FOR_MAX_BONUS = 4;
    private static final int MAX_BONUS = 3;
    private static final int POINT_FOR_FINE = 50;
    private static final int FINE = 10;

    public boolean isSuchUserExist(String login) throws ServiceException {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.isSuchUserExist(login);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public boolean isSuchCardExist(String cardNumber , String cardPassword) throws ServiceException {
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            return registrationDAO.isSuchCardExist(cardNumber,cardPassword);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public User findUserByEncryption(String login, String password) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        AuthenticationDAO authenticationDAO = new AuthenticationDAO();
        try {
            return authenticationDAO.findUser(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void singUpUserByEncryption(String userName, String password, String name, String lastname, String email, String cardNumber) throws ServiceException {
        Encryption encryption = new Encryption();
        password = encryption.encrypt(password);
        RegistrationDAO registrationDAO = new RegistrationDAO();
        try {
            registrationDAO.insertNewUser(userName, password, name, lastname, email, cardNumber);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Order> findOrdersByName(String userName) throws ServiceException {
        OrdersListDAO ordersListDAO = new OrdersListDAO();
        try {
            return ordersListDAO.findOrdersByName(userName);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public List<Review> findReviews() throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            return reviewsDAO.findReviews();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void insertReview(String userName, int mark, String textReview) throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            reviewsDAO.insertNewReview(userName, mark, textReview);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public void deleteReview(int reviewId) throws ServiceException {
        ReviewsDAO reviewsDAO = new ReviewsDAO();
        try {
            reviewsDAO.deleteReview(reviewId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public BigDecimal findMoneyBank(String loginCard, String passwordCard) throws ServiceException {
        MoneyDAO moneyDAO = new MoneyDAO();
        try {
            return moneyDAO.findMoneyFromCard(loginCard, passwordCard);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
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
            throw new ServiceException(e.getMessage(), e);
        }
        return user;
    }

    public boolean checkDateTimeOrder(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return false;
        }
        if ((LocalDateTime.now().until(localDateTime, ChronoUnit.HOURS) <= LOWER_BOUND_TIME_LIMIT) ||
                (LocalDateTime.now().until(localDateTime, ChronoUnit.DAYS) >= UPPER_BOUND_TIME_LIMIT)) {
            return false;
        }
        switch (localDateTime.getDayOfWeek()) {
            case SUNDAY:
                return false;
            case SATURDAY:
                if ((localDateTime.getHour() < LOWER_BOUND_WORK_TIME) || (localDateTime.getHour() > UPPER_BOUND_WORK_TIME_SATURDAY)) {
                    return false;
                }
                break;
            default:
                if ((localDateTime.getHour() < LOWER_BOUND_WORK_TIME) || (localDateTime.getHour() > UPPER_BOUND_WORK_TIME_WEEK_DAY)) {
                    return false;
                }
                break;
        }
        return true;
    }

    public void makeOrder(Order order, User user) throws ServiceException {

        OrderingDAO orderingDAO = new OrderingDAO();
        BigDecimal newUserMoney = user.getMoney();
        int newLoyaltyPoints = user.getLoyaltyPoints();
        long untilDay = LocalDateTime.now().until(order.getDateOfReceiving(), ChronoUnit.DAYS);
        if (untilDay >= MIN_DAY_FOR_MIN_BONUS && untilDay <= MAX_DAY_FOR_MIN_BONUS) {
            newLoyaltyPoints++;
        } else if (untilDay >= MIN_DAY_FOR_MAX_BONUS) {
            newLoyaltyPoints += MAX_BONUS;
        }
        if(newLoyaltyPoints >= POINT_FOR_FINE){
            newLoyaltyPoints -= POINT_FOR_FINE;
            newUserMoney = newUserMoney.add(new BigDecimal(FINE));
        }
        newUserMoney = (order.isCashPayment()) ? newUserMoney : newUserMoney.subtract(order.getOrderCost());
        int newOrdersAmount = user.getNumberOfOrders();
        newOrdersAmount++;

        try {
            user.setLoyaltyPoints(newLoyaltyPoints);
            user.setMoney(newUserMoney);
            user.setNumberOfOrders(newOrdersAmount);
            orderingDAO.makeOrder(newUserMoney, newLoyaltyPoints, newOrdersAmount, order);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    public String changeLanguage(String locale){
        if (locale == null){
            return ENGLISH_LOCALE;
        }
        return ENGLISH_LOCALE.equals(locale) ? RUSSIAN_LOCALE : ENGLISH_LOCALE;
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