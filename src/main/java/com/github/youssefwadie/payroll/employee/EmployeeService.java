package com.github.youssefwadie.payroll.employee;

import com.github.youssefwadie.payroll.attendance.AttendanceNotRegisteredYetException;
import com.github.youssefwadie.payroll.attendance.AttendanceRegisteredBeforeException;
import com.github.youssefwadie.payroll.attendance.AttendanceRepository;
import com.github.youssefwadie.payroll.dto.EmployeeAttendanceRegistration;
import com.github.youssefwadie.payroll.dto.EmployeeBasicDetails;
import com.github.youssefwadie.payroll.dto.RegistrationType;
import com.github.youssefwadie.payroll.entities.Attendance;
import com.github.youssefwadie.payroll.entities.Employee;
import com.github.youssefwadie.payroll.entities.Project;
import com.github.youssefwadie.payroll.project.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final ProjectRepository projectRepository;

    public List<EmployeeBasicDetails> findAllBasic() {
        return employeeRepository.findAllBasic();
    }

    public List<Employee> findAllWithAllowances() {
        return employeeRepository.findAllWithAllowances();
    }

    public List<Employee> findEmployeesInDepartment(Long departmentId) {
        return employeeRepository.findEmployeesInDepartment(departmentId);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }


    public Employee findById(Long id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(String.format("No employee with id: %d was found.", id)));
    }

    public EmployeeBasicDetails findByIdBasic(Long id) {
        return employeeRepository.findByIdBasic(id);
    }

    public void deleteEmployeeById(Long id) throws EmployeeNotFoundException {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("No employee with id: %d was found.", id)));

        List<Attendance> attendanceList = attendanceRepository.findAllAttendanceByEmployee(employee);
        // Break the association in the `employees_attendances` table
        for (Attendance attendance : attendanceList) {
            attendance.setEmployee(null);
            attendanceRepository.save(attendance);
        }

        // Break the association in the `employees_projects` table
        List<Project> projectList = projectRepository.findAll();
        for (Project project : projectList) {
            project.getEmployees().remove(employee);
            projectRepository.save(project);
        }

        attendanceRepository.flush();
        projectRepository.flush();
        employeeRepository.deleteById(id);
    }

    /**
     * @param registration Employee registration, contains the employee's id and registration type (IN or OUT)
     * @throws EmployeeNotFoundException           When an employee with the given id in the registration is not found
     * @throws AttendanceRegisteredBeforeException When an attempt to register an employee's IN or OUT is already in the db
     * @throws AttendanceNotRegisteredYetException When an attempt to register an employee's OUT without the IN
     */
    public void registerEmployeeAttendance(EmployeeAttendanceRegistration registration)
            throws EmployeeNotFoundException, AttendanceRegisteredBeforeException, AttendanceNotRegisteredYetException {

        Employee employee = employeeRepository.findById(registration.employeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("No employee with id: %d was found.", registration.employeeId())));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime today = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 0, 0, 0);

        if (registration.registrationType() == RegistrationType.IN) {
            long numberOfAttendances = attendanceRepository.countAttendanceByEmployeeIdAAndTimeInPeriod(employee.getId(), today, now);
            if (numberOfAttendances == 1) {
                throw new AttendanceRegisteredBeforeException(String.format("Attendance %s for %s is already registered",
                        registration.registrationType(), employee.getFullName()));
            } else if (numberOfAttendances > 1) {
                throw new IllegalStateException("The employee has multiple attendance registrations for some reason");
            }
            // zero..
            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setTimeIn(now);
            attendanceRepository.save(attendance);
        } else if (registration.registrationType() == RegistrationType.OUT) {
            List<Attendance> attendanceList =
                    attendanceRepository.findAttendanceByEmployeeIdAndTimeInPeriod(employee.getId(), today, now);
            if (attendanceList.size() == 0) {
                throw new AttendanceNotRegisteredYetException(
                        String.format("Attendance IN for %s is not registered", employee.getFullName()));
            } else if (attendanceList.size() != 1) {
                throw new IllegalStateException("The employee has multiple attendance registrations for some reason");
            }
            Attendance attendance = attendanceList.get(0);
            if (attendance.getTimeOut() != null) {
                throw new AttendanceRegisteredBeforeException(String.format("Attendance %s for %s is already registered",
                        registration.registrationType(), employee.getFullName()));
            }
            attendance.setTimeOut(now);
            attendanceRepository.save(attendance);
        }

    }
}
