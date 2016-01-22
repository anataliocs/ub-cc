package com.uber;

import junit.framework.TestCase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

import static com.uber.Main.*;

public class MainTest extends TestCase {

    private static final Integer DEFAULT_VAL = 3;
    private static final Integer DEFAULT_VAL2 = 4;
    private static final Integer EXPECTED_RESULT = 12;


    Map<String, String> results;

    Product classUnderTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        classUnderTest = new Product();
        results = new HashMap<String, String>();
    }

    public void testProduct() throws Exception {

        Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);

        assertEquals(EXPECTED_RESULT, actualResult);

        if( EXPECTED_RESULT.equals(actualResult))
            results.put("test1-accuracy", "success");

    }

    public void testProductExecutionSpeed() {

        long startTime = System.nanoTime();
        Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

        System.out.println("Method execution duration: " + duration + " nano seconds");

        if( EXPECTED_RESULT.equals(actualResult)
                && duration < MAX_EXECUTION_TIME_NANO_SEC)
            results.put("test2-duration", duration+"");
    }


    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        results.keySet().stream().forEach(

                        r -> {
                            List<String> lines = Arrays.asList(r, results.get(r));
                            try {
                                Path path = Paths.get("unit-test-"+ r + ".txt");
                                System.out.println(r + ": path = " + path + " - result: " + results.get(r));
                                Files.write(path, lines, StandardCharsets.UTF_8,
                                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

        );

    }
}