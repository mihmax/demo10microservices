package ua.dp.maxym.account.query.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.dp.maxym.account.common.dto.BaseResponse;
import ua.dp.maxym.account.query.domain.BankAccount;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountLookupResponse extends BaseResponse {
    List<BankAccount> accounts;

    public AccountLookupResponse(String message) {
        super(message);
    }
}
