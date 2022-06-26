package com.github.youssefwadie.payroll.services;

import com.github.youssefwadie.payroll.entities.Role;
import com.github.youssefwadie.payroll.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
