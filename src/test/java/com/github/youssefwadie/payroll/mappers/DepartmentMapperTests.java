package com.github.youssefwadie.payroll.mappers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.youssefwadie.payroll.entities.Department;
import com.github.youssefwadie.payroll.deprtment.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DepartmentMapperTests {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testDepartmentMapping() throws JsonProcessingException {
        Department department = departmentRepository.findById(1L).get();
        final String departmentJson = new ObjectMapper().writeValueAsString(department);
        System.out.println(departmentJson);
    }
}
