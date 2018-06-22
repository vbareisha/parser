package com.vbareisha.parser.service;

import com.vbareisha.parser.core.dto.SMSDto;
import com.vbareisha.parser.core.enums.CurrencyType;
import com.vbareisha.parser.core.enums.ParserType;
import com.vbareisha.parser.core.exception.NotFoundParseTypeException;
import com.vbareisha.parser.service.api.IParser;

import java.math.BigDecimal;
import java.util.Date;

import static com.vbareisha.parser.core.enums.CurrencyType.BYN;
import static com.vbareisha.parser.core.enums.Operation.*;

public class Parser implements IParser<SMSDto> {

    @Override
    public SMSDto parse(String text, ParserType type) {
        SMSDto item =  new SMSDto();
        switch (type) {
            case SMS_MTBANK: {
                int startIndex;
                item.setOriginalText(text);
                text = text.replaceAll("\n", " ").trim();
                // Поступления на счет
                if (text.indexOf(TextTemplates.ADMISSION.template) > 0) {
                    startIndex = text.indexOf(TextTemplates.ADMISSION.template) + TextTemplates.ADMISSION.template.length();
                    item.setCurrencyType(getCurrencyFromtext(text, startIndex));
                    item.setConsumption(getSum(text, startIndex, false, item.getCurrencyType().name()));
                    item.setOperation(ADMISSION);
                    item.setOperationCurrency(item.getCurrencyType());
                    item.setDateTime(getDateFromText(text));
                } else if (text.indexOf(TextTemplates.WRITEOFF.template) > 0) { // Списание средств
                    startIndex = text.indexOf(TextTemplates.WRITEOFF.template) + TextTemplates.WRITEOFF.template.length();
                    item.setCurrencyType(getCurrencyFromtext(text, startIndex));
                    item.setConsumption(getSum(text, startIndex, true, item.getCurrencyType().name()));
                    item.setOperation(WRITEOFF);
                    item.setOperationCurrency(item.getCurrencyType());
                    item.setDateTime(getDateFromText(text));
                } else if (text.indexOf(TextTemplates.REST.template) > 0) { // Виды оплаты
                    startIndex = text.indexOf(TextTemplates.REST.template) + TextTemplates.REST.template.length();
                    item.setCurrencyType(getCurrencyFromtext(text, startIndex));
                    item.setRest(getSum(text, startIndex,false, item.getCurrencyType().name()));
                    item.setOperationCurrency(item.getCurrencyType());
                    boolean revert = true;
                    // операция не прошла
                    if (text.indexOf("OTKAZANO NALICHNYE") > 0) {
                        startIndex = 0;
                        item.setOperation(DENIED);
                    } else if (text.indexOf("OTKAZANO OPLATA") > 0) {
                        startIndex = 0;
                        item.setOperation(DENIED_PAY);
                    } else if (text.indexOf("NALICHNYE") > 0) {
                        startIndex = text.indexOf("NALICHNYE") + "NALICHNYE".length();
                        item.setOperation(GET_CASH);
                    } else if (text.indexOf("OPLATA") > 0) {
                        startIndex = text.indexOf("OPLATA") + "OPLATA".length();
                        item.setOperation(PAY);
                    } else if (text.indexOf("OTMENA") > 0) {
                        startIndex = text.indexOf("OTMENA") + "OTMENA".length();
                        item.setOperation(CANCEL);
                        revert = false;
                    } else if (text.indexOf("POPOLNENIE") > 0) {
                        startIndex = text.indexOf("POPOLNENIE") + "POPOLNENIE".length();
                        item.setOperation(ADMISSION);
                        revert = false;
                    }
                    item.setOperationCurrency(getCurrencyFromtext(text, startIndex));
                    item.setConsumption(getSum(text, startIndex, revert, item.getOperationCurrency().name()));
                    item.setDateTime(getDateFromText(text));
                }
                break;
            }
            default: {
                throw new NotFoundParseTypeException("Parse type not found!");
            }
        }
        return item;
    }

    /**
     * Получить сумму из текста
     * @param text - смс текст
     * @param startIndex - начало откуда вырезаем
     * @return сумма {@link BigDecimal}
     */
    private BigDecimal getSum(String text, int startIndex, boolean revert, String currency) {
        if (startIndex > 0) {
            String temp = text.substring(startIndex);
            if (temp.indexOf(currency) > 0) {
                String strSum = temp.substring(0, temp.indexOf(currency));
                strSum = strSum.replaceAll(" ", "").replaceAll(",", ".");
                if (!revert) {
                    return BigDecimal.valueOf(Float.parseFloat(strSum)).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    return BigDecimal.valueOf(Float.parseFloat(strSum) * -1).setScale(2, BigDecimal.ROUND_HALF_UP);
                }
            }
        }
        return BigDecimal.valueOf(0F).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private CurrencyType getCurrencyFromtext(String text, int startIndex) {
        if (startIndex > 0) {
            String temp = text.substring(startIndex, startIndex + 15);
            temp = temp.replaceAll(",", ".");
            int indexDot = temp.indexOf(".");
            return CurrencyType.valueOf(temp.substring(indexDot + 4, indexDot + 7).toUpperCase());
        }
        return BYN;
    }

    /**
     * Получить дату проведения операции из смс, если даты нет, вернуть текущую дату
     * @param text текст смс
     * @return - дата проведения операции
     */
    private Date getDateFromText(final String text) {
        Date result;
        String temp = text;
        if (text.startsWith(StartTextTemplates.RESPECT.template)) {
            result = null;
        } else if (text.startsWith(StartTextTemplates.CARD.template)) {
            for (int i = 0; i < 3; i++) {
                temp = takeTextFromLeftToSpace(temp);
            }
            result = getDate(temp, false);
        } else if (text.startsWith(StartTextTemplates.SALARY_CARD.template)) {
            temp = takeTextFromLeftToSpace(temp);
            result = getDate(temp, true);
        } else {
            throw new NotFoundParseTypeException("Start template is not found in this text!");
        }

        return result;
    }

    private String takeTextFromLeftToSpace(String temp) {
        temp = temp.substring(temp.indexOf(" ") + 1, temp.length());
        return temp;
    }

    private Date getDate(String temp, boolean includeTime) {
        Date result;
        String dateInString;
        if (!includeTime) {
            dateInString = temp.substring(0, temp.indexOf(" "));
        } else {
            dateInString = temp.substring(0, temp.indexOf(" ", temp.indexOf(" ") + 1));
        }

        result = new Date(dateInString);
        return result;
    }

    private enum TextTemplates {
        ADMISSION("postupilo"),
        WRITEOFF("SPISANO"),
        REST("OSTATOK");

        public String template;

        TextTemplates(String template) {
            this.template = template;
        }
    }

    private enum StartTextTemplates {
        RESPECT("Uvazhaemyj"),
        CARD("ZP KARTA"),
        SALARY_CARD("KARTA:");

        public String template;

        StartTextTemplates(String template) {
            this.template = template;
        }
    }
}
