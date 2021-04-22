package epam.lunch.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "menu")
public class DishWrapper {
    private List<Dish> dishes;

    public List<Dish> getDishes() {
        return dishes;
    }

    @XmlElement(name = "dish")
    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
