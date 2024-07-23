package org.example;

import java.util.TimeZone;

public class Main {
    public static void main(String[] args) {
        TimeZone timeZone ;

        for (String temp: TimeZone.getAvailableIDs()) {
            System.out.println(temp);
        }
    }
}