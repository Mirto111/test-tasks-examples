package epam.lunch.util;

import epam.lunch.model.DishWrapper;
import epam.lunch.model.OrderWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.InputStream;

public class JaxbHandler {

    public DishWrapper readMenu(String file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(DishWrapper.class);
        InputStream in = getClass().getResourceAsStream("/" + file);
        return (DishWrapper) context.createUnmarshaller().unmarshal(in);
    }

    public OrderWrapper readOrders(String file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(OrderWrapper.class);
        InputStream in = getClass().getResourceAsStream("/" + file);
        return (OrderWrapper) context.createUnmarshaller().unmarshal(in);
    }

}
