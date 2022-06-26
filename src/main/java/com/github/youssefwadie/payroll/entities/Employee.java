package com.github.youssefwadie.payroll.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.github.youssefwadie.payroll.config.EmployeeListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "employees")
@EntityListeners({EmployeeListener.class})
@JsonIgnoreProperties(value = {"subordinates", "projects", "employeePhoneNumbers", "pastPayslips", "qualifications", "nickNames"})
public class Employee extends AbstractEntity {

    @JsonProperty(value = "full_name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 7, max = 128, message = "Full name must at least 12 characters and at most 128 characters")
    @Column(name = "full_name")
    private String fullName;

    @NotEmpty(message = "Social security number must be set")
    @Column(name = "ssn", nullable = false, unique = true)
    @JsonProperty(value = "ssn")
    private String socialSecurityNumber;

    @NotNull(message = "Date of birth must be set")
    @Past(message = "Date of birth must be in the past")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty(value = "date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;      // yyyy-MM-dd


    @NotNull(message = "Basic salary must be set")
    @DecimalMin(value = "500", message = "Basic salary must be equal to or exceed 500")
    @JsonProperty(value = "basic_salary")
    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @NotNull(message = "Hired date must be set")
    @PastOrPresent(message = "Hired date must be in the past or present")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonProperty(value = "hired_date")
    @Column(name = "hired_date")
    private LocalDate hiredDate;

    @JsonProperty(value = "email")
    @Email(message = "The email must be in the form user@domain.com")
    protected String email;

    @ManyToOne
    @JoinColumn(name = "supervisor_id", referencedColumnName = "id")
    private Employee supervisor;

    @OneToMany(mappedBy = "supervisor")
    private Set<Employee> subordinates = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "employment_type")
    private EmploymentType employmentType;

    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "qualifications", joinColumns = @JoinColumn(name = "employee_id"))
    private Collection<Qualification> qualifications = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "nick_names")
    private Collection<String> nickNames = new ArrayList<>();

    @JsonProperty(value = "age", defaultValue = "19")
    @DecimalMin(value = "18", message = "Age must exceed 18")
    @DecimalMax(value = "60", message = "Age must cannot exceed 60")
    private int age;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "employees_allowances",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "allowance_id", referencedColumnName = "id"))
    private Set<Allowance> employeeAllowances = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "current_payslip_id")
    private Payslip currentPayslip;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private ParkingSpace parkingSpace;


    @OneToMany(mappedBy = "employee")
    private Collection<Payslip> pastPayslips = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "employees_phone_numbers")
    @MapKeyColumn(name = "phone_type")
    @Column(name = "phone_number")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PhoneType, String> employeePhoneNumbers = new HashMap<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToMany(mappedBy = "employees")
    private Collection<Project> projects = new ArrayList<>();

}
