package com.github.youssefwadie.payroll.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.youssefwadie.payroll.config.AbstractEntityListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
//@JsonIgnoreProperties("employees")
@EntityListeners({AbstractEntityListener.class})
public class Department extends AbstractEntity {

    @NotEmpty(message = "Department name must be set")
    @Pattern(regexp = "[A-Z]{2,}\\d{3,}[A-Z]+",
            message = "Department must be in form dept abbreviation, number and branch. Eg FI011M")
    @Column(name = "name")
    private String name; // FIN0011MAIN

    @Email(message = "The email must be in the form user@domain.com")
    @Column(name = "email")
    String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "department")
    private List<Employee> employees = new ArrayList<>();

    @Transient
    private String departmentCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(this.getName().toUpperCase(), that.getName().toUpperCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName().toUpperCase());
    }
}
