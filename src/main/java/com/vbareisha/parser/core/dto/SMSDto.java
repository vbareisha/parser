package com.vbareisha.parser.core.dto;

import com.vbareisha.parser.core.enums.CurrencyType;
import com.vbareisha.parser.core.enums.Operation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@EqualsAndHashCode
public class SMSDto {
    private BigDecimal consumption = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private BigDecimal rest = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private Date dateTime = null;
    private CurrencyType currencyType;
    private CurrencyType operationCurrency;
    private Operation operation;

    private String originalText;
}
