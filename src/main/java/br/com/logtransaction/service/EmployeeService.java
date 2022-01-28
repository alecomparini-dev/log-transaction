package br.com.logtransaction.service;
import java.util.List;
import br.com.logtransaction.model.Employee;

public interface EmployeeService {

    public List<Employee> findAll();
    public Employee findByCode(String code);
    public Employee save(Employee employee);
    public List<Employee> findEmployeeByRangeAge(Integer age1, Integer age2);
    
}
