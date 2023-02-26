package ua.dp.maxym.account.query.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import ua.dp.maxym.account.common.dto.AccountType;
import ua.dp.maxym.cqrs.core.domain.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount extends BaseEntity {
    @Id
    private String id;
    private String accountHolder;
    private LocalDateTime creationDate;
    private AccountType accountType;
    private BigDecimal balance;
}
