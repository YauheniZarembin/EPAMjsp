package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.resource.MessageManager;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;
import com.zarembin.epampjsp.validator.InputTextValidator;

import javax.servlet.http.HttpServletRequest;

public class AdminAddDishCommand implements ActionCommand {

    private static final String PARAM_DISH_NAME= "dishName";
    private static final String PARAM_DISH_TYPE = "dishType";
    private static final String PARAM_DISH_PRISE = "dishPrice";
    private static final String PARAM_ERROR_MESSAGE = "errorMessage";
    private static final String PARAM_DISHES = "dishes";

    private MenuService receiver;

    public AdminAddDishCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        Router router = new Router();
        String page = null;
        String dishName = request.getParameter(PARAM_DISH_NAME);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_DISH_TYPE).toUpperCase());
        String dishPriceString = request.getParameter(PARAM_DISH_PRISE);
        MessageManager messageManager = MessageManager.defineLocale(request);

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
                throw new CommandException(e.getMessage(), e.getCause());
            }
        }
        else {
            request.setAttribute(PARAM_ERROR_MESSAGE,
                    messageManager.getMessage("message.moneyError"));
            page = ConfigurationManager.getProperty("path.page.adminAddDish");
        }

        router.setPagePath(page);
        return router;
    }
}
