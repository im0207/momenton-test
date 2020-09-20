package com.momenton.orgchart.input;

import java.util.function.UnaryOperator;

/**
 * This class sanitises the input strings provided by the user.
 */
public class Sanitiser implements UnaryOperator<String> {
    @Override
    public String apply(String s) {
        return s.trim();
    }
}
