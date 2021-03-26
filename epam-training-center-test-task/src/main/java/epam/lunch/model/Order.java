package epam.lunch.model;

import java.util.List;

public class Order {

    private List<Dish> dishes;
    private Customer customer;

    public Order() {
    }

    public Order(List<Dish> dishes, Customer customer) {
        this.dishes = dishes;
        this.customer = customer;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Order{" +
                "dishes=" + dishes +
                ", customerName=" + customer +
                '}';
    }
}
