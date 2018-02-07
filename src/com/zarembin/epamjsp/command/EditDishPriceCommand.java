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

public class EditDishPriceCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String PARAM_CHOSEN_DISH = "chosenDish";
    private static final String PARAM_TYPE_OF_DISH = "typeChosenDish";
    private static final String PARAM_NEW_PRICE = "newPrice";
    private static final  String PARAM_MESSAGE ="Message";
    private static final  String PARAM_DISHES ="dishes";
    private static final String PARAM_LOCALE = "changeLanguage";

    private MenuService receiver;

    public EditDishPriceCommand(MenuService receiver) {
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page;
        String dishName = request.getParameter(PARAM_CHOSEN_DISH);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_TYPE_OF_DISH));
        String priceString = request.getParameter(PARAM_NEW_PRICE);
        MessageManager messageManager = MessageManager.defineLocale((String) request.getSession().getAttribute(PARAM_LOCALE));

        try {
            InputTextValidator inputTextValidator = new InputTextValidator();
            if (inputTextValidator.isMoneyValid(priceString)) {
                receiver.changeDishPrice(dishName,priceString);
                LOGGER.log(Level.INFO,"Changing dish price");
                request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
            } else {
                request.setAttribute(PARAM_MESSAGE,
                        messageManager.getMessage("message.moneyError"));
                request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));
            }
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }

        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
