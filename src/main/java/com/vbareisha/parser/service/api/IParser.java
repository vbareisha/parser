package com.vbareisha.parser.service.api;

import com.vbareisha.parser.core.enums.ParserType;

import java.math.BigDecimal;

/**
 * Parsing text by template
 */
public interface IParser {
    BigDecimal getSumFromText(String text, ParserType type);
}
