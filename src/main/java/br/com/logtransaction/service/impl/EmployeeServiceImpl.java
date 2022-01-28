package br.com.logtransaction.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.logtransaction.model.Employee;
import br.com.logtransaction.repository.EmployeeRepository;
import br.com.logtransaction.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;   
    

    @Override
    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public Employee findByCode(String code) {
        return this.employeeRepository
            .findById(code)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not exist"));
    }

    @Override
    public Employee save(Employee employee) {

        Employee boss = this.employeeRepository
            .findById(employee.getBoss().getCode())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boss not exist"));

        employee.setBoss(boss);

        return this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeeByRangeAge(Integer age1, Integer age2) {
        return this.employeeRepository.findEmployeeByRangeAge(age1, age2);
    }
    
}
