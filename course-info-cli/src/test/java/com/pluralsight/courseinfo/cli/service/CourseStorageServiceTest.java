package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseStorageServiceTest {

    @Test
    void storePluralsightCourses() {
        CourseRepository repository = new InMemoryCourseRepository();
        CourseStorageService courseStorageService = new CourseStorageService(repository);
        
        PluralsightCourse ps1 = new PluralsightCourse("1", "Title 1", "01:40:00", "/url-1", false);
        courseStorageService.storePluralsightCourses(List.of(ps1));

        Course expected = new Course("1", "Title 1", 100, "http://localhost:8000/pluralsight/url-1");
        assertEquals(List.of(expected), repository.getAllCourses());
    }
    
    static class InMemoryCourseRepository implements CourseRepository {
        private final List<Course> courses = new ArrayList<>();
        @Override
        public void saveCourse(Course course) {
            courses.add(course);
        }

        @Override
        public List<Course> getAllCourses() {
            return courses;
        }
    }
}