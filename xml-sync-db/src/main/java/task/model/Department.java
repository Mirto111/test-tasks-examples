package task.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

/**
 * Класс сущности Department - используется для записи и чтения из БД и xml-файла
 */

public class Department {
    /**
     * Первичный ключ
     */
    private int id;
    /**
     * Составной ключ (код отдела-название должности(уникален))
     */
    private DepCodeJob depCodeJob;
    /**
     * Описание
     */
    private String description;

    public Department() {
    }
    public Department(DepCodeJob depCodeJob, String description) {
        this.depCodeJob = depCodeJob;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    @XmlTransient
    public void setId(int id) {
        this.id = id;
    }

    public DepCodeJob getDepCodeJob() {
        return depCodeJob;
    }

    @XmlElement
    public void setDepCodeJob(DepCodeJob depCodeJob) {
        this.depCodeJob = depCodeJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", depCodeJob=" + depCodeJob.getDepCode() + " " + depCodeJob.getDepJob() +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return depCodeJob.equals(that.depCodeJob) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCodeJob, description);
    }
}
