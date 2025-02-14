package december.tuesday.stream_interview;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The task from real interview on Middle Java Developer position.
 * Return Map where:
 * Key is Developer Name
 * Value is List of Task titles assigned to Developer
 *
 * Handle non-consistent cases:
 * 1. Developer::getId not present in Assignment::getDeveloperId - add Developer::getName with Collections::emptyList
 * 2. Assignment::getTaskId not present in Task::getId - Skip Assignment
 */
@UtilityClass
public class MakeDeveloperMap {

    /**
     * Not Stream API method for return Developer's Map.
     *
     * @param tasks       list of {@link Task}.
     * @param developers  list of {@link Developer}.
     * @param assignments list of {@link Assignment}
     * @return Map where:
     * - Key is Developer Name
     * - Value is List of Task Title assigned to Developer Handle.
     */
    public Map<String, List<String>> report(
            List<Task> tasks,
            List<Developer> developers,
            List<Assignment> assignments
    ) {
        Map<Integer, String> taskIdToName = new HashMap<>();
        for (Task task : tasks) {
            taskIdToName.put(task.id(), task.title());
        }

        Map<Integer, List<String>> developerToTasks = new HashMap<>();
        for (Assignment assignment : assignments) {
            if (taskIdToName.containsKey(assignment.taskId())) {
                developerToTasks.computeIfAbsent(assignment.developerId(), b -> new ArrayList<>());
                developerToTasks.get(assignment.developerId()).add(taskIdToName.get(assignment.taskId()));
            }
        }

        Map<String, List<String>> res = new HashMap<>();

        for (Developer developer : developers) {
            res.put(developer.name(), developerToTasks.getOrDefault(developer.id(), Collections.emptyList()));
        }

        return res;
    }

    /**
     * Method with Stream API for return Developer's Map.
     * @param tasks list of {@link Task}.
     * @param developers list of {@link Developer}.
     * @param assignments list of {@link Assignment}
     * @return Map where:
     * - Key is Developer Name
     * - Value is List of Task Title assigned to Developer Handle.
     */
    public Map<String, List<String>> reportWithStreams(
            List<Task> tasks,
            List<Developer> developers,
            List<Assignment> assignments
    ) {
        Map<Integer, String> taskMap = tasks
                .stream()
                .collect(Collectors.toMap(Task::id, Task::title));

        Map<Integer, List<String>> developerToTasks = assignments
                .stream()
                .filter(a -> taskMap.containsKey(a.taskId()))
                .collect(Collectors.groupingBy(
                        Assignment::developerId,
                        Collectors.mapping(a -> taskMap.get(a.taskId()), Collectors.toList())
                ));

        return developers
                .stream()
                .collect(
                        Collectors.toMap(
                                Developer::name,
                                d -> developerToTasks.getOrDefault(d.id(), Collections.emptyList())
                        )
                );
    }

    // A method to print a map to the console.
    // Maybe useful for testing and debugging.
    private void printMap(Map<String, List<String>> map) {
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

}
