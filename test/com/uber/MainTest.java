package com.uber;

import junit.framework.TestCase;

public class MainTest extends TestCase {

    private static final Integer DEFAULT_VAL = 3;
    private static final Integer DEFAULT_VAL2 = 4;
    private static final Integer EXPECTED_RESULT = 12;

    Product classUnderTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        classUnderTest = new Product();
    }

    public void testProduct() throws Exception {

        Integer actualResult = classUnderTest.getProduct(DEFAULT_VAL, DEFAULT_VAL2);

        assertEquals(EXPECTED_RESULT, actualResult);
    }
}