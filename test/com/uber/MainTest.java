package com.uber;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.IntStream;

import static com.uber.Main.*;
import static java.lang.Double.parseDouble;

public class MainTest extends TestCase {

    private static final Integer DEFAULT_VAL = 3;
    private static final Integer DEFAULT_VAL2 = 4;
    private static final Integer EXPECTED_RESULT = 12;


    Map<String, String> results;

    Product classUnderTest;
    private Long upperBoundExecutionSpeed;
    private Long lowerBoundExecutionSpeed;
    private Long duration;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        classUnderTest = new Product();
        results = new HashMap<String, String>();

        long startTime = System.nanoTime();
        Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);
        long endTime = System.nanoTime();

        duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.

        System.out.println("Method execution duration: " + duration + " nano seconds");

        if( EXPECTED_RESULT.equals(actualResult)
                && duration < MAX_EXECUTION_TIME_NANO_SEC) {

            upperBoundExecutionSpeed = new Long(new Double(Math.ceil(duration.doubleValue() * 1.02)).longValue());
            lowerBoundExecutionSpeed = new Long(new Double(Math.floor(duration.doubleValue() * .98)).longValue());

            System.out.println();
            System.out.println("upperBoundExecutionSpeed = " + upperBoundExecutionSpeed);
            System.out.println("lowerBoundExecutionSpeed = " + lowerBoundExecutionSpeed);
        }
    }

    @Test
    public void testProduct() throws Exception {

        Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);

        assertEquals(EXPECTED_RESULT, actualResult);

        if( EXPECTED_RESULT.equals(actualResult))
            results.put("test1-accuracy", "success");
        else
            results.put("test1-accuracy", "fail");

    }

    @Test
    public void testProductExecutionSpeed() {

        assertTrue(duration < MAX_EXECUTION_TIME_NANO_SEC);

        results.put("test1-duration", duration+"");
    }

    @Test
    public void testProductExecutionSpeedDeviation() {

        StringBuffer executionTimesString = new StringBuffer();
        final Boolean[] executionTimeOutOfRange = new Boolean[1];

        executionTimesString.append("Method invocations out of deviation: ");
        IntStream.rangeClosed(1, 100)
                .parallel().forEach(
                i -> {
                    long startTime = System.nanoTime();
                    Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);

                    if (duration > upperBoundExecutionSpeed
                            || duration < lowerBoundExecutionSpeed) {
                        executionTimeOutOfRange[0] = true;
                        executionTimesString.append(i + "(" + duration + "), ");

                    }
                }
                );

            assertNotNull(executionTimeOutOfRange[0]);
            assertTrue(executionTimeOutOfRange[0]);

            if(executionTimeOutOfRange[0] != null && !executionTimeOutOfRange[0])
                results.put("test2-duration-deviation", "success");
            else
                results.put("test2-duration-deviation", executionTimesString.toString());
    }



    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        System.out.println("results.keySet() = " + results.keySet());

        results.keySet().stream().forEach(

                        r -> {
                            List<String> lines = Arrays.asList(r, results.get(r));
                            try {
                                Path path = Paths.get("unit-test-"+ r + ".txt");

                                //Clear file
                                PrintWriter pw = null;
                                try {
                                    pw = new PrintWriter(path.toString());
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                pw.close();

                                System.out.println(r + ": path = " + path + " - result: " + results.get(r));

                                //Write new data
                                Files.write(path, lines, StandardCharsets.UTF_8,
                                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

        );

    }
}