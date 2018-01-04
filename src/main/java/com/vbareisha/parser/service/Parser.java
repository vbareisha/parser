package com.vbareisha.parser.service;

import com.vbareisha.parser.core.enums.ParserType;
import com.vbareisha.parser.core.exception.NotFoundParseTypeException;
import com.vbareisha.parser.service.api.IParser;

import java.math.BigDecimal;

public class Parser implements IParser {

    private static final String BYN = "BYN";

    @Override
    public BigDecimal getSumFromText(String text, ParserType type) {
        BigDecimal targetSum = BigDecimal.ZERO;
        switch (type) {
            case SUM: {
                int startIndex = (text.indexOf(TextTemplates.ADMISSION.template) > 0) ? text.indexOf(TextTemplates.ADMISSION.template) + TextTemplates.ADMISSION.template.length()
                        : ((text.indexOf(TextTemplates.WRITEOFF.template) > 0) ? text.indexOf(TextTemplates.WRITEOFF.template) + TextTemplates.WRITEOFF.template.length()
                        : ((text.indexOf(TextTemplates.REST.template) > 0) ? text.indexOf(TextTemplates.REST.template) + TextTemplates.REST.template.length() : 0));
                if (startIndex > 0) {
                    String temp = text.substring(startIndex);
                    if (temp.indexOf(BYN) > 0) {
                        String strSum = temp.substring(0, temp.indexOf("BYN"));
                        strSum = strSum.replaceAll(" ", "").replaceAll(",", ".");
                        targetSum = BigDecimal.valueOf(Float.parseFloat(strSum)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
                break;
            }
            default: {
                throw new NotFoundParseTypeException("Parse type not found!");
            }
        }
        return targetSum;
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
}
