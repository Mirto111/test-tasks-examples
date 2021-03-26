package epam.lunch;

import epam.lunch.model.Dish;
import epam.lunch.model.Order;
import epam.lunch.view.ConsoleView;

import java.util.ArrayList;
import java.util.List;

import static epam.lunch.util.StaxXMLReader.parseXmlMenu;
import static epam.lunch.util.StaxXMLReader.parseXmlOrder;

public class App {

    public static List<Dish> dishes = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
        String menu = "menu.xml";
        String order = "orders.xml";
        dishes = parseXmlMenu(menu);
        orders = parseXmlOrder(order);
        ConsoleView.getOrders();
        ConsoleView.getCustomers();
    }


}
