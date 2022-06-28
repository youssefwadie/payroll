package com.github.youssefwadie.payroll.security;

import com.github.youssefwadie.payroll.entities.User;
import com.github.youssefwadie.payroll.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class PayrollUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email);
        log.debug(String.format("User with email: %s is trying to login", email));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user with email: %s was found!", email));
        }
        return new PayrollUserDetails(user);
    }

}
