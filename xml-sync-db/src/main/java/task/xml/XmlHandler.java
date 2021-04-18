package task.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.App;
import task.model.Department;
import task.model.DepartmentWrapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;


/**
 * Класс для чтения и записи в xml-файл посредством JAXB
 */
public class XmlHandler {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Читаем и преобразовываем данные из xml-файла в сущность
     *
     * @param fileName - имя файла
     * @return DepartmentWrapper
     * @throws JAXBException если какие то проблемы файлом
     */
    public DepartmentWrapper unmarshall(String fileName) throws JAXBException {
        logger.info("Read from Xml file");
        JAXBContext context = JAXBContext.newInstance(DepartmentWrapper.class);
        return (DepartmentWrapper) context.createUnmarshaller()
                .unmarshal(new File(fileName));
    }

    /**
     * Записываем список сущностей в xml-файл
     *
     * @param fileName - имя файла
     * @param depList  - список сущностей класса Department
     * @throws JAXBException если какие то проблемы при записи
     */
    public void marshal(String fileName, List<Department> depList) throws JAXBException {
        logger.info("Write to Xml file");
        JAXBContext context = JAXBContext.newInstance(DepartmentWrapper.class);
        Marshaller mar = context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        DepartmentWrapper wrapper = new DepartmentWrapper(depList);
        mar.marshal(wrapper, new File(fileName));
        logger.info("Was recorded " + depList.size() + " entries");
    }
}
