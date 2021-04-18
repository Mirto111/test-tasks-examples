package task.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *  Класс обертка для Department
 */

@XmlRootElement(name = "departments")
public class DepartmentWrapper {

    public DepartmentWrapper() {
    }

    public DepartmentWrapper(List<Department> departments) {
        this.departments = departments;
    }

    private List<Department> departments;

    @XmlElement(name = "department")
    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }


}
