package com.zarembin.epamjsp.command;

import com.zarembin.epamjsp.entity.TypeOfDish;
import com.zarembin.epamjsp.exception.CommandException;
import com.zarembin.epamjsp.exception.ServiceException;
import com.zarembin.epamjsp.resource.ConfigurationManager;
import com.zarembin.epamjsp.resource.MessageManager;
import com.zarembin.epamjsp.service.MenuService;
import com.zarembin.epamjsp.servlet.Router;
import com.zarembin.epamjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminAddDishCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_DISH_NAME= "dishName";
    private static final String PARAM_DISH_TYPE = "dishType";
    private static final String PARAM_DISH_PRISE = "dishPrice";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_DISHES = "dishes";
    private static final String PARAM_LOCALE = "changeLanguage";

    private MenuService receiver;

    public AdminAddDishCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        Router router = new Router();
        String page;
        String dishName = request.getParameter(PARAM_DISH_NAME);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_DISH_TYPE).toUpperCase());
        String dishPriceString = request.getParameter(PARAM_DISH_PRISE);
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));

        InputTextValidator inputTextValidator = new InputTextValidator();
        if (inputTextValidator.isMoneyValid(dishPriceString)) {
            try {
                if(receiver.findDishByName(dishName) == null){
                        receiver.insertNewDish(dishName,typeOfDish,dishPriceString);
                        request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
                        page = ConfigurationManager.getProperty("path.page.adminMenu");
                }
                else{
                    request.setAttribute(PARAM_ERROR_MESSAGE,
                            messageManager.getMessage("message.dishExist"));
                    page = ConfigurationManager.getProperty("path.page.adminAddDish");
                }
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }
        else {
            request.setAttribute(PARAM_ERROR_MESSAGE,
                    messageManager.getMessage("message.moneyError"));
            page = ConfigurationManager.getProperty("path.page.adminAddDish");
        }

        router.setPagePath(page);
        LOGGER.log(Level.INFO,"Admin add new dish");
        return router;
    }
}
