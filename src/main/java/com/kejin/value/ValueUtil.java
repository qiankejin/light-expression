package com.kejin.value;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValueUtil {
    public static Map<String, Value> randomValue(Set<String> args) {
        if (args==null||args.isEmpty()) {

            return new HashMap<>();
        }
        Random random = new Random();
        return args.stream().collect(Collectors.toMap(Function.identity(), s -> Value.of(Decimal.of(random.nextInt(1000)+1).divide(100))));
    }
}
