package december.tuesday.stream_interview;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MakeDeveloperMapTest {

    private static List<Developer> developers;
    private static List<Assignment> assignments;
    private static List<Task> tasks;
    private static final Map<String, List<String>> expected = new HashMap<>();

    @BeforeAll
    static void setUp() {
        /* Initialize developers */
        developers = List.of(
                new Developer(1, "John Doe"),
                new Developer(2, "Jane Doe"),
                new Developer(3, "Jack Doe"),
                new Developer(5, "New position")
        );

        /* Initialize assignments */
        assignments = List.of(
                new Assignment(1, 1),
                new Assignment(2, 1),
                new Assignment(3, 1),
                new Assignment(4, 2),
                new Assignment(5, 2),
                new Assignment(6, 2),
                new Assignment(7, 2),
                new Assignment(8, 3),
                new Assignment(9, 3),
                new Assignment(10, 3),
                new Assignment(13, 1)
        );

        /* Task initialization */
        tasks = List.of(
                new Task(1, "One"),
                new Task(2, "Two"),
                new Task(3, "Three"),
                new Task(4, "Four"),
                new Task(5, "Five"),
                new Task(6, "Six"),
                new Task(7, "Seven"),
                new Task(8, "Eight"),
                new Task(9, "Nine"),
                new Task(10, "Ten"),
                new Task(11, "Eleven"),
                new Task(12, "Twelve")
        );

        // Expected results
        expected.put("John Doe", List.of("One", "Two", "Three"));
        expected.put("Jane Doe", List.of("Four", "Five", "Six", "Seven"));
        expected.put("Jack Doe", List.of("Eight", "Nine", "Ten"));
        expected.put("New position", Collections.emptyList());
    }

    @Test
    void checkReport() {
        var actual = MakeDeveloperMap.report(tasks, developers, assignments);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void checkReportWithStreams() {
        var actual = MakeDeveloperMap.reportWithStreams(tasks, developers, assignments);

        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

}
