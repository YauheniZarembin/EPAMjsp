package test.com.zarembin.epamjsp.dao;

import com.zarembin.epamjsp.dao.MenuDAO;
import com.zarembin.epamjsp.dao.ReviewsDAO;
import com.zarembin.epamjsp.entity.Dish;
import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.DAOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestDAO {
    private static MenuDAO menuDAO = new MenuDAO();

    @BeforeClass
    public static void addDish() throws DAOException {
        menuDAO.insertNewDish("Vodka", TypeOfDish.DRINK , "3.22");
    }
    @AfterClass
    public static void deleteBall() throws DAOException {
        menuDAO.deleteDish("Vodka");
    }
    @Test
    public void findDishTest() throws DAOException {
        BigDecimal price = new BigDecimal(3.22);
        price.setScale(2,BigDecimal.ROUND_HALF_UP);
        Dish expected = new Dish("Vodka",TypeOfDish.DRINK,price,"\\resource\\image\\not.jpg",false);
        Dish actual = menuDAO.findDishByName("Vodka");
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected, actual);
    }

}
