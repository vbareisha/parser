package com.vbareisha.parser.service.api;

import com.vbareisha.parser.core.enums.ParserType;

/**
 * Parsing text by template
 */
public interface IParser<T> {
    T parse(String text, ParserType type);
}
