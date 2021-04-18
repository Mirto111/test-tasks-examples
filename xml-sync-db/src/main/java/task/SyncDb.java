package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.jdbc.JdbcRepo;
import task.model.DepCodeJob;
import task.model.Department;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Класс для подготовки синхронизированных данных перед изменением БД
 */
public class SyncDb {

    private static final Logger logger = LoggerFactory.getLogger(SyncDb.class);

    private final Map<DepCodeJob, Department> xmlFile = new HashMap<>();
    private final JdbcRepo jdbcRepo = new JdbcRepo();
    private Map<DepCodeJob, Department> db = new HashMap<>();


    /**
     * Сравниваем два списка и находим различия
     *
     * @param xmlList - список сущностей из xml-файла
     * @param dbList  - список сущностей из БД
     */
    public void sync(List<Department> xmlList, List<Department> dbList) {
        logger.info("Preparation for sync");
        for (Department depart : xmlList) { // должна быть ошибка если вставляем дубликат
            if (xmlFile.containsKey(depart.getDepCodeJob()))
                throw new IllegalArgumentException("В файле содержится повторный натуральный ключ  " + depart);
            xmlFile.put(depart.getDepCodeJob(), depart);
        }
        db = dbList.stream().collect(Collectors.toMap(Department::getDepCodeJob, Function.identity()));

        List<Department> updateList = new ArrayList<>();
        List<Department> insertList = new ArrayList<>();
        List<Department> deleteList = new ArrayList<>();

        for (Map.Entry<DepCodeJob, Department> entry : xmlFile.entrySet()) {
            Department dep;
            if ((dep = db.get(entry.getKey())) != null) {
                if (!entry.getValue().equals(dep)) {
                    entry.getValue().setId(dep.getId());
                    updateList.add(entry.getValue());
                }
            } else {
                insertList.add(entry.getValue());
            }
        }
        Set<DepCodeJob> set = db.keySet();
        set.removeAll(xmlFile.keySet());
        set.forEach(x -> deleteList.add(db.get(x)));

        jdbcRepo.syncDb(insertList, updateList, deleteList);
    }

}
