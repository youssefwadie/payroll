package com.github.youssefwadie.payroll.seeders;

import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.Project;
import com.github.youssefwadie.payroll.repositories.EmployeeRepository;
import com.github.youssefwadie.payroll.repositories.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Component
public class ProjectsSeeder {
    private final LocalDate today = LocalDate.now();
    private final LocalDate projectStartDate = LocalDate.of(today.getYear(), today.getMonth().plus(-2), 1);

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;


    public void seedProjects() {
        Project mapsProj = new Project();
        mapsProj.setEmail("maps-proj@payroll.com");
        mapsProj.setName("Hack My Route");
        mapsProj.setStartDate(projectStartDate);


        Project CSVtoJSONProj = new Project();
        CSVtoJSONProj.setEmail("csv-to-json-proj@payroll.com");
        CSVtoJSONProj.setName("CSV to JSON");
        CSVtoJSONProj.setStartDate(projectStartDate);


        Project CSVCleanerProj = new Project();
        CSVCleanerProj.setEmail("csv-cleaner-proj@payroll.com");
        CSVCleanerProj.setStartDate(projectStartDate);


        Project numericalLibraries = new Project();
        numericalLibraries.setName("Numerical Libraries");
        numericalLibraries.setEmail("numerical-libs-proj@payroll.com");

        Project computerization = new Project();
        computerization.setName("Computerization");
        computerization.setEmail("computerization-proj@payroll.com");

        Project reorganization = new Project();
        reorganization.setName("Reorganization");
        reorganization.setEmail("reorganization-proj@payroll.com");
        projectRepository.saveAllAndFlush(List.of(mapsProj, CSVtoJSONProj, CSVCleanerProj, numericalLibraries,
                computerization, reorganization));

    }

    public void assignProjectsToEmployees() {
        final Random random = new Random();
        for (Project project : projectRepository.findAll()) {
            int numberOfEmployees = random.nextInt(10, 30);
            for (int i = 0; i < numberOfEmployees; ++i) {
                Employee employee = employeeRepository.findById(random.nextLong(1, 106)).get();
                if (!project.getEmployees().contains(employee)) {
                    project.getEmployees().add(employee);
                }
            }
            projectRepository.save(project);
        }
    }

    public Long projectsCount() {
        return projectRepository.count();
    }
}
