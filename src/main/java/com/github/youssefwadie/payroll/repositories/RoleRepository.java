package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
