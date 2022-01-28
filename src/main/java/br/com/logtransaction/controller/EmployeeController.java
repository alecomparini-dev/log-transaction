package br.com.logtransaction.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.logtransaction.model.Employee;
import br.com.logtransaction.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> findAll() {
        return this.employeeService.findAll();
    }

    @GetMapping("/{code}")
    public Employee findByCode(@PathVariable String code) {
        return this.employeeService.findByCode(code);
    }

    @GetMapping("/range")
    public List<Employee> findEmployeeByRangeAge(@RequestParam("age1")  Integer age1,
                                                 @RequestParam("age2")  Integer age2) {
        return this.employeeService.findEmployeeByRangeAge(age1, age2);
    }

    @PostMapping
    public Employee salve(@RequestBody Employee employee) {
        return this.employeeService.save(employee);
    }

    
}
