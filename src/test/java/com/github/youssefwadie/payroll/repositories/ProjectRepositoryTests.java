package com.github.youssefwadie.payroll.repositories;

import com.github.youssefwadie.payroll.entities.Project;
import com.github.youssefwadie.payroll.project.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProjectRepositoryTests {
    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testCreateRoboticsProject() {
        Project project = new Project();
        project.setName("Robotics");
        project.setEmail("robotics-proj@payroll.com");
        project.setStartDate(LocalDate.of(2023, 8, 1));
        Project savedProject = projectRepository.save(project);
        assertThat(savedProject.getId()).isGreaterThan(0);
        assertThat(savedProject.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    public void testCreateComputerizationProject() {
        Project project = new Project();
        project.setName("Computerization");
        project.setEmail("computerization-proj@payroll.com");
        project.setStartDate(LocalDate.of(2022, 6, 1));
        Project savedProject = projectRepository.save(project);
        assertThat(savedProject.getId()).isGreaterThan(0);
        assertThat(savedProject.getCreatedOn()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}
