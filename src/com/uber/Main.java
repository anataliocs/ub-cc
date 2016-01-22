package com.uber;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {

    private static final Long EXPECTED_LINE_COUNT = 2l;
    public static final Long MAX_EXECUTION_TIME_NANO_SEC = 10000l;

    public static void main(String[] args) {

        Boolean gateOneResults = gateOne();
        System.out.println("Gate One Results = " + gateOneResults);
        System.out.println();

        Boolean gateTwoResults = gateTwo();
        System.out.println("Gate Two Results = " + gateTwoResults);
        System.out.println();
    }


    private static List<String> getFileContents(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.lines(Paths.get(filePath)).collect(
                    Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();

        }
        return lines;
    }

    public static Boolean gateOne() {
        List<String> lines = getFileContents("/Users/canatalio/uber-code-challenge/unit-test-test1-accuracy.txt");

        Long actualLineCount = lines.stream().count();

        System.out.println("Actual Line Count = " + actualLineCount);

        if(EXPECTED_LINE_COUNT.equals(actualLineCount))
            return true;

        return false;
    }

    public static Boolean gateTwo() {
        List<String> lines = getFileContents("/Users/canatalio/uber-code-challenge/unit-test-test2-duration.txt");

        Optional<String> executionTime = lines.stream()
                .filter(l -> isNumeric(l))
                .findFirst();

        if(executionTime.isPresent()
                && Long.parseLong(executionTime.get()) < MAX_EXECUTION_TIME_NANO_SEC) {

            System.out.println("Method ExecutionTime = " + executionTime.get());
            System.out.println("Max Method ExecutionTime = " + MAX_EXECUTION_TIME_NANO_SEC);
            return true;
        }


        return false;
    }


    public static boolean isNumeric(String str)
    {
        try
        {
            Long l = Long.parseLong(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }

        return true;
    }
}
