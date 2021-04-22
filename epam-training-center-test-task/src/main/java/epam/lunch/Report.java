package epam.lunch;

import epam.lunch.dto.DishWithQuantity;
import epam.lunch.dto.OrderWithPrice;
import epam.lunch.model.Dish;
import epam.lunch.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static epam.lunch.App.orders;

public class Report {

    public static List<DishWithQuantity> getAllOrderedDish() {
        Map<Dish, Integer> dishesWithQuantity = new HashMap<>();
        for (Order order : orders) {
            List<String> dishList = order.getDishes().stream().map(Dish::getName).collect(Collectors.toList());
            for (String name : dishList) {
                Dish dish = getByName(name);
                dishesWithQuantity.merge(dish, 1, Integer::sum);
            }
        }
        return dishesWithQuantity.entrySet().stream()
                .map(x -> new DishWithQuantity(x.getKey(), x.getValue())).collect(Collectors.toList());
    }

    public static List<OrderWithPrice> getAllOrders() {
        List<OrderWithPrice> list = new ArrayList<>();
        for (Order order : orders) {
            List<String> dishList = order.getDishes().stream().map(Dish::getName).collect(Collectors.toList());
            int totalprice = 0;
            for (String name : dishList) {
                Dish dish = getByName(name);
                totalprice += dish.getPrice();
            }
            list.add(new OrderWithPrice(order.getCustomer(), dishList, totalprice));
        }
        return list;
    }

    private static Dish getByName(String name) { // выбрасывает ошибку если не находим заказаного блюда в меню
        return App.dishes.stream().filter(x -> x.getName().equals(name)).findFirst().orElseThrow(() -> new IllegalArgumentException("Нет такого блюда в меню"));
    }
}
