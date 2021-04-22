package epam.lunch;

import epam.lunch.model.Dish;
import epam.lunch.model.Order;
import epam.lunch.util.JaxbHandler;
import epam.lunch.view.ConsoleView;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static List<Dish> dishes = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String menu = "menu.xml";
        String ordersList = "orders.xml";
        JaxbHandler jxb = new JaxbHandler();
        dishes = jxb.readMenu(menu).getDishes();
        orders = jxb.readOrders(ordersList).getOrders();
        ConsoleView.getAllOrderedDish();
        ConsoleView.getAllOrders();
    }


}
