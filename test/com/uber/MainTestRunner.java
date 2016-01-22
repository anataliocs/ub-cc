package com.uber;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MainTestRunner {



    public static void main(String[] args) {

        org.junit.runner.JUnitCore.main("com.uber.MainTest");
    }

}
