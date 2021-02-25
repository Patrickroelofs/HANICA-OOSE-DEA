package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringCalculator {
    public int add(String numbers) {
        if(numbers.equals(""))
            return 0;

        List<String> splittedString = new ArrayList<>(Arrays.asList(numbers.split(",|\n")));

        int calculated = 0;
        for(String s : splittedString) {
            if(Integer.parseInt(s) <= 1000)
                calculated = Integer.parseInt(s) + calculated;
        }

        return calculated;
    }
}
