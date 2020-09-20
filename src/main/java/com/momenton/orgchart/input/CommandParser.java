package com.momenton.orgchart.input;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class CommandParser implements Function<String, List<String>> {
    @Override
    public List<String> apply(String s) {
        return Arrays.asList(s.split(","));
    }
}
