package com.github.youssefwadie.payroll.seeders;

import com.github.youssefwadie.payroll.entities.*;
import com.github.youssefwadie.payroll.repositories.DepartmentRepository;
import com.github.youssefwadie.payroll.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@AllArgsConstructor
@Component
public class EmployeeSeeder {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

//    private final PayslipRepository payslipRepository;

    private final Double minSalary = 5000d;

    private final static String[] firstNames =
            {"John", "William", "James", "Charles", "George", "Frank", "Joseph", "Thomas",
                    "Henry", "Robert", "Edward", "Harry", "Walter", "Arthur", "Fred", "Albert", "Samuel", "David", "Louis", "Joe",
                    "Willie", "Alfred", "Sam", "Roy", "Herbert", "Jacob", "Tom", "Mary", "Anna", "Emma", "Elizabeth", "Minnie",
                    "Margaret", "Ida", "Clara", "Bertha", "Annie", "Alice", "Florence", "Grace", "Sarah", "Bessie", "Martha",
                    "Nellie", "Ethel", "Ella", "Mabel",};

    private final static String[] lastNames = {"Charlie", "Clarence", "Richard", "Andrew", "Daniel", "Ernest", "Will",
            "Jesse", "Oscar", "Lewis", "Peter", "Elmer", "Francis", "Dewey", "Lawrence", "Alfred", "Daniel", "Leo", "Sam",
            "Eugene", "Oscar", "Jesse", "Will", "Herman", "Benjamin", "Frederick", "Chester", "Tom", "Lloyd", "Leroy",
            "Jessie", "Jim", "Martin", "Ben", "Donald", "Eddie", "Stanley", "Cecil", "Theodore", "Harvey", "Edgar",
            "Luther", "Homer", "Norman", "Philip", "Bernard", "Patrick", "Kenneth", "Hugh",
    };

    private final static String[] emailSeparators = {".", "_", ""};
    private final static String[] emailProviders =
            {"gmail.com", "yahoo.com", "hitmail.com", "rxdoc.biz", "cox.com", "126.net", "126.com", "comast.com", "comast.net",
                    "yandex.com", "wegas.ru", "twc.com", "charter.com", "outlook.com", "gmx.com", "ddns.org", "findhere.com",
                    "freeservers.com", "hotmail.com"};

    private final static String[] states = {
            "Florida",
            "Michigan",
            "Michigan",
            "Pennsylvania",
            "Texas",
            "Aldford",
            "Aldridge",
            "Alveley",
            "Amersham"
    };

    private final static String[] allowances = {
            "Food",
            "Trans.",
            "Wear",
            "Edu"
    };

    private final static String[] cities = {
            "Houston",
            "Philadelphia",
            "Alexandria",
            "Metairie",
            "Northville",
            "Boca Raton",
            "Grand Forks",
            "Downers Grove",
            "Harleigh"
    };

    private static final String[] streetNames = {
            "Ashton Lane",
            "Golf Course Drive",
            "Formula Lane",
            "Lang Avenue",
            "Selah Way",
            "Little Acres Lane",
            "Cheshire Road",
            "Trymore Road",
            "Michigan Avenue",
            "Oak Drive"
    };

    private final Random random = new Random();
    private final Double allowanceMaxAmount = 1000.0;

    public Long employeesCount() {
        return employeeRepository.count();
    }

    public void seedEmployee() {
        Employee employee = new Employee();

        String fullName = getFullName();
        employee.setFullName(fullName);
        employee.setEmail(getEmailForName(fullName));
        employee.setDateOfBirth(getDateOfBirth());
        employee.setAddress(getAddress());
        employee.setBasicSalary(getRandomDouble());
        employee.setEmploymentType(getEmploymentType());
        employee.setHiredDate(getHiredDate());
        employee.setSocialSecurityNumber(getSsnForName(fullName));
        employee.setDepartment(departmentRepository.findById(random.nextLong(1, 10)).get());
        seedAllowancesForEmployee(employee);
        employeeRepository.save(employee);
    }

    private void seedAllowancesForEmployee(Employee employee) {
        for (int i = 0, counter = random.nextInt(4); i < counter; ++i) {
            Allowance allowance = new Allowance();
            allowance.setAllowance(getRandom(allowances));
            allowance.setAmount(Double.min(getRandomDouble(), allowanceMaxAmount));
            allowance.setAllowanceType(AllowanceType.MONTHLY);
            employee.getEmployeeAllowances().add(allowance);
        }
    }

    private String getFullName() {
        String firstName = getRandom(firstNames);
        String lastName = getRandom(lastNames);
        return String.format("%s %s", firstName, lastName);
    }

    private String getEmailForName(String name) {
        String[] nameParts = name.split("\\s");
        String separator = getRandom(emailSeparators);
        String emailProvider = getRandom(emailProviders);
        int num = random.nextInt(0, Integer.MAX_VALUE);
        return String.format("%s%s%s%d@%s", nameParts[0], separator, nameParts[1], num, emailProvider);
    }


    private String getSsnForName(String name) {
        int nameHashCode = name.hashCode() % 100_000_000;
        int randomNumber = random.nextInt(999_999_999);
        return String.format("%09d", (nameHashCode + randomNumber) % 999_999_999);
    }

    private Address getAddress() {
        Address address = new Address();
        address.setCountry("United Kingdom");
        address.setCity(getRandom(cities));
        address.setState(getRandom(states));
        address.setStreetAddress(getStreetAddress());
        address.setPhone(getPhoneNumber());
        address.setZipCode(getZipCode());
        return address;
    }

    private String getStreetAddress() {
        return String.format("%d %s", random.nextInt(1, 9000), getRandom(streetNames));
    }

    private String getPhoneNumber() {
        return String.format("%03d %03d %03d",
                random.nextInt(999),
                random.nextInt(999),
                random.nextInt(999));
    }

    private String getZipCode() {
        final String SALT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 4; i++) {
            sb.append(SALT.charAt(random.nextInt(SALT.length())));
        }
        sb.append(' ');
        for (int i = 0; i < 3; i++) {
            sb.append(SALT.charAt(random.nextInt(SALT.length())));
        }
        return sb.toString();
    }

    private <T> T getRandom(T[] elements) {
        return elements[random.nextInt(elements.length)];
    }

    private Double getRandomDouble() {
        double randomNumber = random.nextDouble() * random.nextInt(5_000, 15_000);
        return Double.max(minSalary, randomNumber);
    }

    private Double getRandomDouble(Double max) {
        return Double.min(getRandomDouble(), max);
    }

    private LocalDate getDateOfBirth() {
        return LocalDate.of(random.nextInt(1986, 2000), random.nextInt(1, 13), random.nextInt(1, 28));
    }

    private EmploymentType getEmploymentType() {
        return getRandom(EmploymentType.values());
    }

    private LocalDate getHiredDate() {
        return LocalDate.of(random.nextInt(2013, 2022),
                random.nextInt(1, 13),
                random.nextInt(1, 28));
    }

}
