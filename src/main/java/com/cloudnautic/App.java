package com.cloudnautic;

public class App {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" Java Maven Jenkins Pipeline");
        System.out.println("========================================");
        System.out.println("Application started successfully!");
        System.out.println("Maven build completed successfully.");
        System.out.println("Jenkins Pipeline is working!");
        System.out.println("========================================");
    }

    public static String getMessage() {

        return "Java Maven Jenkins Pipeline is working!";
    }

    public static int add(int a, int b) {

        return a + b;
    }
}
