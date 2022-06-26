package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.entities.User;
import com.github.youssefwadie.payroll.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
