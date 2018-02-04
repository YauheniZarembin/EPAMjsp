package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.User;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.UserService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class TopUpCommand implements ActionCommand {

    private static final String PARAM_USER = "user";
    private static final String PARAM_CARD_PASSWORD ="cardPassword";
    private static final  String PARAM_ADD_MONEY ="newMoney";
    private static final  String PARAM_MESSAGE ="MessageTopUp";

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
        MessageManager messageManager = MessageManager.defineLocale(request);


        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isMoneyValid(moneyString)) {
            BigDecimal moneyForTopUp = new BigDecimal(moneyString);
            try {
                BigDecimal bankMoney = receiver.findMoneyBank(user.getCardNumber(),cardPassword);
                if (bankMoney != null) {
                    if (bankMoney.compareTo(moneyForTopUp) == 1) {
                        user = receiver.topUpMoney(bankMoney,moneyForTopUp,user);
                        request.getSession().setAttribute(PARAM_USER,user);
                        page = ConfigurationManager.getProperty("path.page.myProfile");
                        router.setRoute(Router.RouteType.REDIRECT);
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
                throw new CommandException(e.getMessage(), e.getCause());
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
