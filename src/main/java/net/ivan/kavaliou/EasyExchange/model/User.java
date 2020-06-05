package net.ivan.kavaliou.EasyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    @Email (message = "error.email.format")
    @NotNull(message = "error.email.notEmpty")
    @NotEmpty(message = "error.email.notEmpty")
    @Size(max = 100, message = "error.email.size")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "error.email.notEmpty")
    @NotEmpty (message = "error.password.notEmpty")
    @Size(min = 4, max = 100, message = "error.password.size")
    private String password;

    @Column(name = "registred", nullable = false, columnDefinition = "timestamp default now()")
    private Date registred = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

}
