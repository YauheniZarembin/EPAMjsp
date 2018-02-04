package com.zarembin.epampjsp.command;

import com.zarembin.epampjsp.entity.TypeOfDish;
import com.zarembin.epampjsp.exception.CommandException;
import com.zarembin.epampjsp.exception.ServiceException;
import com.zarembin.epampjsp.resource.ConfigurationManager;
import com.zarembin.epampjsp.service.MenuService;
import com.zarembin.epampjsp.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public class ChangePictureCommand implements ActionCommand {


    private static final String PARAM_CHOSEN_DISH = "chosenDish";
    private static final String PARAM_TYPE_DISH = "typeChosenDish";
    private static final String PARAM_DISHES = "dishes";

    private String pictureFileName;
    private MenuService receiver;

    public ChangePictureCommand(String pictureFileName, MenuService receiver) {
        this.pictureFileName = pictureFileName;
        this.receiver = receiver;
    }

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router();
        String page = null;
        String dishName = request.getParameter(PARAM_CHOSEN_DISH);
        TypeOfDish typeOfDish = TypeOfDish.valueOf(request.getParameter(PARAM_TYPE_DISH));

        try {
            receiver.uploadDishPicture(dishName,pictureFileName);
            request.getSession().setAttribute(PARAM_DISHES, receiver.findDishesByType(typeOfDish));

        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e.getCause());
        }

        page = ConfigurationManager.getProperty("path.page.adminMenu");
        router.setPagePath(page);
        return router;
    }
}
