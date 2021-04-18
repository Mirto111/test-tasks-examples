package task.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

/**
 * Класс для описания составного ключа БД и для проверки уникальности ключа в xml-файле
 */
public class DepCodeJob {

    /**
     * Код отдела
     */
    private String depCode;
    /**
     * Название должности в отделе
     */
    private String depJob;

    public DepCodeJob() {
    }

    public DepCodeJob(String depCode, String depJob) {
        this.depCode = depCode;
        this.depJob = depJob;
    }

    public String getDepCode() {
        return depCode;
    }

    @XmlElement(name = "depcode")
    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    @XmlElement(name = "depjob")
    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepCodeJob that = (DepCodeJob) o;
        return Objects.equals(depCode, that.depCode) &&
                Objects.equals(depJob, that.depJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depCode, depJob);
    }
}
