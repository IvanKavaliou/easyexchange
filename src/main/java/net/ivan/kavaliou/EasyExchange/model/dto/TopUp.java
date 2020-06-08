package net.ivan.kavaliou.EasyExchange.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TopUp {
    @NotNull
    private BigDecimal value;
}
