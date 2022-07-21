# Payroll
A payroll management system.

**Table of contents**
- [About](#about)
- [Schema](#schema)
- [Installation](#installation)
- [Endpoints](#end-points)
- [Built with](#built-with)

---
About <a name="about"></a>
===
<p>
Payroll is a Java software which can be used to handle the process of calculating employees' pay in a company.
It handles the employees attendance and basic information about them, department they belong to and projects they work on.
<br>
The authentication system is <s>Session Based (JWT Based will be implemented)</s> JWT Based.
<br>
The authorization system works as follows:
There are two types of users in the system; normal user and admin user.
An admin user can access all the end-points, normal user can access any get endpoint under the <em>employees</em> and <em>departments</em> resources.
<br>
<strong>There's a lot of work needs to be implemented and a lot of features that can be added; I may work on them from time to time.</strong>
</p>

---
Schema <a name = "schema"></a>
===
<img alt="schema" src="/db/payroll.png">

---
Installation <a name = "installation"></a>
===
1. Install [MariaDB](https://mariadb.org/download/).
2. Create a new schema named `payroll`.
3. Import the data tables and sample from the [import script](/db/dump.sql).
4. Install **JDK 17+**, you can use any implementation from your trusted provider.
5. Install [Maven](https://maven.apache.org/).
6. Update the [username](/src/main/resources/application.properties#L7) and [password](/src/main/resources/application.properties#L8) according to your **DBMS** configuration.
7. Import the project to your preferred IDE, or run it directly with *Maven*, with `mvn spring-boot:run`.


---
End Points <a name = "end-points"></a>
===
* `/api/v1/employees` list all employees with a set of their information; *full name, age, basic salary, email, supervisor and department* [GET].
* `/api/v1/employees/{id}` get a set of basic information about a given employee [GET].
* `/api/v1/employees/create` add a new employee to the system - accepts JSON [POST].
* `/api/v1/employees/delete/{id}` remove an employee from the system [DELETE].
* `/api/v1/employees/register` register an employee's attendance - accepts JSON [PUT].
* `/api/v1/payroll` calculate generate a payroll report for a given period of time - accepts JSON [GET].
* `/api/v1/departments/` list all departments with a set of their information; name, id [GET].
* `/api/v1/departments/{id}` get all the available information about a given department [GET].
---
Built with <a name = "built-with"></a>
===
* [Eclipse Temurin JDK with HotSpotVM 17](https://adoptium.net/temurin/releases) - JDK implementation.
* [Spring Boot](https://spring.io/projects/spring-boot) with [MariaDB](https://mariadb.org)  - the main the backend technologies.
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) framework to remove most of the JPA-based repositories boilerplate.
* [Spring Security](https://spring.io/projects/spring-security) framework.
* [Lombok](https://projectlombok.org) - A Java library to remove most of the getters, setters, constructors, etc boilerplate.
* [Jackson](https://github.com/FasterXML/jackson/) - A Java library to marshal JSON to POJOs or vice versa.
* [JUnit 5](https://junit.org/junit5/) with [AssertJ](https://github.com/assertj/assertj-core) for testing.

## Note
* **DO NOT** use the [seeder](/src/main/java/com/github/youssefwadie/payroll/seeders) if you don't know which data should be seeded and inserted first (I may work on that later).
