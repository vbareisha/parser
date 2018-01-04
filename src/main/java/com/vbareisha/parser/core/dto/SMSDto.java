package com.vbareisha.parser.core.dto;

import com.vbareisha.parser.core.enums.CurrencyType;
import com.vbareisha.parser.core.enums.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SMSDto {
    private BigDecimal consumption = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private BigDecimal rest = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private LocalDateTime dateTime = null;
    private CurrencyType currencyType;
    private CurrencyType operationCurrency;
    private Operation operation;

    private String originalText;

    public BigDecimal getConsumption() {
        return consumption;
    }

    public BigDecimal getRest() {
        return rest;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public CurrencyType getOperationCurrency() {
        return operationCurrency;
    }

    public void setOperationCurrency(CurrencyType operationCurrency) {
        this.operationCurrency = operationCurrency;
    }

    @Override
    public String toString() {
        return "SMSDto{" +
                "consumption=" + consumption +
                ", rest=" + rest +
                ", dateTime=" + dateTime +
                ", currencyType=" + currencyType +
                ", operationCurrency=" + operationCurrency +
                ", operation=" + operation +
                ", originalText='" + originalText + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SMSDto smsDto = (SMSDto) o;

        if (consumption != null ? !consumption.equals(smsDto.consumption) : smsDto.consumption != null) return false;
        if (rest != null ? !rest.equals(smsDto.rest) : smsDto.rest != null) return false;
        if (dateTime != null ? !dateTime.equals(smsDto.dateTime) : smsDto.dateTime != null) return false;
        if (currencyType != smsDto.currencyType) return false;
        if (operationCurrency != smsDto.operationCurrency) return false;
        if (operation != smsDto.operation) return false;
        return originalText != null ? originalText.equals(smsDto.originalText) : smsDto.originalText == null;
    }

    @Override
    public int hashCode() {
        int result = consumption != null ? consumption.hashCode() : 0;
        result = 31 * result + (rest != null ? rest.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (currencyType != null ? currencyType.hashCode() : 0);
        result = 31 * result + (operationCurrency != null ? operationCurrency.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (originalText != null ? originalText.hashCode() : 0);
        return result;
    }
}
