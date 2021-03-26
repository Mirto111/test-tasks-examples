package epam.lunch;

import epam.lunch.model.Dish;
import epam.lunch.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static epam.lunch.App.orders;

public class Report {

    public static Map<Dish, Integer> getOrders(){
        Map<Dish, Integer> dishesWithQuantity= new HashMap<>();
        for (Order order : orders) {
            List<Dish> ddd = order.getDishes();
            for (Dish dish : ddd) {
                dishesWithQuantity.merge(dish, 1, Integer::sum);
            }
        }
        return dishesWithQuantity;
    }

    public static List<Order> getCustomers(){
        return orders;
    }
}
