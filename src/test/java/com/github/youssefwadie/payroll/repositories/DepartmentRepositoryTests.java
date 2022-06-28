package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.deprtment.DepartmentRepository;
import com.github.youssefwadie.payroll.entities.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class DepartmentRepositoryTests {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Test
    public void testCreateDepartment() {
        Department department = new Department();
        department.setName("IT101M");
        department.setEmail("it@payroll.com");
        department.setDepartmentCode("101");
        Department savedDepartment = departmentRepository.save(department);
        assertThat(savedDepartment.getId()).isGreaterThan(0);
        assertThat(savedDepartment.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedDepartment.getUpdatedOn()).isNull();
    }

    @Test
    public void testCreateHRDepartment() {
        Department department = new Department();
        department.setName("HR101S");
        department.setEmail("hr@payroll.com");
        Department savedDepartment = departmentRepository.save(department);
        assertThat(savedDepartment.getId()).isGreaterThan(0);
        assertThat(savedDepartment.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(savedDepartment.getUpdatedOn()).isNull();
    }

    @Test
    public void testFindDepartmentByName() {
        String departmentName = "IT101M";
        Department department = departmentRepository.findByName(departmentName);
        assertThat(department).isNotNull();
        assertThat(department.getName()).isEqualTo(departmentName);
        assertThat(department.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
