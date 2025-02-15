package december.tuesday;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class ListStreamTest {

    private static final List<String> ANIMALS = Arrays.asList("Cat", "Dog", "Elephant", "Lion", "Tiger", "Bear");
    private static final List<String> FRUITS = Arrays.asList("Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig");

    @Test
    void reverseSort() {
        List<String> actual = ListStream.reverseSort(ANIMALS, FRUITS);
        List<String> expected = List.of("Tiger", "Lion", "Fig", "Elephant", "Elderberry", "Dog", "Date", "Cherry", "Cat", "Bear", "Banana", "Apple");

        Assertions.assertIterableEquals(expected, actual);
    }
}
