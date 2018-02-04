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

public class EditDishPriceCommand implements ActionCommand {

    private static final String PARAM_CHOSEN_DISH = "chosenDish";
    private static final String PARAM_TYPE_OF_DISH = "typeChosenDish";
    private static final String PARAM_NEW_PRICE = "newPrice";
    private static final  String PARAM_MESSAGE ="Message";
    private static final  String PARAM_DISHES ="dishes";

    private MenuService receiver;

    public EditDishPriceCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page = null;
        String dishName = request.getParameter(PARAM_CHOSEN_DISH);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_TYPE_OF_DISH));
        String priceString = request.getParameter(PARAM_NEW_PRICE);
        MessageManager messageManager = MessageManager.defineLocale(request);

        try {
            InputTextValidator inputTextValidator = new InputTextValidator();
            if (inputTextValidator.isMoneyValid(priceString)) {
                receiver.changeDishPrice(dishName,priceString);
                request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
            } else {
                request.setAttribute(PARAM_MESSAGE,
                        messageManager.getMessage("message.moneyError"));
                request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }

        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
