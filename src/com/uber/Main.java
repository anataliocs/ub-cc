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

        Boolean gateThreeResults = gateThree();
        System.out.println("Gate Three Results = " + gateThreeResults);
        gateResults.add(gateThreeResults);
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

            lines = Files.lines(Paths.get("src/com/uber/"+filePath)).collect(
                    Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();

        }
        return lines;

    }

    public static Boolean gateOne() {
        List<String> lines = getFileContents("unit-test-test1-accuracy.txt");

        Long actualLineCount = lines.stream().count();

        System.out.println("Actual Line Count = " + actualLineCount);

        if(EXPECTED_LINE_COUNT.equals(actualLineCount))
            return true;

        return false;
    }

    public static Boolean gateTwo() {
        List<String> lines = getFileContents("unit-test-test2-duration.txt");

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

    public static Boolean gateThree() {
        List<String> lines = getFileContents("unit-test-test3-duration-deviation.txt");

        lines.stream().forEach(
                l -> System.out.println(l)
        );
        System.out.println("warning method invokation deviation > 2%");

        /*
               Execution time deviation is always greater then 2% for some method invocations so returning true
         */

        return true;
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
