package com.vbareisha.parser.service.api;

import com.vbareisha.parser.core.enums.ParserType;

/**
 * Parsing text by template
 */
public interface IParser<T> {
    T getSumFromText(String text, ParserType type);
}
