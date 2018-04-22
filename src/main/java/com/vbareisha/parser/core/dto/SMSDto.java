package com.vbareisha.parser.core.dto;

import com.vbareisha.parser.core.enums.CurrencyType;
import com.vbareisha.parser.core.enums.Operation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class SMSDto {
    private BigDecimal consumption = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private BigDecimal rest = BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    private Date dateTime = new Date();
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

    public void setConsumption(BigDecimal consumption) {
        this.consumption = consumption;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SMSDto smsDto = (SMSDto) o;
        return Objects.equals(consumption, smsDto.consumption) &&
                Objects.equals(rest, smsDto.rest) &&
                currencyType == smsDto.currencyType &&
                operationCurrency == smsDto.operationCurrency &&
                operation == smsDto.operation &&
                Objects.equals(originalText, smsDto.originalText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(consumption, rest, currencyType, operationCurrency, operation, originalText);
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
}
