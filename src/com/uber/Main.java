package com.uber;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final Long EXPECTED_LINE_COUNT = 2l;

    public static void main(String[] args) {

        Boolean gateOneResults = gateOne();
        System.out.println("gateOneResults = " + gateOneResults);
    }


    public static Boolean gateOne() {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get("/Users/canatalio/uber-code-challenge/unit-test-accuracy.txt")).collect(
                    Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Long actualLineCount = lines.stream().count();

        System.out.println("actualLineCount = " + actualLineCount);

        if(EXPECTED_LINE_COUNT.equals(actualLineCount))
            return true;

        return false;
    }



}
