package com.vbareisha.parser;

import com.vbareisha.parser.service.Parser;
import com.vbareisha.parser.service.api.IParser;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.vbareisha.parser.core.enums.ParserType.*;

public class ParserTest {

    private IParser parser = new Parser();

    private class TestData {
        String text;
        BigDecimal expect;

        public TestData(String text, BigDecimal expect) {
            this.text = text;
            this.expect = expect;
        }
    }

    @Test
    public void parserTest() {
        List<TestData> testCase = new ArrayList<>();
        TestData item = new TestData("Uvazhaemyj klient! Na Vash schet BY51MTBK30140008000003552111 postupilo 2 053,52 BYN. Vash MTBank!", new BigDecimal(2053.52F).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(item);
        item = new TestData("ZP KARTA 4177*1531 30/11/2017 PLATA ZA SMS-UVEDOMLENIYA: SPISANO 1.80 BYN Spravka \u200E5099999", new BigDecimal(1.80F).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(item);
        item = new TestData("KARTA:4177*1531212 14/12/17 17:03 NALICHNYE 200.00 USD M6739195 OSTATOK 1 648.11 BYN Spr.:\u200E5099999", new BigDecimal(1648.11F).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(item);
        item = new TestData("KARTA:4177*1531 14/12/17 17:07 OTKAZANO NALICHNYE 200.00 BYN M6739195 OSTATOK 638.51 BYN Spr.:\u200E5099999", new BigDecimal(638.51F).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(item);
        item = new TestData("KARTA:4177*1532 16/12/17 17:20 OPLATA 6.08 BYN APTEKA N 1 BAPB, , MINSK OSTATOK 334.44 BYN KOD AVT.035607 (WWW.MTBINGO.BY) Spr.:\u200E5099999", new BigDecimal(334.44F).setScale(2, BigDecimal.ROUND_HALF_UP));
        testCase.add(item);

        testCase.forEach(testData -> {
            System.out.println("Test data: " + testData.text);
            BigDecimal actual = parser.getSumFromText(testData.text, SUM);
            Assert.assertEquals(testData.expect, actual);
        });
    }
}
