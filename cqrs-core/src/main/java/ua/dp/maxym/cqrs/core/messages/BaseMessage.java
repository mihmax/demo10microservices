package ua.dp.maxym.cqrs.core.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseMessage {
    private String id;
}
