package com.github.youssefwadie.payroll.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@ComponentScan(basePackages = {"com.github.youssefwadie.payroll"})
public class SecurityConfigTests {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void passwordEncoderTests() {
        String passwordInPlainText = "admin2";
        String encryptedPassword = passwordEncoder.encode(passwordInPlainText);
        System.out.println(encryptedPassword);
        assertThat(passwordEncoder.matches(passwordInPlainText, encryptedPassword)).isTrue();

    }
}
