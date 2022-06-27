package com.github.youssefwadie.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "allowances")
public class Allowance extends AbstractEntity {

    @NotBlank(message = "Allowance name must be set")
    private String allowance;

    private String description;

    @NotNull(message = "Allowance amount must be set")
    private Double amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "allowance_type")
    private AllowanceType allowanceType;

    @Column(name = "effective_date")
    private LocalDate effectiveDate;
}
