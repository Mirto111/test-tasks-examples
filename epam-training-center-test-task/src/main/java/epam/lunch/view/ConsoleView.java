package epam.lunch.view;

import epam.lunch.Report;
import epam.lunch.dto.DishWithQuantity;
import epam.lunch.dto.OrderWithPrice;

import java.util.List;

public class ConsoleView {


    public static void getAllOrderedDish() {
        List<DishWithQuantity> orders = Report.getAllOrderedDish();
        System.out.println("Название    Количество     Стоимость");
        int totalprice = 0;

        for (DishWithQuantity d : orders) {
            totalprice = totalprice + (d.getDish().getPrice() * d.getQuantity());
            System.out.println(d.getDish().getName() + "  " + d.getQuantity() + "  " + d.getDish().getPrice() * d.getQuantity());
        }
        System.out.println("Общая сумма = " + "   " + totalprice);
    }

    public static void getAllOrders() {
        List<OrderWithPrice> list = Report.getAllOrders();
        for (OrderWithPrice order : list) {
            System.out.println("Имя сотрудника");
            System.out.println(order.getCustomer().getName());
            System.out.println("Заказанные блюда");
            System.out.println(order.getDishes());
            System.out.println("Стоимость обеда  " + order.getTotalPrice());
            System.out.println("--------");
        }
    }
}
