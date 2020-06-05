package net.ivan.kavaliou.EasyExchange.model.dto;

import lombok.Builder;
import lombok.Data;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class TopUp {

    @NotNull
    private CurrencyType currency;

    @NotNull
    private BigDecimal value;
}
