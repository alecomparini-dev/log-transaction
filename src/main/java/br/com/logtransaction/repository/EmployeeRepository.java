package br.com.logtransaction.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.com.logtransaction.model.Employee;


public interface EmployeeRepository extends MongoRepository<Employee,String>{

    @Query("{ $and: [ { 'age': { $gte: ?0 } }, { 'age': {$lte: ?1 } } ] }")
    public List<Employee> findEmployeeByRangeAge(Integer age1, Integer age2);
    
    public List<Employee> findByName(String name);
}
