package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.ParkingSpace;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ParkingSpaceRepositoryTests {
    @Autowired
    private ParkingSpaceRepository parkingSpaceRepository;


    @Test
    public void testCreateFirstParkingSpace() {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setParkingLotNumber("G001");
        parkingSpaceRepository.save(parkingSpace);
        assertThat(parkingSpace.getId()).isGreaterThan(0);
        assertThat(parkingSpace.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void testCreateSecondParkingSpace() {
        ParkingSpace parkingSpace = new ParkingSpace();
        parkingSpace.setParkingLotNumber("G002");
        parkingSpaceRepository.save(parkingSpace);
        assertThat(parkingSpace.getId()).isGreaterThan(0);
        assertThat(parkingSpace.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
