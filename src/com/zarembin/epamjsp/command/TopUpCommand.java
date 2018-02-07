package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.User;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;
import com.zarembin.epamjsp.service.UserService;
import com.zarembin.epamjsp.servlet.Router;
import com.zarembin.epamjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopUpCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_USER = "user";
    private static final String PARAM_CARD_PASSWORD ="cardPassword";
    private static final  String PARAM_ADD_MONEY ="newMoney";
    private static final  String PARAM_MESSAGE ="MessageTopUp";
    private static final String PARAM_LOCALE = "changeLanguage";

    private UserService receiver;

    public TopUpCommand(UserService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        User user = (User) request.getSession().getAttribute(PARAM_USER);
        String cardPassword = request.getParameter(PARAM_CARD_PASSWORD);
        String moneyString = request.getParameter(PARAM_ADD_MONEY);
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));


        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isMoneyValid(moneyString)) {
            BigDecimal moneyForTopUp = new BigDecimal(moneyString);
            try {
                BigDecimal bankMoney = receiver.findMoneyBank(user.getCardNumber(),cardPassword);
                if (bankMoney != null) {
                    if (bankMoney.compareTo(moneyForTopUp) >= 0) {
                        user = receiver.topUpMoney(bankMoney,moneyForTopUp,user);
                        request.getSession().setAttribute(PARAM_USER,user);
                        page = ConfigurationManager.getProperty("path.page.myProfile");
                        router.setRoute(Router.RouteType.REDIRECT);
                        LOGGER.log(Level.INFO,user.getUserName()+" topped up account for "+moneyString);
                    } else {
                        request.setAttribute(PARAM_MESSAGE,
                                messageManager.getMessage("message.notEnoughMoney"));
                        page = ConfigurationManager.getProperty("path.page.myProfile");
                    }
                }
                else{
                    request.setAttribute(PARAM_MESSAGE,
                            messageManager.getMessage("message.cardPasswordError"));
                    page = ConfigurationManager.getProperty("path.page.myProfile");

                }
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }
        else{
            request.setAttribute(PARAM_MESSAGE,
                    messageManager.getMessage("message.moneyError"));
            page = ConfigurationManager.getProperty("path.page.myProfile");
        }
        router.setPagePath(page);
        return router;
    }
}
