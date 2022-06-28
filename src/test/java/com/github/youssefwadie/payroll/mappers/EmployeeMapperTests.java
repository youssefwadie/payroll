package com.github.youssefwadie.payroll.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.employee.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeMapperTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testEmployeeMapping() throws JsonProcessingException {
        Employee employee = employeeRepository.findById(1L).get();
        final String employeeJson = new ObjectMapper().writeValueAsString(employee);
        System.out.println(employeeJson);
    }
}
