package epam.lunch.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class Order {

    private Customer customer;
    private List<Dish> dishes;

    public Order() {
    }

    public Customer getCustomer() {
        return customer;
    }

    @XmlElement(name = "customer")
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "dish")
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
