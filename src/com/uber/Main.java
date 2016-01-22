package com.uber;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Double.*;

public class Main {

    private static final Long EXPECTED_LINE_COUNT = 2l;
    public static final Long MAX_EXECUTION_TIME_NANO_SEC = 10000l;
    private static Long upperBoundExecutionSpeed;
    private static Long lowerBoundExecutionSpeed;

    public static void main(String[] args) {

        List<Boolean> gateResults = new ArrayList<>();

        Boolean gateOneResults = gateOne();
        gateResults.add(gateOneResults);
        System.out.println("Gate One Results = " + gateOneResults);
        System.out.println();

        Boolean gateTwoResults = gateTwo();
        System.out.println("Gate Two Results = " + gateTwoResults);
        gateResults.add(gateTwoResults);
        System.out.println();


        Optional<Boolean> failedGate = gateResults.stream().filter( gr -> !gr).findFirst();

        System.out.println();
        System.out.println("====");
        System.out.println();
        if(failedGate.isPresent())
            System.out.println("Failed");
        else
            System.out.println("Ready to deploy!");

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

            upperBoundExecutionSpeed = new Double(Math.ceil(parseDouble(executionTime.get()) * 1.02)).longValue();
            lowerBoundExecutionSpeed = new Double(Math.floor(parseDouble(executionTime.get()) * .98)).longValue();

            System.out.println();
            System.out.println("upperBoundExecutionSpeed = " + upperBoundExecutionSpeed);
            System.out.println("lowerBoundExecutionSpeed = " + lowerBoundExecutionSpeed);

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

    public static Long getUpperBoundExecutionSpeed() {
        return upperBoundExecutionSpeed;
    }

    public static void setUpperBoundExecutionSpeed(Long upperBoundExecutionSpeed) {
        Main.upperBoundExecutionSpeed = upperBoundExecutionSpeed;
    }

    public static Long getLowerBoundExecutionSpeed() {
        return lowerBoundExecutionSpeed;
    }

    public static void setLowerBoundExecutionSpeed(Long lowerBoundExecutionSpeed) {
        Main.lowerBoundExecutionSpeed = lowerBoundExecutionSpeed;
    }
}
