package net.ivan.kavaliou.EasyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
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
    @Email (message = "Can be email format!")
    @NotNull(message = "Email cannot be null!")
    @NotEmpty(message = "Email cannot be empty!")
    @Size(max = 100, message = "Email max charters = 100")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password cannot be null!")
    @NotEmpty (message = "Password cannot be empty!")
    @Size(min = 4, max = 100, message = "Password must be between 4 and 100 characters!")
    private String password;

    @Column(name = "registred", nullable = false, columnDefinition = "timestamp default now()")
    private Date registred = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "boolean default true")
    private boolean enabled = true;

    private BigDecimal balance;

}
