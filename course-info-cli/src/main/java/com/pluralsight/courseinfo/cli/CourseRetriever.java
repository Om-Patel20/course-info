package com.pluralsight.courseinfo.cli;

import com.pluralsight.courseinfo.cli.service.CourseRetrievalService;
import com.pluralsight.courseinfo.cli.service.CourseStorageService;
import com.pluralsight.courseinfo.cli.service.PluralsightCourse;
import com.pluralsight.courseinfo.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.function.Predicate.not;

public class CourseRetriever {
    private static final Logger LOG = LoggerFactory.getLogger(CourseRetriever.class);

    public static void main(String... args) {
        LOG.info("Course Retriever is starting...");
        if (args.length == 0) {
            LOG.warn("Please provide the author name as first argument.");
            return;
        }

        try {
            retrieveCourses(args[0]);
            //retrieveCourses("sander-mak");
        } catch (Exception e) {
            LOG.error("Unexpected Error", e); // We don't need a StackTrace for this error log.
        }
    }

    private static void retrieveCourses(String authorId) {
        LOG.info("Retrieve courses for this author '{}'...", authorId);
        CourseRetrievalService courseRetrievalService = new CourseRetrievalService();
        CourseRepository courseRepository = CourseRepository.openCourseRepository("./courses.db");
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        List<PluralsightCourse> coursesToStore = courseRetrievalService.getCoursesFor(authorId)
                                .stream()
                                .filter(not(PluralsightCourse::isRetired)) // Filters out retired courses from the returned JSON list
                                .filter(course -> course.duration() != null) // There were some courses where duration=null
                                .toList();

        LOG.info("Retrieved the following {} courses {}", coursesToStore.size(), coursesToStore);
        courseStorageService.storePluralsightCourses(coursesToStore);
        LOG.info("Courses successfully stored");
    }
} 