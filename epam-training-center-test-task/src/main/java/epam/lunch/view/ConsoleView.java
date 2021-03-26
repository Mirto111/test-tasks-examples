package epam.lunch.view;

import epam.lunch.Report;
import epam.lunch.model.Dish;
import epam.lunch.model.Order;

import java.util.List;
import java.util.Map;

public class ConsoleView {


    public static void getOrders() {
        Map<Dish, Integer> orders = Report.getOrders();
        System.out.println("Название    Количество     Стоимость");
        int totalprice = 0;

        for (Map.Entry<Dish, Integer> entry : orders.entrySet()) {
            Dish x = entry.getKey();
            Integer y = entry.getValue();
            totalprice = totalprice + (x.getPrice() * y);
            System.out.println(x.getName() + "  " + y + "  " + x.getPrice() * y);
        }
        System.out.println("Общая сумма = " + "   " + totalprice);

    }

    public static void getCustomers() {
        List<Order> ddd = Report.getCustomers();

        for (Order order : ddd) {
            System.out.println("Имя сотрудника");
            System.out.println(order.getCustomer().getName());
            int totalprice = 0;
            System.out.println("Заказанные блюда");
            for (Dish dish : order.getDishes()) {
                totalprice += dish.getPrice();
                System.out.println(dish.getName());
            }
            System.out.println("Стоимость обеда  " + totalprice);
            System.out.println("--------");
        }
    }
}
