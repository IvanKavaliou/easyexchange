package net.ivan.kavaliou.EasyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Email
    @NotBlank(message = "error.email.notBlank")
    @Size(max = 100, message = "error.email.size")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank (message = "error.password.notBlank")
    @Size(min = 4, max = 100, message = "error.password.size")
    private String password;

    @Column(name = "registred", nullable = false, columnDefinition = "timestamp default now()")
    private Date registred = new Date();

}
