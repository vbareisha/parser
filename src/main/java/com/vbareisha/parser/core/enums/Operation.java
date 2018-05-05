package com.vbareisha.parser.core.enums;

public enum Operation {
    DENIED("Отказано в снятии наличных."),
    DENIED_PAY("Отказано в оплате."),
    GET_CASH("Снятие наличных."),
    ADMISSION("Поступление на карту."),
    WRITEOFF("Списание средств."),
    PAY("Оплата."),
    CANCEL("Отмена.");

    public String description;

    Operation(String description) {
        this.description = description;
    }
}
