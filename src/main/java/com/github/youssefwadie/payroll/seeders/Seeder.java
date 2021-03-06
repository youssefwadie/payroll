package com.github.youssefwadie.payroll.seeders;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@AllArgsConstructor
@Component
@Transactional
public class Seeder implements CommandLineRunner {

    private final EmployeeSeeder employeeSeeder;
    private final ProjectsSeeder projectsSeeder;

    private final AttendanceSeeder attendanceSeeder;

    private final boolean seed = false;

    private final boolean seedProjectEmployees = false;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {

            if (projectsSeeder.projectsCount() == 0) {
                projectsSeeder.seedProjects();
            }

            if (employeeSeeder.employeesCount() == 0) {
                for (int i = 0; i < 105; ++i) {
                    employeeSeeder.seedEmployee();
                }
            }

            if (seedProjectEmployees && projectsSeeder.projectsCount() != 0) {
                projectsSeeder.assignProjectsToEmployees();
            }

            if (attendanceSeeder.getCount() == 0) {
                attendanceSeeder.seed();
            }
        }
    }


}
