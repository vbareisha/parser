package com.vbareisha.parser;

import com.vbareisha.parser.core.dto.SMSDto;
import com.vbareisha.parser.service.Parser;
import com.vbareisha.parser.service.api.IParser;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.vbareisha.parser.core.enums.CurrencyType.BYN;
import static com.vbareisha.parser.core.enums.CurrencyType.USD;
import static com.vbareisha.parser.core.enums.Operation.*;
import static com.vbareisha.parser.core.enums.ParserType.*;

public class ParserTest {

    private IParser<SMSDto> parser = new Parser();

    private class TestData {
        String text;
        SMSDto expect;

        public TestData(String text, SMSDto expect) {
            this.text = text;
            this.expect = expect;
            this.expect.setOriginalText(text);
        }
    }

    @Test
    public void parserTest() {
        List<TestData> testCase = data();

        testCase.forEach(testData -> {
            SMSDto actual = parser.getSumFromText(testData.text, SMS);
            Assert.assertEquals(testData.expect, actual);
            System.out.println("Test data: " + testData.text);
        });
    }

    private List<TestData> data() {
        List<TestData> testCase = new ArrayList<>();
        SMSDto sms = new SMSDto();
        sms.setCurrencyType(BYN);
        sms.setConsumption(new BigDecimal(2053.52F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setOperation(ADMISSION);
        sms.setOperationCurrency(BYN);
        testCase.add(new TestData("Uvazhaemyj klient! Na Vash schet BY51MTBK30140008000003552111 postupilo 2 053,52 BYN. Vash MTBank!", sms));

        sms = new SMSDto();
        sms.setCurrencyType(BYN);
        sms.setConsumption(new BigDecimal(-1.80F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setOperation(WRITEOFF);
        sms.setOperationCurrency(BYN);
        testCase.add(new TestData("ZP KARTA 4177*1531 30/11/2017 PLATA ZA SMS-UVEDOMLENIYA: SPISANO 1.80 BYN Spravka \u200E5099999", sms));


        sms = new SMSDto();
        sms.setRest(new BigDecimal(1648.11F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setConsumption(new BigDecimal(-200F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setCurrencyType(BYN);
        sms.setOperationCurrency(USD);
        sms.setOperation(GET_CASH);
        testCase.add(new TestData("KARTA:4177*1531212 14/12/17 17:03 NALICHNYE 200.00 USD M6739195 OSTATOK 1 648.11 BYN Spr.:\u200E5099999", sms));


        sms = new SMSDto();
        sms.setRest(new BigDecimal(638.51F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setCurrencyType(BYN);
        sms.setOperationCurrency(BYN);
        sms.setOperation(DENIED);
        testCase.add(new TestData("KARTA:4177*1531 14/12/17 17:07 OTKAZANO NALICHNYE 200.00 BYN M6739195 OSTATOK 638.51 BYN Spr.:\u200E5099999", sms));

        sms = new SMSDto();
        sms.setRest(new BigDecimal(334.44F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setCurrencyType(BYN);
        sms.setConsumption(new BigDecimal(-6.08F).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setOperation(PAY);
        sms.setOperationCurrency(BYN);
        testCase.add(new TestData("KARTA:4177*1532 16/12/17 17:20 OPLATA 6.08 BYN APTEKA N 1 BAPB, , MINSK OSTATOK 334.44 BYN KOD AVT.035607 (WWW.MTBINGO.BY) Spr.:\u200E5099999", sms));

        sms = new SMSDto();
        sms.setCurrencyType(BYN);
        sms.setOperation(DENIED_PAY);
        sms.setOperationCurrency(BYN);
        testCase.add(new TestData("KARTA:5351*9577 14/12/17 16:35 OTKAZANO OPLATA 23.01 BYN MTB INTERNET POS, PR. PARTIZANSKIY 6A, MINSK OSTATOK 0.00 BYN Spr.:5099999", sms));

        sms = new SMSDto();
        sms.setCurrencyType(BYN);
        sms.setOperation(ADMISSION);
        sms.setOperationCurrency(BYN);
        sms.setRest(new BigDecimal(1823.22).setScale(2, BigDecimal.ROUND_HALF_UP));
        sms.setConsumption(new BigDecimal(47.00).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(new TestData("KARTA:4177*1536\n" +
                "12/01/18 17:57\n" +
                "POPOLNENIE 47.00 BYN\n" +
                "MTB INTERNET POS, PR. PARTIZANSKIY 6A, MINSK\n" +
                "OSTATOK 1 823.22 BYN\n" +
                "Spr.:\u200E5099999", sms));
        return testCase;
    }
}
