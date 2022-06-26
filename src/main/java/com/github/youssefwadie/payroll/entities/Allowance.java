package com.github.youssefwadie.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "allowances")
public class Allowance extends AbstractEntity {

    @NotEmpty(message = "Allowance name must be set")
    private String allowanceName;

    @NotNull(message = "Allowance amount must be set")
    private BigDecimal allowanceAmount;
}
