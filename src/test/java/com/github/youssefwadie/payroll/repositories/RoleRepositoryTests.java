package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Role;
import com.github.youssefwadie.payroll.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testCreateAdminRole() {
        Role admin = new Role();
        admin.setName("Admin");
        admin.setDescription("Manages every thing, add or remove employees, departments and projects");
        Role savedRole = roleRepository.save(admin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    void testCreateUserRole() {
        Role user = new Role();
        user.setName("User");
        user.setDescription("Get information about employees or departments");
        Role savedRole = roleRepository.save(user);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }


}
