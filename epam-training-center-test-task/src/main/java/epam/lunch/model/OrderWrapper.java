package epam.lunch.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "orders")
public class OrderWrapper {
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    @XmlElement(name = "order")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
