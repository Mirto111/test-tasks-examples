package epam.lunch.dto;

import epam.lunch.model.Dish;

public class DishWithQuantity {

    private final Dish dish;
    private final int quantity;

    public DishWithQuantity(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public int getQuantity() {
        return quantity;
    }
}
