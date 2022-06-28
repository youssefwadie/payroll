package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Role;
import com.github.youssefwadie.payroll.entities.User;
import com.github.youssefwadie.payroll.role.RoleRepository;
import com.github.youssefwadie.payroll.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@ComponentScan(basePackages = "com.github.youssefwadie.payroll.security")
public class UserRepositoryTests {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void createAdminUser() {
        User admin = new User();
        String password = "admin";
        System.out.println(passwordEncoder.encode(password));
        admin.setPassword(passwordEncoder.encode(password));
        admin.setEmail("admin@payroll.com");
        admin.setFirstName("Admin");
        admin.setLastName("User");
        Role adminRole = roleRepository.findById(1L).get();
        admin.setRoles(Set.of(adminRole));
        User savedUser = userRepository.save(admin);

        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(passwordEncoder.matches(password, savedUser.getPassword())).isTrue();
        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }

    @Test
    void createNormalUser() {
        User user = new User();
        String password = "user";
        System.out.println(passwordEncoder.encode(password));
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail("user@payroll.com");
        user.setFirstName("Normal");
        user.setLastName("User");
        Role userRole = roleRepository.findById(2L).get();
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isGreaterThan(0);
        assertThat(passwordEncoder.matches(password, savedUser.getPassword())).isTrue();
        assertThat(savedUser.getRoles().size()).isEqualTo(1);
    }
}
