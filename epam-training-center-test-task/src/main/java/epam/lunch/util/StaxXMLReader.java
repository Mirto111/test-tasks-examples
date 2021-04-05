package epam.lunch.util;

import epam.lunch.App;
import epam.lunch.model.Customer;
import epam.lunch.model.Dish;
import epam.lunch.model.Order;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class StaxXMLReader {

    public static List<Dish> parseXmlMenu(String fileName) {
        List<Dish> empList = new ArrayList<>();
        Dish dish = null;
        ClassLoader cls = StaxXMLReader.class.getClassLoader();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(cls.getResourceAsStream(fileName));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    String startElement = xmlEvent.asStartElement().getName().getLocalPart();
                    if (startElement.equals("dish")) {
                        dish = new Dish();
                    }
                    else if (startElement.equals("name")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        dish.setName(xmlEvent.asCharacters().getData());
                    } else if (startElement.equals("weight")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        dish.setWeight(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.equals("price")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        dish.setPrice(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("dish")) {
                        empList.add(dish);
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }

    public static List<Order> parseXmlOrder(String fileName) {
        List<Order> empList = new ArrayList<>();
        List<Dish> dishes = null;
        Order emp = null;
        ClassLoader cls = StaxXMLReader.class.getClassLoader();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(cls.getResourceAsStream(fileName));

            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    String startElement = xmlEvent.asStartElement().getName().getLocalPart();
                    if (startElement.equals("order")) {
                        emp = new Order();
                        dishes = new ArrayList<>();
                        emp.setDishes(dishes);
                    }
                    else if (startElement.equals("customer")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        Customer customer = new Customer(xmlEvent.asCharacters().getData());
                        emp.setCustomer(customer);
                    }
                    else if (startElement.equals("name")) {
                        xmlEvent = xmlEventReader.nextEvent();
                        Dish dish = getByName(xmlEvent.asCharacters().getData());
                       dishes.add(dish);
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("order")) {
                        empList.add(emp);
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return empList;
    }

    private static Dish getByName(String name) {
        return App.dishes.stream().filter(x->x.getName().equals(name)).findFirst().get();
    }

}
