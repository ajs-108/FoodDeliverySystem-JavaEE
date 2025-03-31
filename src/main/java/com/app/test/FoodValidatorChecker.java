package com.app.test;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FoodValidatorChecker {

    public static void main(String[] args) throws IOException {
        Path path = FileSystems.getDefault().getPath("/src/main/webapp");
        System.out.println(Paths.get("")
                .toAbsolutePath()
                .toString());
    }
}
