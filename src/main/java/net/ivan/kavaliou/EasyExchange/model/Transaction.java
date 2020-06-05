package net.ivan.kavaliou.EasyExchange.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.EasyExchange.utils.enums.TransactionType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transactions")
public class Transaction {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Account account;

    @NotNull
    private BigDecimal value;


    @Column(name = "transaction", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType transaction;

    @Column(name = "date", nullable = false, columnDefinition = "timestamp default now()")
    private Date date = new Date();
}
