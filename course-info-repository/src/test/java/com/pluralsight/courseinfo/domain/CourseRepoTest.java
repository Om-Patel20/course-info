package com.pluralsight.courseinfo.domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseRepoTest {

    @Test
    void filledId() {
        Course course = new Course("CRS-01", "Python", 45, "www.pluralsight.com/sander-mak", Optional.empty());
        assertEquals("CRS-01", course.id());
    }

    @Test
    void filledName() {
        Course course = new Course("CRS-01", "Python", 45, "www.pluralsight.com/sander-mak", Optional.empty());
        assertEquals("Python", course.name());
    }

    @Test
    void filledUrl() {
        Course course = new Course("CRS-01", "Python", 45, "www.pluralsight.com/sander-mak", Optional.empty());
        assertEquals("www.pluralsight.com/sander-mak", course.url());
        //Exception exception = assertThrows(IllegalArgumentException.class, course::url); // course::url == () -> { course.url() }
        //exception.printStackTrace();
    }
}