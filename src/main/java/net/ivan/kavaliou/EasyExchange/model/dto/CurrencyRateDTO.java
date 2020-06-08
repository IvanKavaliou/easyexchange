package net.ivan.kavaliou.EasyExchange.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.EasyExchange.utils.enums.CurrencyType;

import java.math.BigDecimal;

@Builder
@Data
public class CurrencyRateDTO {
    String currency;
    CurrencyType code;
    BigDecimal bid;
    BigDecimal ask;
}
