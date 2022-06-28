package com.github.youssefwadie.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends AbstractEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Email(message = "The email must be in the form user@domain.com")
    private String email;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "employees_projects",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"))
    private Collection<Employee> employees;
}
