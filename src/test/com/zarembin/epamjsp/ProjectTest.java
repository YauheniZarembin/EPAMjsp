package test.com.zarembin.epamjsp;

import com.zarembin.epamjsp.pool.ConnectionDB;
import com.zarembin.epamjsp.validator.InputTextValidator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import test.com.zarembin.epamjsp.util.ConstantForTest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ProjectTest {

    private static InputTextValidator validator;

    @BeforeClass
    public static void initText() throws IOException {
        validator = new InputTextValidator();
    }

    @AfterClass
    public static void deleteText() {
        validator = null;
    }

    @Test
    public void moneyValidatorTest() {
        assertFalse(validator.isMoneyValid(ConstantForTest.MONEY_TEST));
    }

    @Test
    public void emailValidatorTest() {
        assertTrue(validator.isEmailValid(ConstantForTest.EMAIl_TEST));
    }

    @Test(expectedExceptions = SQLException.class)
    public void getConnectionTest() throws SQLException {
        Connection expected = null;
        assertEquals(ConnectionDB.getConnection(ConstantForTest.URL_TEST,ConstantForTest.USER_NAME_TEST,ConstantForTest.USER_PASSWORD_TEST),expected);
    }



}