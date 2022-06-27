package com.github.youssefwadie.payroll.config;


import com.github.youssefwadie.payroll.entities.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AbstractEntityListener {


    @PrePersist
    public void setCreatedOn(AbstractEntity abstractEntity) {
        abstractEntity.setCreatedOn(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedOn(AbstractEntity abstractEntity) {
        abstractEntity.setUpdatedOn(LocalDateTime.now());
    }


}
