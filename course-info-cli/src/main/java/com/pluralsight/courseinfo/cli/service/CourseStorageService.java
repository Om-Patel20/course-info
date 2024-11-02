package com.pluralsight.courseinfo.cli.service;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;

import java.util.List;

public class CourseStorageService {
    private static final String PS_BASE_URL = "http://localhost:8000/pluralsight";
    private final CourseRepository courseRepository;
    public CourseStorageService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Storing courses fetched from the Pluralsight website to the database.
    public void storePluralsightCourses(List<PluralsightCourse> psCourses) {
        for (PluralsightCourse psCourse: psCourses) {
            Course course = new Course(psCourse.id(),
                    psCourse.title(),
                    psCourse.durationInMinutes(),
                    PS_BASE_URL+psCourse.contentUrl());
            courseRepository.saveCourse(course);
        }
    }
}
