package com.github.youssefwadie.payroll.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @NotNull
    @Email(message = "The email must be in the form user@domain.com")
    @Column(name = "email", length = 128, unique = true, nullable = false)
    private String email;
    public static final String PASSWORD_VALIDATION_MESSAGE = """
            The message must contains at least 8 characters and at most 16 characters
            \t1-at least one digit.
            \t2-one lowercase alphabet.
            \t3-one uppercase alphabet.
            \t4-least one special character which includes !@#$%&*()-+=^.
            \t5-doesn't contain any white space.
            """;

    @Size(min = 8, max = 64)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,16}$",
            message = PASSWORD_VALIDATION_MESSAGE)
    private String password;

    private String salt;

    @Column(name = "first_name", length = 64, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 64, nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

}
