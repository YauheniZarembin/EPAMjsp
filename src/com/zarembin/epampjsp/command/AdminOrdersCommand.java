//package com.zarembin.epampjsp.command;
//
//import com.zarembin.epampjsp.exception.ServiceException;
//import com.zarembin.epampjsp.resource.ConfigurationManager;
//import com.zarembin.epampjsp.service.AdminService;
//import com.zarembin.epampjsp.servlet.Router;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//public class AdminOrdersCommand implements ActionCommand {
//
//    private AdminService receiver;
//    private static final String PARAM_ORDERS = "orders";
//
//    public AdminOrdersCommand(AdminService receiver) {
//        this.receiver = receiver;
//    }
//
//    @Override
//    public Router execute(HttpServletRequest request) {
//        Router router = new Router();
//        String page;
//        try {
//            request.getSession().setAttribute(PARAM_ORDERS,receiver.findAllOrders());
//        } catch (ServiceException e) {
//            ////////    как ошибку отправлять Forfard или REDIRECT  ???????????
//            page = ConfigurationManager.getProperty("path.page.login");
//            router.setPagePath(page);
//            return router;
//        }
//
//        page = ConfigurationManager.getProperty("path.page.adminOrders");
//        router.setPagePath(page);
//        return router;
//    }
//}
