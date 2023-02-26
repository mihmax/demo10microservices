package ua.dp.maxym.account.query.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dp.maxym.account.common.dto.AccountType;
import ua.dp.maxym.cqrs.core.domain.BaseEntity;

import java.time.LocalDateTime;

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
    private double balance;
}
