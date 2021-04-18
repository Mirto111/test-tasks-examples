package task;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.jdbc.JdbcRepo;
import task.model.Department;
import task.model.DepartmentWrapper;
import task.xml.XmlHandler;

import java.util.List;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Необходимо указать имя операции и путь к файлу");
            return;
        }

        XmlHandler xmlHandler = new XmlHandler();
        JdbcRepo jdbcRepo = new JdbcRepo();
        if (args[0].equals("sync")) {
            logger.info("Start sync");
            DepartmentWrapper dep = xmlHandler.unmarshall(args[1]);
            List<Department> dblist = jdbcRepo.getAll();
            SyncDb syncDb = new SyncDb();
            syncDb.sync(dep.getDepartments(), dblist);
        } else if (args[0].equals("unload")) {
            logger.info("Start unload");
            List<Department> list = jdbcRepo.getAll();
            xmlHandler.marshal(args[1], list);
        }
    }

}
