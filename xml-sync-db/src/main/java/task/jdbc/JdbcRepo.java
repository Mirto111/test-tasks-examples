package task.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task.model.DepCodeJob;
import task.model.Department;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Класс для работы с базой данных посредством JDBC
 */

public class JdbcRepo {
    private static final Logger logger = LoggerFactory.getLogger(JdbcRepo.class);


    /**
     * Возвращает соединение с БД
     * (url, юзер и пароль берутся из проперти файла)
     *
     * @return a connection
     * @throws SQLException - если проблемы с БД
     */
    private Connection getConnect() throws SQLException {
        logger.info("Connecting to DB");
        Properties props = getProperties();
        Connection con = DriverManager
                .getConnection(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        return con;
    }

    /**
     * Возвращает полный список всех сущностей-департаментов
     *
     * @return List всех сущностей
     */
    public List<Department> getAll() {
        String query = "SELECT id, dep_code, dep_job, description FROM department";
        List<Department> departments = new ArrayList<>();
        try (PreparedStatement statement = getConnect().prepareStatement(query)) {
            logger.info("Select All from DB");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String depCode = rs.getString("dep_code");
                String depJob = rs.getString("dep_job");
                String description = rs.getString("description");
                Department dep = new Department();
                dep.setId(id);
                dep.setDepCodeJob(new DepCodeJob(depCode, depJob));
                dep.setDescription(description);
                departments.add(dep);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return departments;
    }

    /**
     * Возвращает PreparedStatement с включенными в него сущностями для вставки
     *
     * @param departments - список сущностей для вставки
     * @param con         - соединение с БД
     * @return PreparedStatement
     * @throws SQLException - если проблемы с БД
     */
    private PreparedStatement insert(List<Department> departments, Connection con) throws SQLException {
        logger.info("Insert into DB");
        String query = "INSERT INTO department(" +
                "dep_code," +
                "dep_job, description) " +
                "VALUES (?, ?, ?);";

        PreparedStatement st = con.prepareStatement(query);
        for (Department d : departments) {
            st.setString(1, d.getDepCodeJob().getDepCode());
            st.setString(2, d.getDepCodeJob().getDepJob());
            st.setString(3, d.getDescription());
            st.addBatch();
        }
        return st;
    }

    /**
     * Возвращает PreparedStatement с включенными в него сущностями для обновления
     *
     * @param departments - список сущностей для обновления
     * @param con         - соединение с БД
     * @return PreparedStatement
     * @throws SQLException - если проблемы с БД
     */
    private PreparedStatement update(List<Department> departments, Connection con) throws SQLException {
        logger.info("Update DB");
        String query = "UPDATE department SET " +
                "dep_code=?," +
                "dep_job=?, description=? " +
                "WHERE id=?";

        PreparedStatement st = con.prepareStatement(query);
        for (Department d : departments) {
            st.setString(1, d.getDepCodeJob().getDepCode());
            st.setString(2, d.getDepCodeJob().getDepJob());
            st.setString(3, d.getDescription());
            st.setInt(4, d.getId());
            st.addBatch();
        }
        return st;
    }

    /**
     * Возвращает PreparedStatement с включенными в него сущностями для удаления
     *
     * @param departments - список сущностей для удаления
     * @param con         - соединение с БД
     * @return PreparedStatement
     * @throws SQLException - если проблемы с БД
     */
    private PreparedStatement delete(List<Department> departments, Connection con) throws SQLException {
        logger.info("Delete from DB");
        String query = "DELETE FROM department WHERE id=?";
        PreparedStatement st = con.prepareStatement(query);
        for (Department d : departments) {
            st.setInt(1, d.getId());
            st.addBatch();
        }
        return st;
    }

    /**
     * Синхронизируем xml-файл и БД в одной транзакции
     *
     * @param insert - список сущностей для вставки
     * @param update - список сущностей для обновления
     * @param delete - список сущностей для удаления
     */
    public void syncDb(List<Department> insert, List<Department> update, List<Department> delete) {
        logger.info("Sync DB");
        try (Connection con = getConnect();
             PreparedStatement ins = insert(insert, con);
             PreparedStatement upd = update(update, con);
             PreparedStatement del = delete(delete, con)) {

            con.setAutoCommit(false);
            // можно проверить списки на нулевой размер и тогда уже выполнять
            ins.executeBatch();
            upd.executeBatch();
            del.executeBatch();
            con.commit();
            con.setAutoCommit(true);
            logger.info("Insert " + insert.size() + " records");
            logger.info("Update " + update.size() + " records");
            logger.info("Delete " + delete.size() + " records");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Читаем файл настроек и возвращаем их в виде сущности
     *
     * @return сущность класса Properties с загруженными настройками
     */
    private Properties getProperties() {
        logger.info("Read properties");
        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("/app.properties"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }

}


