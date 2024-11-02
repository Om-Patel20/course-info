package com.pluralsight.courseinfo.domain;

public record Course(String id, String name, long length, String url) {

    public Course {
        filled(id);
        filled(name);
        filled(url);
    }
    public static void filled(String s) { // Checks if string is null or blank
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("No value present!");
        }
    }
}
